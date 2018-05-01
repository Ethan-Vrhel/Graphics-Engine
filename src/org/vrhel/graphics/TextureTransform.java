package org.vrhel.graphics;

/**
 * The <code>TextureTransform</code> class contains
 * information on texture transformation.
 * 
 * @author Ethan Vrhel
 * @since 1.1
 */
public class TextureTransform {

	float scale;
	float offset_x;
	float offset_y;
	
	/**
	 * Creates a new transformation.
	 * 
	 * @param scale The texture scale.
	 * @param offset_x The x offset.
	 * @param offset_y The y offset.
	 */
	public TextureTransform(float scale, float offset_x, float offset_y) {
		this.scale = scale;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
	}
	
	/**
	 * Creates the default transformation of
	 * scale 1, x offset 0, and y offset 0.
	 */
	public TextureTransform() {
		this(1f, 0f, 0f);
	}
}
