package org.vrhel.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.glBlendFunci;

import org.lwjgl.opengl.GL30;

/**
 * A texture buffer creates a texture that can be
 * rendered to.
 * 
 * @author Ethan Vrhel
 *
 */
public class TextureBuffer extends AbstractBuffer {

	//private FrameBuffer fbo;
	//private Texture2D tex;
	
	private ObjectBuffer buffer;
	
	int texture;
	int renderbuffer;
	int framebuffer;
	
	int width;
	int height;
	
	TextureBuffer(int width, int height, int filter) throws RuntimeException {
		/*fbo = new FrameBuffer();
		
		tex = new Texture2D(width, height, filter);
		*/
		this.width = width;
		this.height = height;
		buffer = new ObjectBuffer();
/*
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tex.getID(), 0);
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("Buffer creation failed.");
		}*/
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width,
		height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
		// Create a depth buffer for our framebuffer
		renderbuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, renderbuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32F,
		width, height);
		// Attach the texture and depth buffer to the framebuffer
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebuffer);
		glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER,
		GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);
		glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER,
		GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderbuffer);
		glEnable(GL_DEPTH_TEST);
		
	}
	
	@Override
	ObjectBuffer getObjectBuffer() {
		return buffer;
	}
	
	void bind() {
		//fbo.bind(GL_FRAMEBUFFER);
		//buffer = null;
	}
	
	@Override
	void destroy() {
		//fbo.destroy();
		//tex.destroy();
	}
	
	public int getTexture() {
		//return tex;
		return texture;
	}

	@Override
	void render() {
		/*
		fbo.bind(GL_DRAW_FRAMEBUFFER);
		fbo.unbind(GL_READ_FRAMEBUFFER);
		
		System.out.println("rendering to texture");
		
		//if (flag != null) {	
			glClearColor(0,0,0,1); // Color to clear with
	
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//}

		buffer.render();
		
		fbo.bind(GL_READ_FRAMEBUFFER);
		fbo.unbind(GL_DRAW_FRAMEBUFFER);
		*/
		
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebuffer);
		glViewport(0, 0, width, height);
		glClearColor(1,1,1,1);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		buffer.render();
		//...
		//Generate mipmaps of our texture
		glGenerateMipmap(GL_TEXTURE_2D);
		glEnable(GL_TEXTURE_2D);
		
//		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
//
//		//glEnable(GL_BLEND);
//		glBlendFunci(fbo.getFBO(), GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glBlitFramebuffer(0, 0, tex.width(), tex.height(), 0, 0, tex.width(), tex.height(),
//			GL_COLOR_BUFFER_BIT, GL_NEAREST);	
	}
}
