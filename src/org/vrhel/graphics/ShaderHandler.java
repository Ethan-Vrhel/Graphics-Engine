package org.vrhel.graphics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The <code>ShaderHandler</code> class handles
 * the combination of shaders.
 * 
 * @author Ethan Vrhel
 * @deprecated The <code>Shader</code> class allows multiple
 * shaders.
 * @since 1.2
 */
@Deprecated
public class ShaderHandler {

	private static ShaderHandler handler;

	private File defaultFragmentShader;
	private File defaultVertexShader;

	@SuppressWarnings("unused")
	private File newFragmentShader;
	@SuppressWarnings("unused")
	private File newVertexShader;

	private ArrayList<AbstractShader> shaderStack;

	private ShaderHandler(String defaultShader, String newShader) {
		this.defaultFragmentShader = new File(defaultShader + ".fs");
		this.defaultVertexShader = new File(defaultShader + ".vs");

		this.newFragmentShader = new File(newShader + ".fs");
		this.newVertexShader = new File(newShader + ".vs");

		this.shaderStack = new ArrayList<AbstractShader>();
	}

	static void createHandler(String defaultShader, String newShader) {
		if (handler != null)
			throw new UnsupportedOperationException("Shader handler already exists");
		handler = new ShaderHandler(defaultShader, newShader);
	}

	static void destroy() {
		if (handler != null) {
			handler.cleanup();
			handler = null;
		}
	}

	private void cleanup() {
		for (int i = 0; i < shaderStack.size(); i++) {
			shaderStack.set(i, null);
		}
		shaderStack = null;
	}

	/**
	 * Adds a shader to the stack.
	 * 
	 * @param shader The shader to add to the stack.
	 */
	public void addShader(AbstractShader shader) {
		if (! shaderStack.contains(shader)) {
			shaderStack.add(shader);
		}
	}

	/**
	 * Applies the current shader stack.
	 */
	public void apply() {
		if (! defaultFragmentShader.exists() || ! defaultVertexShader.exists()) {
			throw new IllegalArgumentException("No default shader exists.");
		}
		
	}
	
	@SuppressWarnings("unused")
	private static class ShaderField {
		
		private String[] fieldTypes;
		
		ShaderField(String[] fields) {
			this.fieldTypes = fields;
		}
	}
	
	@SuppressWarnings("unused")
	private static class ShaderMethod {
		
		private String methodName;
		private String[] methodArgTypes;
		
		ShaderMethod(String methodName, String[] methodArgTypes) {
			this.methodName = methodName;
			this.methodArgTypes = methodArgTypes;
		}
	}
	
	@SuppressWarnings("unused")
	private void read() throws IOException {
		
	}
}
