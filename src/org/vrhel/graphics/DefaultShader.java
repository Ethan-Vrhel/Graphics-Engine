package org.vrhel.graphics;

import org.joml.Matrix4f;

/**
 * The <code>DefaultShader</code> wraps the default
 * shader into an <code>AbstractShader</code>.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class DefaultShader extends AbstractShader {

	private TransformData data;
	
	/**
	 * Creates a new <code>DefaultShader</code> with
	 * transformation data.
	 * 
	 * @param data The transformation data.
	 */
	public DefaultShader(TransformData data) {
		super(Shader.defaultShader);
		this.data = data;
	}
	
	/**
	 * Returns the <code>TransformData</code> associated
	 * with this shader.
	 * 
	 * @return The associated <code>TransformData</code>.
	 */
	public TransformData getTransform() {
		return data;
	}

	@Override
	public void configure() {
		Matrix4f target = data.getTransform();
		if (GraphicsEngine.getEngine().getConfiguration().getType() == GraphicsConfiguration.SuggestedType.Dimension2)
			shader.setUniform("type", 0);
		else
			shader.setUniform("type", 1	);
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", GraphicsWindow.getWindow().camera.projection().mul(target));
	}
}
