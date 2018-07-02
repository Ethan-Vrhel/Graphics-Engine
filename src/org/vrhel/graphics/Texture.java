package org.vrhel.graphics;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;

/**
 * Signifies that something is a texture.
 * Also contains information for creating
 * textures.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public abstract class Texture {
	
	/**
	 * Filtering mode.
	 */
	public static final int LINEAR	= GL_LINEAR,
							NEAREST	= GL_NEAREST;
	
	private static int id = 1;
	
	private String name;
	private int texid;
	
	/**
	 * Creates an unnamed texture.
	 */
	public Texture() { 
		name = "texid " + id;
		this.texid = id;
		id++;
		TextureHandler.getHandler().add(this);
	}
	
	/**
	 * Creates a named texture.
	 * 
	 * @param name The name.
	 */
	public Texture(String name) {
		this.name = name;
		this.texid = id;
	}
	
	/**
	 * Gets the name of this texture.
	 * 
	 * @return The name.
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * Gets the id of this texture.
	 * 
	 * @return The id.
	 */
	public final int getID() {
		return texid;
	}
	
	/**
	 * Destroys this texture.  This assumes that
	 * this texture is bound with OpenGL.
	 */
	protected void destroy() {
		glDeleteTextures(texid);
	}

	@Override
	public String toString() {
		return getClass() + ": " + name;
	}

	/**
	 * Returns the width of this texture.
	 * 
	 * @return The width.
	 */
	public abstract int width();
	
	/**
	 * Returns the height of this texture.
	 * 
	 * @return The height.
	 */
	public abstract int height();
	
	/**
	 * Returns the filtering mode of this texture.
	 * 
	 * @return The filtering mode.
	 */
	public abstract int filter();
}
