package org.vrhel.graphics;

/**
 * Shape parameters define what the <code>Shape</code>
 * must use to create a new <code>Shape</code>.
 * 
 * @author Ethan Vrhel
 * @since 1.1
 */
@Useless
public abstract class ShapeParameters {

	/**
	 * Checks if parameters <code>check</code> is the right parameters
	 * for the shape <code>shape</code>.
	 * 
	 * @param shape The shape.
	 * @param check The parameters.
	 * @return <code>true</code> if it is valid and <code>false</code>
	 * otherwise.
	 */
	public static final boolean valid(Shape shape, ShapeParameters check) {
		return shape.valid(check.getClass());
	}
}
