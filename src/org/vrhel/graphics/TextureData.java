package org.vrhel.graphics;

/**
 * The <code>TextureData</code> class contains information
 * for rendering VBOs.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class TextureData {

	private float[] tex;
	private float[] orig_tex;
	private float scale;
	private float offset_x;
	private float offset_y;
	
	/**
	 * Creates a new texture data
	 * 
	 * @param tex The original texture array.
	 * @param scale The texture scale.
	 * @param offset_x The texture x offset.
	 * @param offset_y The texture y offset.
	 */
	public TextureData(float[] tex, float scale, float offset_x, float offset_y) {
		this.tex = tex;
		this.orig_tex = tex;
		this.scale = scale;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
	}
	
	void transform() { 
		for (int i = 0; i < tex.length; i += 2) {
			tex[i] = (orig_tex[i] + offset_x) / scale;
			tex[i + 1] = (orig_tex[i + 1] + offset_y) / scale;
		}
	}
	
	float[] getTextureData() {
		return tex;
	}
	
	/**
	 * Returns the scale of the texture.
	 * 
	 * @return The scale.
	 */
	public float getScale() {
		return scale;
	}
	
	/**
	 * Returns the x offset of the texture.
	 * 
	 * @return The x offset.
	 */
	public float getOffsetX() {
		return offset_x;
	}
	
	/**
	 * Returns the y offset of the texture.
	 * 
	 * @return The y offset.
	 */
	public float getOffsetY() {
		return offset_y;
	}
}
