package org.vrhel.graphics;

/**
 * A <code>Shape</code> is used to convert a
 * <code>ShapeParameters</code> object to <code>float</code>
 * and <code>int</code> arrays that is acceptable for
 * a <code>Model</code>.
 * 
 * @author Ethan Vrhel
 * @since 1.1
 */
@Useless
public abstract class Shape {
	
	private Class<? extends ShapeParameters> required;
	
	/**
	 * Creates a new <code>Shape</code> with the required
	 * parameters.
	 * 
	 * @param required The required parameters.
	 */
	public Shape(Class<? extends ShapeParameters> required) {
		this.required = required;
	}
	
	/**
	 * Gets the parameters that this <code>Shape</code> must
	 * take.
	 * 
	 * @return The required parameters.
	 */
	public final Class<? extends ShapeParameters> getRequiredParameters() {
		return required;
	}
	
	/**
	 * Checks if the class <code>check</code> matches the
	 * required parameters of this shape.
	 * 
	 * @param check The class to check.
	 * @return <code>true</code> if the class is valid and
	 * <code>false</code> otherwise.
	 */
	public final boolean valid(Class<?> check) {
		return check.equals(required);
	}

	/**
	 * Converts the parameters to a <code>float</code> array.
	 * 
	 * @param param The shape parameters.
	 * @return The new <code>float</code> array.
	 * @throws IllegalArgumentException When the parameters are
	 * invalid.
	 */
	public abstract float[] toVertices	(ShapeParameters param) throws IllegalArgumentException;
	
	/**
	 * Converts the parameters to a <code>float</code> array.
	 * 
	 * @param param The shape parameters.
	 * @return The new <code>float</code> array.
	 * @throws IllegalArgumentException When the parameters are
	 * invalid.
	 */
	public abstract float[] toTexture	(ShapeParameters param) throws IllegalArgumentException;
	
	/**
	 * Converts the parameters to an <code>int</code> array.
	 * 
	 * @param param The shape parameters.
	 * @return The new <code>int</code> array.
	 * @throws IllegalArgumentException When the parameters are
	 * invalid.
	 */
	public abstract int[]	toIndicies	(ShapeParameters param)	throws IllegalArgumentException;
}
