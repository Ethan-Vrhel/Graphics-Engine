package org.vrhel.graphics;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL45.*;
/**
 * The <code>BufferHandler</code> handles
 * the storage of Framebuffers and
 * Renderbuffers.
 * 
 * @author Ethan Vrhel
 * @since 1.2
 */
public class BufferHandler {
	
	/**
	 * Indicates that no buffer should be
	 * bound.
	 */
	public static final int UNBIND = -1;

	private static BufferHandler handler;
	
	private ArrayList<Buffer> buffers;
	
	private Buffer boundBuffer;
	
	private BufferHandler() {
		this.buffers = new ArrayList<Buffer>();
	}
	
	static void init() {
		if (handler == null)
			handler = new BufferHandler();
		else
			throw new UnsupportedOperationException("BufferHandler already exists");
	}
	
	static void destroy() {
		for (int i = 0; i < handler.buffers.size(); i++) {
			if (handler.buffers.get(i) != null) {
				handler.buffers.get(i).destroy();
			}
		}
		handler.buffers = null;
		handler = null;
	}
	
	/**
	 * Returns the <code>BufferHandler</code>.
	 * 
	 * @return The <code>BufferHandler</code>.
	 * @throws IllegalArgumentException When the handler
	 * has not been initialized.
	 */
	public static BufferHandler getHandler() throws IllegalArgumentException {
		if (handler == null)
			throw new IllegalArgumentException("Handler has not been initialized");
		return handler;
	}
	
	void render() {
		//glClearColor(0.5f, 0.5f, 1f, 1.0f);
		//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		//ObjectBuffer.getBuffer().render();
		
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);

		glClearColor(0.5f, 0.5f, 1f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		ObjectBuffer.getBuffer().render();
		
		for (int i = 0; i < buffers.size(); i++) {
			if (buffers.get(i) != null) {
				if (buffers.get(i).isEnabled()) {
					//System.out.println("Drawing to: " + buffers.get(i));
					buffers.get(i).render();
					Buffer buff = buffers.get(i);
					FrameBuffer fbo = buffers.get(i).getFrameBuffer();
					RenderBuffer rbo = buffers.get(i).getRenderBuffer();
					Buffer.ClearFlag flag = buffers.get(i).getClearFlag();
					
					fbo.bind(GL_DRAW_FRAMEBUFFER);
					fbo.unbind(GL_READ_FRAMEBUFFER);
					
					//glBlendFuncSeparatei(fbo.getFBO(), GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
					
					//if (i == 0) {
						clear(buffers.get(i));
					//}
					buffers.get(i).getObjectBuffer().render();
					
					fbo.bind(GL_READ_FRAMEBUFFER);
					fbo.unbind(GL_DRAW_FRAMEBUFFER);					
					
					//fbo.unbind(GL_READ_FRAMEBUFFER);
					
					glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);

//					glClearColor(0.5f, 0.5f, 1f, 1.0f);
//					glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//					ObjectBuffer.getBuffer().render();
				
					//FrameBuffer fbo = buffers.get(0).getFrameBuffer();
					//RenderBuffer rbo = buffers.get(0).getRenderBuffer();
					//glBlendFuncSeparatei(0, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_DST_ALPHA);
					glEnable(GL_BLEND);
					glBlendFunci(fbo.getFBO(), GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					glBlitFramebuffer(0, 0, rbo.getWidth(), rbo.getHeight(), buff.getX(), buff.getY(), buff.getX() + rbo.getWidth(), buff.getY() + rbo.getHeight(),
						GL_COLOR_BUFFER_BIT, GL_NEAREST);		
				}
			}
		}	
	}
	
	private void clear(Buffer buff) {
		Buffer.ClearFlag flag = buff.getClearFlag();
		if (flag != null) {
			buff.getFrameBuffer().bind(GL_DRAW_FRAMEBUFFER);
			
			
			
			glClearColor(flag.r,flag.g,flag.b,flag.a); // Color to clear with
	
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
		}
	}
	
	/**
	 * Generates a new <code>Buffer</code>.  The
	 * order of generation matters, so those generated
	 * later will be used after the ones generated
	 * earlier.
	 * 
	 * @param name The name of the <code>Buffer</code>.
	 * <code>null</code> indicates a default name.
	 * @param width The width of the buffer.
	 * @param height The height of the buffer.
	 * @param shader The associated shader.  <code>null</code>
	 * specifies that no shader will be used.
	 * @param flag The <code>ClearFlag</code> that represents
	 * how the buffer will clear on each update.
	 * @return The new <code>Buffer</code>.
	 */
	public int genBuffer(String name, int width, int height, Shader shader, Buffer.ClearFlag flag) {
		return genBuffer(name, 0, 0, width, height, shader, flag);
	}
	
	/**
	 * Generates a new <code>Buffer</code>.  The
	 * order of generation matters, so those generated
	 * later will be used after the ones generated
	 * earlier.
	 * 
	 * @param name The name of the <code>Buffer</code>.
	 * <code>null</code> indicates a default name.
	 * @param x The x position of the buffer.
	 * @param y The y position of the buffer.
	 * @param width The width of the buffer.
	 * @param height The height of the buffer.
	 * @param shader The associated shader.  <code>null</code>
	 * specifies that no shader will be used.
	 * @param flag The <code>ClearFlag</code> that represents
	 * how the buffer will clear on each update.
	 * @return The new <code>Buffer</code>.
	 */
	public int genBuffer(String name, int x, int y, int width, int height, Shader shader, Buffer.ClearFlag flag) {
		Buffer buff = Buffer.genBuffer(name, x, y, width, height, shader, flag);
		buffers.add(buff);
		return buff.getID();
	}
	
	/**
	 * Returns the <code>Buffer</code> of name
	 * <code>name</code>.
	 * 
	 * @param name The name of the <code>Buffer</code>.
	 * @return The respective <code>Buffer</code>.  Returns
	 * <code>null</code> if it does not exist.
	 */
	public Buffer getBuffer(String name) {
		if (name == null)
			return null;
		for (int i = 0; i < buffers.size(); i++) {
			if (name.equals(buffers.get(i).getName()))
				return buffers.get(i);
		}
		return null;
	}
	
	/**
	 * Returns the <code>Buffer</code> width id
	 * <code>id</code>.
	 * 
	 * @param id The <code>Buffer</code>'s id.
	 * @return The respective <code>Buffer</code>.  Returns
	 * <code>null</code> if it does not exist.
	 */
	public Buffer getBuffer(int id) {
		if (id < 0)
			return null;
		for (int i = 0; i < buffers.size(); i++) {
			if (buffers.get(i).getID() == id)
				return buffers.get(i);
		}
		return null;
	}
	
	/**
	 * Binds the buffer with id <code>id</code>.
	 * 
	 * @param id The id.
	 */
	public void bind(int id) {
		boundBuffer = getBuffer(id);
	}
	
	Buffer getBoundBuffer() {
		return boundBuffer;
	}
}
