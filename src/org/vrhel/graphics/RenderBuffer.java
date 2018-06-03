package org.vrhel.graphics;

import static org.lwjgl.opengl.GL30.*;

final class RenderBuffer {

	private int rbo;
	
	RenderBuffer(int width, int height, int format) {
		rbo = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
		glRenderbufferStorage(GL_RENDERBUFFER, format, width, height);
		System.out.println("Genreated renderbuffer: " + rbo + " | size: " + width + "x" + height + " format: " + format);
	}
	
	int getRenderBuffer() {
		return rbo;
	}
	
	void bind() {
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
	}
}
