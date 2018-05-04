package org.vrhel.graphics;

/**
 * A <code>UseableShader</code> wraps multiple
 * <code>AbstractShaders</code> into a class to configure
 * them together.  This allows for someone to configure
 * multiple values within a vertex shader or a 
 * fragment shader.  This class will always contain
 * the default shader and the code for the default shader
 * must be in each shader file.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public final class UseableShader extends GraphicsObject {
	
	public static final int DEFAULT_SHADER_LOCATION = 0;

	private AbstractShader[] stack;
	
	/**
	 * Creates a new <code>UseableShader</code> from
	 * an array of <code>AbstractShaders</code>.
	 * If <code>stack</code> is equal to <code>null</code> 
	 * the shading stack will be populated with only 
	 * the default shader.
	 * 
	 * @param stack The stack to be used.
	 */
	public UseableShader(AbstractShader[] stack) {
		if (stack == null) {
			this.stack = new AbstractShader[1];
			this.stack[0] = new DefaultShader(new TransformData(0, 0, 0, 1));
		} else {
			this.stack = new AbstractShader[stack.length];
		}
	}
	
	/**
	 * Creates a new <code>UseableShader</code> from
	 * a <code>TransformData</code> object.
	 * 
	 * @param data The transformation data.
	 */
	public UseableShader(TransformData data) {
		if (data == null)
			data = new TransformData(0, 0, 0, 1);
		this.stack = new AbstractShader[1];
		this.stack[0] = new DefaultShader(data);
	}
	
	/**
	 * Gets the shader of index <code>i</code> out
	 * of the stack.
	 * 
	 * @param i The index.
	 * @return The associated <code>AbstractShader</code>.
	 */
	public AbstractShader get(int i) {
		return stack[i];
	}
	
	/**
	 * Returns the default shader inside the stack.
	 * 
	 * @return The default shader.
	 */
	public DefaultShader getDefault() {
		return (DefaultShader) stack[DEFAULT_SHADER_LOCATION];
	}
	
	/**
	 * Returns the length of the stack.
	 * 
	 * @return The length of the stack.
	 */
	public int stackLength() {
		return stack.length;
	}
	
	void bind() {
		stack[0].bind();
	}
	
	void configure() {
		for (int i = 0; i < stack.length; i++) {
			stack[i].configure();
		}
	}
	
	@Override
	public String toString() {
		return getClass() + " (contains " + stackLength() + " shaders)";
	}

	@Override
	void destroy() {
		stack = null;
	}
}
