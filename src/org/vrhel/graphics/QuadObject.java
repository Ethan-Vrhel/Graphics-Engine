package org.vrhel.graphics;

import static org.lwjgl.opengl.GL11.*;

/**
 * A <code>QuadObject</code> is an object that can
 * be drawn by OpenGL.  This uses a quad to draw
 * a 2D texture.  A <code>QuadObject</code> is not
 * affected by the camera.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public final class QuadObject extends RenderableObject {

	private Texture2D texture;
	
	private TransformData data;
	private float width;
	private float height;
	
	/**
	 * Creates a new <code>RenderableObject</code> from
	 * a texture.
	 * 
	 * @param texture The texture.
	 * @param zBuffer The z-buffer of the object.
	 * @param data The transformation data.
	 * @param width The width of the object.
	 * @param height The height of the object.
	 */
	public QuadObject(Texture2D texture, int zBuffer, TransformData data, float width, float height) {
		super(zBuffer, texture.width(), texture.height());
		this.texture = texture;
		this.data = data;
		this.width = width;
		this.height = height;
		ObjectBuffer.getBuffer().add(this);
	}
	
	@Override
	public RenderableObject clone() {
		QuadObject q = new QuadObject(texture, getZBuffer(), data.clone(), width, height);
		return q;
	}

	@Override
	void render() {
		texture.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(data.getX(), data.getY());
			glTexCoord2f(0, 1);
			glVertex2f(data.getX(), data.getY() - height);
			glTexCoord2f(1, 1);
			glVertex2f(data.getX() - width, data.getY() - height);
			glTexCoord2f(1, 0);
			glVertex2f(data.getX() + width, data.getY());
		glEnd();
	}

	@Override
	void destroy() {
		texture = null;
		data = null;
	}
}
