package org.vrhel.graphics;

/**
 * The <code>GraphicsConfiguration</code> class is
 * used to hint on how the grahpics engine will
 * behave.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class GraphicsConfiguration {

	private SuggestedType type;
	private WindowProperties properties;
	
	/**
	 * Creates a new <code>GraphicsConfiguration</code>.
	 * 
	 * @param windowName The text to display on the window.
	 * @param size The resolution of the application window.
	 * @param type The <code>SuggestedType</code>.
	 * @throws IllegalArgumentException When the dimension has invalid values.
	 */
	public GraphicsConfiguration(WindowProperties properties, SuggestedType type) throws IllegalArgumentException {
		this.properties = properties;
		if (this.properties == null)
			this.properties = new WindowProperties();
		this.type = type;
	}
	
	/**
	 * Creates a default configuration.
	 */
	public GraphicsConfiguration() {
		this(null, SuggestedType.Dimension2);
	}
	
	public WindowProperties getWindowProperties() {
		return properties;
	}
	
	/**
	 * Returns get associated <code>SuggestedType</code>
	 * with this configuration.
	 * 
	 * @return The <code>SuggestedType</code>.
	 */
	public SuggestedType getType() {
		return type;
	}

	/**
	 * Enumerated types which suggest what mode the
	 * renderer will use.
	 * 
	 * @author Ethan Vrhel
	 * @since 1.0
	 */
	public static enum SuggestedType {
		/**
		 * Suggests that the graphics
		 * engine should be using 2D
		 * graphics.
		 */
		Dimension2,
		
		/**
		 * Suggests that the graphics
		 * engine should be using 3D
		 * graphics.
		 */
		Dimension3;
	}
	
	/**
	 * Enumerated types that specify the
	 * rendering path the engine should take.
	 * 
	 * @author Ethan Vrhel
	 * @since 1.1
	 */
	@Useless
	public static enum RenderingType {
		/**
		 * Specifies forward rendering.
		 */
		Forward,
		
		/**
		 * Specifies deferred rendering.
		 */
		Deferred;
	}
}
