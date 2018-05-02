package org.vrhel.graphics;

/**
 * The <code>VBOObjectFactory</code> handles the
 * creation of <code>VBOObjects</code> easily.
 * 
 * @author Ethan Vrhel
 * @since 1.1
 */
public final class VBOObjectFactory {

	private VBOObjectFactory() { }
	
	/**
	 * Creates a new <code>VBOObject</code>.
	 * 
	 * @param model The <code>Model</code>.
	 * @param zBuffer The z-buffer.
	 * @param tex The texture.
	 * @param shader The shader.
	 * @return The new <code>VBOObject</code>.
	 */
	public static VBOObject newObject(Model model, int zBuffer, Texture2D tex, UseableShader shader) {
		VBOObject obj = new VBOObject(model, zBuffer, tex, shader);
		return obj;
	}
	
	/**
	 * Creates a new <code>VBOObject</code>.
	 * 
	 * @param width The width.
	 * @param height The height.
	 * @param zBuffer The z-buffer.
	 * @param tex The texture.
	 * @param shader The shader.
	 * @param transform The texture transformation.
	 * @return The new <code>VBOObject</code>.
	 */
	public static VBOObject newObject(float width, float height, int zBuffer, Texture2D tex, UseableShader shader, TextureTransform transform) {
		Model model = ModelFactory.newModel(width, height, transform);
		return newObject(model, zBuffer, tex, shader);
	}
	
	/**
	 * Creates a new <code>VBOObject</code>.
	 * 
	 * @param model The <code>Model</code>.
	 * @param zBuffer The z-buffer.
	 * @return The new <code>VBOObject</code>.
	 */
	public static VBOObject newObject(Model model, int zBuffer) {
		return newObject(model, zBuffer, null, null);
	}
	
	/**
	 * Creates a new <code>VBOObject</code>.
	 * 
	 * @param width The width.
	 * @param height The height.
	 * @param zBuffer The z-buffer.
	 * @return The new <code>VBOObject</code>.
	 */
	public static VBOObject newObject(float width, float height, int zBuffer) {
		return newObject(width, height, zBuffer, null, null, null);
	}
	
	/**
	 * Creates a new <code>VBOObject</code>.
	 * 
	 * @return The new <code>VBOObject</code>.
	 */
	public static VBOObject newObject() {
		return newObject(1f, 1f, 0, null, null, null);
	}
}
