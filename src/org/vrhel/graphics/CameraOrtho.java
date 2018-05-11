package org.vrhel.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * An orthographic camera.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class CameraOrtho extends Camera {
	
	/**
	 * Creates a new <code>CameraOrtho</code> of
	 * width <code>width</code> and height
	 * <code>height</code>.
	 * 
	 * @param width The width.
	 * @param height The height.
	 */
	public CameraOrtho(int width, int height) {
		projection = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height / 2, height / 2);		
	}
	
	@Override
	Matrix4f projection() {
		Matrix4f target = new Matrix4f();
		Matrix4f pos = new Matrix4f().setTranslation(position);
		
		target = projection.mul(pos, target);
		return target;
	}
}
