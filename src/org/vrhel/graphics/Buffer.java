package org.vrhel.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;

import java.awt.Color;

/**
 * A <code>Buffer</code> consists of a
 * Framebuffer as well as a Renderbuffer.
 * 
 * @author Ethan Vrhel
 * @since 1.2
 */
public final class Buffer {
	
	/**
	 * The flags for clearing the buffer.
	 * 
	 * @author Ethan Vrhel
	 * @since 1.2
	 */
	public static class ClearFlag {
		
		float r, g, b, a;
		
		public ClearFlag() { }
		
		public ClearFlag(Color color) {
			this.r = color.getAlpha() / 255f;
			this.g = color.getGreen() / 255f;
			this.b = color.getBlue() / 255f;
			this.a = color.getAlpha() / 255f;
		}
		
		public ClearFlag(float r, float g, float b, float a) {
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;
		}
		
		public ClearFlag(int r, int g, int b, int a) {
			this.a = r / 255f;
			this.g = g / 255f;
			this.b = b / 255f;
			this.a = a / 255f;
		}
	}

	private static int id_next = 0;
	
	private int id;
	private String name;
	private boolean enabled;
	private FrameBuffer fbo;
	private RenderBuffer rbo;
	private ClearFlag flag;
	
	private Shader shader;
	private ObjectBuffer buffer;
	
	private Buffer(String name, int id, int width, int height, Shader shader, ClearFlag flag) {
		this.id = id;
		if (name == null)
			name = "Buffer-" + id;
		this.name = name;
		this.enabled = true;
		fbo = new FrameBuffer();
		rbo = new RenderBuffer(width, height, GL_RGBA);
		fbo.attachRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, rbo);
		this.shader = shader;
		this.flag = flag;
		this.buffer = new ObjectBuffer();
	}
	
	static Buffer genBuffer(String name, int width, int height, Shader shader, ClearFlag flag) {
		Buffer buffer = genBuffer(name, id_next, width, height, shader, flag);
		id_next++;
		return buffer;
	}
	
	static Buffer genBuffer(String name, int id, int width, int height, Shader shader, ClearFlag flag) {
		return new Buffer(name, id, width, height, shader, flag);
	}
	
	void destroy() {
		fbo.destroy();
		fbo = null;
		rbo.destroy();
		rbo = null;
		buffer = null;
	}
	
	/**
	 * Returns the ID of this buffer.
	 * 
	 * @return The ID.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the name of this buffer.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets whether this buffer is enabled.
	 * 
	 * @param enabled Whether this buffer is
	 * enabled.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Returns whether this buffer is enabled.
	 * 
	 * @return <code>true</code> if this buffer is
	 * enabled and <code>false</code> otherwise.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	FrameBuffer getFrameBuffer() {
		return fbo;
	}
	
	RenderBuffer getRenderBuffer() {
		return rbo;
	}
	
	ObjectBuffer getObjectBuffer() {
		return buffer;
	}
	
	/**
	 * Returns the associated shader.
	 * 
	 * @return The shader.
	 */
	public Shader getShader() {
		return shader;
	}
	
	/**
	 * Sets the associated shader.
	 * 
	 * @param shader The shader.
	 */
	public void setShader(Shader shader) {
		this.shader = shader;
	}
	
	/**
	 * Gets the clear flag associated with this
	 * buffer.
	 * 
	 * @return The clear flag.
	 */
	public ClearFlag getClearFlag() {
		return flag;
	}
	
	@Override
	public String toString() {
		return "Buffer \"" + name + "\" id: " + id;
	}
}
