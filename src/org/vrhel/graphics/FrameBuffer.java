package org.vrhel.graphics;

import org.lwjgl.opengl.GL14;
 
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;

final class FrameBuffer {

	private int fbo;
	private int tex;
	private int depth;
	
	FrameBuffer(int width, int height, int border, int filter) {
		fbo = glGenFramebuffersEXT();
		tex = glGenTextures();
		depth = glGenRenderbuffersEXT();
		
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fbo);                        // switch to the new framebuffer
	     
        // initialize color texture
        glBindTexture(GL_TEXTURE_2D, tex);                                   // Bind the colorbuffer texture
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);               // make it linear filterd
        WindowProperties prop = GraphicsEngine.getEngine().getConfiguration().getWindowProperties();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, prop.getResolution().width, prop.getResolution().height, border, GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);  // Create the texture data
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, tex, 0); // attach it to the framebuffer

        // initialize depth renderbuffer
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depth);                // bind the depth renderbuffer
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, width, height); // get the data space for it
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, depth); // bind it to the renderbuffer
 
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);                                    // Swithch back to normal framebuffer rendering
	}
	
	void bind() {
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fbo);
	}
	
	void unbind() {
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
	}
	
	int getFBO() {
		return fbo;
	}
}
