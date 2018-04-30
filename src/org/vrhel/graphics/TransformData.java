package org.vrhel.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * The <code>TransformData</code> class contains
 * positional as well as scaling information.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class TransformData implements Cloneable {

	private Matrix4f proj;
	
	private Vector3f pos;
	private float scale;
	
	/**
	 * Creates new transformation data.
	 * 
	 * @param x The x position.
	 * @param y The y position.
	 * @param z The z position.
	 * @param scale The scale.
	 */
	public TransformData(float x, float y, float z, float scale) {
		this.pos = new Vector3f(x, y, z);
		this.scale = scale;
		setTransform(this.pos, scale);
	}
	
	/**
	 * Returns the associated x position.
	 * 
	 * @return The x position.
	 */
	public float getX() {
		return pos.x;
	}
	
	/**
	 * Returns the associated x position.
	 * 
	 * @return The y position.
	 */
	public float getY() {
		return pos.y;
	}
	
	/**
	 * Returns the associated x position.
	 * 
	 * @return The z position.
	 */
	public float getZ() {
		return pos.z;
	}
	
	/**
	 * Returns the scaling of this transformation.
	 * 
	 * @return The scaling.
	 */
	public float getScale() {
		return scale;
	}
	
	/**
	 * Sets the transformation info.
	 * 
	 * @param x The x position.
	 * @param y The y position.
	 * @param z The z position.
	 * @param scale The scale.
	 */
	public void setTransform(float x, float y, float z, float scale) {
		setTransform(new Vector3f(x, y, z), scale);
	}
	
	/**
	 * Sets the transformation info.
	 * 
	 * @param pos The position.
	 * @param scale The scale.
	 */
	public void setTransform(Vector3f pos, float scale) {
		this.pos = pos;
		this.proj = new Matrix4f()
				.translate(pos)
				.scale(scale);
	}
	
	@Override
	public TransformData clone() {
		return new TransformData(pos.x, pos.y, pos.z, scale);
	}
	
	@Override
	public String toString() {
		return "Transform: " + pos.x + ", " + pos.y + ", " + pos.z + " scale: " + scale;
	}
	
	final Matrix4f getTransform() {
		return proj;
	}
}
