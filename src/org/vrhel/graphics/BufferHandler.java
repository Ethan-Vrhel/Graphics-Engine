package org.vrhel.graphics;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;

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
		
		for (int i = 0; i < buffers.size(); i++) {
			if (buffers.get(i) != null) {
				if (buffers.get(i).isEnabled()) {
					//System.out.println("Drawing to: " + buffers.get(i));
					FrameBuffer fbo = buffers.get(i).getFrameBuffer();
					RenderBuffer rbo = buffers.get(i).getRenderBuffer();
					Buffer.ClearFlag flag = buffers.get(i).getClearFlag();
					
					fbo.bind(GL_DRAW_FRAMEBUFFER);
					fbo.unbind(GL_READ_FRAMEBUFFER);
					
					glBlendFuncSeparatei(fbo.getFBO(), GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
					
					if (i == 0) {
						clear(buffers.get(i));
					}
					buffers.get(i).getObjectBuffer().render();
					
					fbo.bind(GL_READ_FRAMEBUFFER);
					fbo.unbind(GL_DRAW_FRAMEBUFFER);
					
					if (i < buffers.size() - 1) {
						FrameBuffer fbo2 = buffers.get(i + 1).getFrameBuffer();
						RenderBuffer rbo2 = buffers.get(i + 1).getRenderBuffer();
						fbo2.bind(GL_DRAW_FRAMEBUFFER);
						fbo2.unbind(GL_READ_FRAMEBUFFER);
							
						clear(buffers.get(i + 1));
						
						//System.out.println("blit to: " + buffers.get(i + 1));
						//glBlendFuncSeparatei(fbo.getFBO(), GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_DST_ALPHA);
						glBlitFramebuffer(0, 0, rbo2.getWidth(), rbo2.getHeight(), 0, 0, rbo2.getWidth(), rbo2.getHeight(),
								GL_COLOR_BUFFER_BIT, GL_NEAREST);
						
						fbo2.unbind(GL_DRAW_FRAMEBUFFER);
						fbo2.unbind(GL_READ_FRAMEBUFFER);
					}
				
					
					
					//fbo.unbind(GL_READ_FRAMEBUFFER);
				}
			}
		}
		
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		
		glClearColor(0.5f, 0.5f, 1f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		ObjectBuffer.getBuffer().render();

		//glBlendFuncSeparatei(0, GL_ONE, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_DST_ALPHA);
		//glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		glBlitFramebuffer(0, 0, 1280, 720, 0, 0, 1280 / 2, 720 / 2,
				GL_COLOR_BUFFER_BIT, GL_NEAREST);
		
	}
	
	private void clear(Buffer buff) {
		Buffer.ClearFlag flag = buff.getClearFlag();
		if (flag != null) {
			buff.getFrameBuffer().bind(GL_DRAW_FRAMEBUFFER);
			
			
			
			glClearColor(0f,0f,1f,0f);
	
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
		Buffer buff = Buffer.genBuffer(name, width, height, shader, flag);
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
