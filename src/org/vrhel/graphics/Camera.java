package org.vrhel.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

abstract class Camera { 
	
	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Matrix4f projection;
	
	Camera() { }
	
	Camera(Vector3f position) {
		this.position = position;
	}
	
	final void setPositon(Vector3f position) {
		this.position = position;
	}
	
	final void addPositon(Vector3f position) {
		this.position.add(position);
	}
	
	final Vector3f getPosition() { return position; }
	
	/**
	 * Projects the <code>Camera</code> to a 
	 * matrix.
	 * 
	 * @return The projected matrix.
	 */
	abstract Matrix4f projection();
}
