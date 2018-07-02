package org.vrhel.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * A texture buffer creates a texture that can be
 * rendered to.
 * 
 * @author Ethan Vrhel
 *
 */
public class TextureBuffer extends AbstractBuffer {

	private FrameBuffer fbo;
	private Texture2D tex;
	
	private ObjectBuffer buffer;
	
	TextureBuffer(int width, int height, int filter) throws RuntimeException {
		fbo = new FrameBuffer();
		
		tex = new Texture2D(width, height, filter);
		
		buffer = new ObjectBuffer();

		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tex.getID(), 0);
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("Buffer creation failed.");
		}
		
	}
	
	ObjectBuffer getBuffer() {
		return buffer;
	}
	
	void bind() {
		fbo.bind(GL_FRAMEBUFFER);
		buffer = null;
	}
	
	void destroy() {
		fbo.destroy();
		tex.destroy();
	}

	@Override
	void render() {
		// TODO Auto-generated method stub
		
	}
}
