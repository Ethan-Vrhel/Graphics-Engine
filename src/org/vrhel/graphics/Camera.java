package org.vrhel.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * A <code>Camera</code> projects the current buffer
 * to a <code>Matrix4f</code>.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public abstract class Camera { 
	
	protected Vector3f position;
	protected Matrix4f projection;
	
	/**
	 * Creates a new <code>Camera</code> at
	 * <code>(0, 0, 0)</code>.
	 */
	public Camera() { 
		position = new Vector3f(0, 0, 0);
	}
	
	/**
	 * Creates a new <code>Camera</code>.
	 * 
	 * @param position The position.
	 */
	public Camera(Vector3f position) {
		this.position = position;
	}
	
	/**
	 * Sets the position of the camera.
	 * 
	 * @param position The position.
	 */
	public final void setPositon(Vector3f position) {
		this.position = position;
	}
	
	/**
	 * Sets the position of the camera.
	 * 
	 * @param x The x position.
	 * @param y The y position.
	 * @param z The z position.
	 */
	public final void setPosition(float x, float y, float z) {
		this.position = new Vector3f(x, y, z);
	}
	
	/**
	 * Adds a position.
	 * 
	 * @param position The addition.
	 */
	public final void addPositon(Vector3f position) {
		this.position.add(position);
	}
	
	/**
	 * Returns the position.
	 * 
	 * @return The position.
	 */
	public final Vector3f getPosition() { return position; }
	
	/**
	 * Projects the <code>Camera</code> to a 
	 * matrix.
	 * 
	 * @return The projected matrix.
	 */
	abstract Matrix4f projection();
}
