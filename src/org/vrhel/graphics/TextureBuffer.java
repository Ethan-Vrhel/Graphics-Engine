package org.vrhel.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.glBlendFunci;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL42;

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
	private Texture2D tex;
	private int renderbuffer;
	private int framebuffer;
	
	private int width;
	private int height;
	
	//private FrameBuffer fbo;
	//private RenderBuffer rbo;
	
	TextureBuffer(int width, int height, int filter) throws RuntimeException {		
		this.width = width;
		this.height = height;
		this.buffer = new ObjectBuffer();
		
		
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);

		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		
		tex = new Texture2D(width, height, texture, -1);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
			
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture, 0);
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Failed to configure framebuffer: " + framebuffer);
		}
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
	
	/**
	 * Gets the texture of this buffer.
	 * 
	 * @return The texture.
	 */
	public Texture2D getTexture() {
		return tex;
	}

	@Override
	void render() {
		//System.out.println("calling render()");
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebuffer);
		
		// Sets the viewport to be relative to the texture
		glViewport(0, 0, width, height);

		glClearColor(1,0,0,0.5f);
		glClear(GL_COLOR_BUFFER_BIT);
		
		//System.out.println("drew to framebuffer: " + framebuffer);
		//glGenerateMipmap(GL_TEXTURE_2D);
		//glEnable(GL_TEXTURE_2D);
		buffer.render();
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		
		// Resets the viewport
		GraphicsWindow.getWindow().viewport();

	}
}
