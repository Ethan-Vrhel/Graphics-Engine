package org.vrhel.graphics;

import java.util.ArrayList;

/**
 * A <code>GraphicsObject</code> is an object that
 * directly relates to the process of the renderer.
 * 
 * @author Ethan Vrhel
 * @since 1.1
 */
public abstract class GraphicsObject {

	private static ArrayList<GraphicsObject> buffer;
	
	/**
	 * Creates a new <code>GraphicsObject</code>.
	 */
	public GraphicsObject() {
		buffer.add(this);
	}
	
	static void create() {
		buffer = new ArrayList<GraphicsObject>();
	}
	
	static void destroyAll() {
		for (int i = 0; i < buffer.size(); i++) {
			buffer.get(i).destroy();
		}
		buffer = null;
	}
	
	/**
	 * Destroys this object safely.
	 */
	abstract void destroy();
}
