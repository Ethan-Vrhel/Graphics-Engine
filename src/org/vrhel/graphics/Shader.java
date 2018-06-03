package org.vrhel.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {

	static final Shader defaultShader = new Shader(new String[] {"shader"});
	
	private int program;
	private int vs;
	private int fs;
	
	/**
	 * Creates a new <code>Shader</code> from a 
	 * file.
	 * 
	 * @param filename The file name index <code>0</code>
	 * must be the <code>defaultShader</code>.
	 */
	public Shader(String[] files) {
		if (GraphicsEngine.getEngine() == null)
			throw new UnsupportedOperationException("Engine has not been initialized");
		
		program = glCreateProgram();
		for (int i = 0; i < files.length; i++) {
			vs = glCreateShader(GL_VERTEX_SHADER);
			glShaderSource(vs, readFile(files[i] + ".vs"));
			glCompileShader(vs);
			if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
				System.err.println(glGetShaderInfoLog(vs));
				System.exit(1);
			}
			
			fs = glCreateShader(GL_FRAGMENT_SHADER);
			glShaderSource(fs, readFile(files[i] + ".fs"));
			glCompileShader(fs);
			if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
				System.err.println(glGetShaderInfoLog(fs));
				System.exit(1);
			}
			
			glAttachShader(program, vs);
			glAttachShader(program, fs);
			
			glBindAttribLocation(program, 0, "vertices");
			glBindAttribLocation(program, 1, "textures");
		}
		
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		glValidateProgram(program);
		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
	}
	
	/**
	 * Sets a uniform value.
	 * 
	 * @param name The uniform name.
	 * @param value The value.
	 */
	public final void setUniform(String name, int value) {
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform1i(location, value);
	}
	
	/**
	 * Sets a uniform value.
	 * 
	 * @param name The uniform name.
	 * @param value The value.
	 */
	public final void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		if (location != -1)
			glUniformMatrix4fv(location, false, buffer);
	}
	
	final void bind() {
		glUseProgram(program);
	}
	
	private final String readFile(String filename) {
		StringBuilder string = new StringBuilder();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(new File(".//shaders//" + filename)));
			String line;
			while ((line = in.readLine()) != null) {
				string.append(line);
				string.append("\n");
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string.toString();
	}
}
