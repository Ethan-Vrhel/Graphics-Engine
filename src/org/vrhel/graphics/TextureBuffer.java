package org.vrhel.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.glBlendFunci;

import org.lwjgl.opengl.GL11;
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
	
	private int texture;
	private int renderbuffer;
	private int framebuffer;
	
	private int width;
	private int height;
	
	private FrameBuffer fbo;
	private RenderBuffer rbo;
	
	TextureBuffer(int width, int height, int filter) throws RuntimeException {
		//this.enabled = true;
		/*fbo = new FrameBuffer();
		
		tex = new Texture2D(width, height, filter);
		*/
		this.width = width;
		this.height = height;
		this.buffer = new ObjectBuffer();
/*
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tex.getID(), 0);
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("Buffer creation failed.");
		}*/
		
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width,
		height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
		// Create a color buffer for our framebuffer
		/*
		renderbuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, renderbuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_RGBA,
		width, height);
		// Attach the texture and color buffer to the framebuffer
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebuffer);
		glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER,
		GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);
		glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER,
		GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, renderbuffer);
		//glEnable(GL_DEPTH_TEST);
		 */
		
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);
		
		
		
		System.out.println("generated texture     : " + texture);
		System.out.println("generated framebuffer : " + framebuffer);
		//System.out.println("generated renderbuffer: " + renderbuffer);
		
		/*AbstractBufferHandler.getHandler().bind(id);
		VBOObject obj5 = VBOObjectFactory.newObject(1f, 1f, 0, new Texture2D(6), new UseableShader(new TransformData(0, 0, 0, 640)), new TextureTransform());
		obj5.setShouldDraw(true);
		AbstractBufferHandler.getHandler().bind(-1);*/
		
		// Testing clearing the texture with a color
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebuffer);
		
		// Sets the viewport to be relative to the texture
		glViewport(0, 0, width, height);
		
		glClearColor(1,0,0,0.5f);
		glClear(GL_COLOR_BUFFER_BIT);
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
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebuffer);
		
		// Sets the viewport to be relative to the texture
		glViewport(0, 0, width, height);
		
		glClearColor(1,0,0,1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		//sSystem.out.println("drew to framebuffer: " + framebuffer);
		//glGenerateMipmap(GL_TEXTURE_2D);
		//glEnable(GL_TEXTURE_2D);
		buffer.render();
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		
		// Resets the viewport
		GraphicsWindow.getWindow().viewport();
		
		
		
//		glEnable(GL_TEXTURE_2D);
//		
//		
//
//		glEnable(GL_BLEND);
//		glBlendFunci(framebuffer, GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glBlitFramebuffer(0, 0, width, height, 0, 0, width, height,
//			GL_COLOR_BUFFER_BIT, GL_NEAREST);	
	}
}
