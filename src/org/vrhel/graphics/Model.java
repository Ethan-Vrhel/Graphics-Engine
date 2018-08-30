package org.vrhel.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 * The <code>Model</code> class represents a
 * drawable VBO.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class Model implements Cloneable {
	
	public static final int X_AXIS = 0,
							Y_AXIS = 1;

	private int draw_count;
	private int v_id;
	private int t_id;
	
	private int i_id;
	
	private int width;
	private int height;
	
	private float[] tex_coords;
	
	//private float[] verticies;
	
	/**
	 * Creates a new <code>Model</code> with vertex coordinates.
	 * 
	 * @param vertices The vertices of the <code>Model</code>.
	 * @param tex_coords The texture coordinates.
	 * @param indicies The indices.
	 */
	public Model(float[] vertices, float[] tex_coords, int[] indicies) {
		draw_count = indicies.length;
		
		//verticies = copy(verticies);
		
		v_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		// GL_STATIC_DRAW or GL_DYNAMIC_DRAW
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
		
		t_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, t_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(tex_coords), GL_STATIC_DRAW);
		
		i_id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		
		IntBuffer buffer = BufferUtils.createIntBuffer(indicies.length);
		buffer.put(indicies);
		buffer.flip();
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Returns the width of the model.
	 * 
	 * @return The width.
	 */
	public int width() {
		return width;
	}
	
	/**
	 * Returns the height of the model.
	 * 
	 * @return The height.
	 */
	public int height() {
		return height;
	}
	
	/**
	 * Rotates the model.
	 * 
	 * @param angle The angle to rotate..
	 * @param xaxis Specifies the x axis to rotate around.
	 * Must be in the range of 0 to 1.
	 * @param yaxis Specifies the y axis to rotate around.
	 * Must be in the range of 0 to 1.
	 * @param zaxis Specifies the z axis to rotate around.
	 * Must be in the range of 0 to 1.
	 */
	public void rotate(float angle, float xaxis, float yaxis, float zaxis) {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glRotatef(angle, xaxis, yaxis, zaxis);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	/**
	 * Translates the model.
	 * 
	 * @param x The x translation.
	 * @param y The y translation.
	 * @param z The z tanslation.
	 */
	public void translate(float x, float y, float z) {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glTranslatef(x, y, z);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	void render() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, t_id);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
