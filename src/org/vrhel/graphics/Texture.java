package org.vrhel.graphics;

import org.lwjgl.opengl.GL11;

/**
 * Signifies that something is a texture.
 * Also contains information for creating
 * textures.
 * 
 * @author Ethan Vrhel
 * @insce 1.0
 */
public abstract class Texture {
	
	/**
	 * Filtering mode.
	 */
	public static final int LINEAR	= GL11.GL_LINEAR,
							NEAREST	= GL11.GL_NEAREST;

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
