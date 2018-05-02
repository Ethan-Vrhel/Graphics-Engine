package org.vrhel.graphics;

/**
 * The <code>ModelFactory</code> handles the
 * creation of <code>Models</code> easily.
 * 
 * @author Ethan Vrhel
 * @since 1.1
 */
public class ModelFactory {

	private ModelFactory() { }
	
	/**
	 * Creates a new <code>Model</code>.
	 * 
	 * @param width The width.
	 * @param height The height.
	 * @param transform The texture transformation.
	 * @return A new <code>Model</code>.
	 */
	public static Model newModel(float width, float height, TextureTransform transform) {
		float[] vertices = new float[] {
			-width / 2, height / 2, 0,	// 0
			width / 2, height / 2, 0,	// 1
			width / 2, -height / 2, 0,	// 2
			-width / 2, -height / 2, 0	// 3
		};
		
		float[] texture = new float[] {
			0, 0,
			1, 0,
			1, 1,
			0, 1
		};
		
		if (transform != null) {
			TextureData data = new TextureData(texture, transform.scale, transform.offset_x, transform.offset_y);
			data.transform();
		}
		
		int[] indicies = new int[] {
			0, 1, 2,
			2, 3, 0
		};
		
		Model model = new Model(vertices, texture, indicies);
		return model;
	}
	
	/**
	 * Creates a new <code>Model</code>.
	 * 
	 * @param shape The shape.
	 * @param param The shape parameters.
	 * @return The new <code>Model</code>.
	 */
	public static Model newModel(Shape shape, ShapeParameters param) {
		float[] vertices = shape.toVertices(param);
		float[] texture = shape.toTexture(param);
		int[] indicies = shape.toIndicies(param);
		return new Model(vertices, texture, indicies);
	}
}
