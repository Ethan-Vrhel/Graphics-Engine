package org.vrhel.graphics;

import static org.lwjgl.opengl.GL30.*;

final class FrameBuffer {

	private int fbo;
	
	FrameBuffer() {
		fbo = glGenFramebuffers();
	}
	
	void bind(int type) {
		glBindFramebuffer(type, fbo);
	}
	
	void unbind(int type) {
		glBindFramebuffer(type, 0);
	}
	
	int getFBO() {
		return fbo;
	}
	
	void attach(int target, int attach, RenderBuffer buffer) {
		attach(target, attach, buffer.getRenderBuffer());
	}
	
	void attach(int target, int attach, int rbo) {
		bind(GL_FRAMEBUFFER);
		glFramebufferRenderbuffer(target, attach, GL_RENDERBUFFER, rbo);
	}
	
	void detach(int target, int attach) {
		bind(GL_FRAMEBUFFER);
		glFramebufferRenderbuffer(target, attach, GL_RENDERBUFFER, 0);
	}
}
