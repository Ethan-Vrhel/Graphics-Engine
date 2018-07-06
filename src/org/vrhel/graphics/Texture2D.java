package org.vrhel.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

/**
 * The <code>Texture</code> is a class representing
 * a 2D image.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public final class Texture2D extends Texture {

	private int id;
	private int width;
	private int height;
	private int filter;
	
	private boolean transparent;

	/**
	 * Creates a new texture from a file name.
	 * 
	 * @param filename The file name.
	 * @param filter The filter to use.  Specified as
	 * <code>Texture.LINEAR</code> or <code>Texture.NEAREST</code>.
	 * @throws IOException When an I/O exception occurs.
	 */
	public Texture2D(String filename, int filter) throws IOException {
		this(ImageIO.read(new File(filename)), filter);
	}
	
	/**
	 * Creates a new texture from an <code>BufferedImage</code>.
	 * 
	 * @param bi The original image.
	 * @param filter The filtering mode of the texture.  Specified
	 * as <code>Texture.LINEAR</code> or <code>Texture.NEAREST</code>.
	 */
	public Texture2D(BufferedImage bi, int filter) {
		width = bi.getWidth();
		height = bi.getHeight();
		this.filter = filter;
		transparent = bi.getColorModel().hasAlpha();
			
		
		int[] pixels_raw = new int[width * height];
		pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
		ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int pixel = pixels_raw[i * width + j];
				pixels.put((byte) ((pixel >> 16) & 0xFF));	// Red
				pixels.put((byte) ((pixel >> 8) & 0xFF));	// Green
				pixels.put((byte) ((pixel >> 0) & 0xFF)); 	// Blue
				pixels.put((byte) ((pixel >> 24) & 0xFF)); 	// Alpha	
			}
		}
		
		pixels.flip();
		id = glGenTextures();
		bind();
		// GL_LINEAR or GL_NEAREST
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		
		//glDepthMask(GL_FALSE);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
	}
	
	public Texture2D(int id) {
		this.id = id;
	}
	
	/**
	 * Creates a blank texture.
	 * 
	 * @param width The width.
	 * @param height The height.
	 */
	public Texture2D(int width, int height, int filter) {
		this.width = width;
		this.height = height;
		id = glGenTextures();
		bind();
		// GL_LINEAR or GL_NEAREST
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		
		//glDepthMask(GL_FALSE);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
	}
	
	@Override
	public int width() {
		return width;
	}
	
	@Override
	public int height() {
		return height;
	}
	
	@Override
	public int filter() {
		return filter;
	}
	
	/**
	 * Returns the state of transparency held by this
	 * texture.
	 * 
	 * @return <code>true</code> if it is transparent
	 * and <code>false</code> otherwise.
	 */
	public boolean transparent() {
		return transparent;
	}
	
	void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}
	
	void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
}
