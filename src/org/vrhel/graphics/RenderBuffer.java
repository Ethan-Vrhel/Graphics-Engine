package org.vrhel.graphics;

import static org.lwjgl.opengl.GL30.*;

final class RenderBuffer {

	private int rbo;
	
	private int width;
	private int height;
	
	RenderBuffer(int width, int height, int format) {
		rbo = glGenRenderbuffers();
		this.width = width;
		this.height = height;
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
		glRenderbufferStorage(GL_RENDERBUFFER, format, width, height);
		System.out.println("Genreated renderbuffer: " + rbo + " | size: " + width + "x" + height + " format: " + format);
	}
	
	void destroy() {
		glDeleteRenderbuffers(rbo);
	}
	
	int getRenderBuffer() {
		return rbo;
	}
	
	void bind() {
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
	}
	
	int getWidth() {
		return width;
	}
	
	int getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		return getClass() + ": " + rbo;
	}
}
