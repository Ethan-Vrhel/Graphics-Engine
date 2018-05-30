package org.vrhel.graphics;

/**
 * A <code>RenderListener</code> is an interface that
 * has a method that is called each frame before rendering.
 * 
 * @author Ethan Vrhel
 * @since 1.1
 */
@FunctionalInterface
public interface RenderListener {

	/**
	 * Called before rendering.
	 */
	public void update();
}
