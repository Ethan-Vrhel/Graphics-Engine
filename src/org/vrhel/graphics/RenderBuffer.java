package org.vrhel.graphics;

import static org.lwjgl.opengl.GL30.*;

final class RenderBuffer {

	private int rbo;
	
	RenderBuffer(int width, int height, int format) {
		rbo = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
		glRenderbufferStorage(rbo, format, width, height);
	}
	
	int getRenderBuffer() {
		return rbo;
	}
	
	void bind() {
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
	}
}
