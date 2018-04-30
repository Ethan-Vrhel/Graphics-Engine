package org.vrhel.graphics;

/**
 * A <code>VBOObject</code> is a <code>RenderableObject</code>
 * that uses VBOs to draw itself.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public final class VBOObject extends RenderableObject {

	private Model model;
	private Texture2D tex;
	private UseableShader shader;
	
	/**
	 * Creates a new <code>VBOObject</code> from a <code>Model</code>.
	 * 
	 * @param model The model to use.
	 * @param zBuffer The z-buffer of the object.
	 */
	public VBOObject(Model model, int zBuffer) { 
		this(model, zBuffer, null, null);
	}
	
	/**
	 * Creates a new textured <code>VBOOBject</code>.
	 * 
	 * @param model The model to use.
	 * @param zBuffer The z-buffer of the object.
	 * @param tex The texture to use.
	 * @param shader The shader to use.
	 */
	public VBOObject(Model model, int zBuffer, Texture2D tex, UseableShader shader) {
		super(zBuffer, model.width(), model.height());
		this.model = model;
		this.tex = tex;
		this.shader = shader;
	}
	
	/**
	 * Gets the bound shader.
	 * 
	 * @return The bound shader.
	 */
	public UseableShader getShader() {
		return shader;
	}

	@Override
	public VBOObject clone() {
		return new VBOObject(model, getZBuffer(), tex, shader);
	}

	@Override
	void render() {
		if (tex == null) {
			model.render();
		} else {
			if (shader != null) {
				shader.configure();
				tex.bind();
				shader.bind();
			} else {
				tex.bind();
			}
			model.render();
		}
	}
}
