package org.vrhel.graphics;

/**
 * An <code>AbstractShader</code> is a class that
 * contains a <code>Shader</code> which can be used
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public abstract class AbstractShader extends GraphicsObject {

	/**
	 * The bound shader.
	 */
	protected Shader shader;
	
	/**
	 * Creates a new <code>AbstractShader</code> with
	 * a <code>Shader</code>.
	 * 
	 * @param shader The <code>Shader</code>.
	 */
	public AbstractShader(Shader shader) {
		if (shader == null)
			throw new IllegalArgumentException("Shader cannot be null.");
		this.shader = shader;
	}
	
	final void bind() {
		shader.bind();
	}
	
	@Override
	final void destroy() {
		shader = null;
	}
	
	/**
	 * Configures the shader before rendering.
	 */
	public abstract void configure();
}
