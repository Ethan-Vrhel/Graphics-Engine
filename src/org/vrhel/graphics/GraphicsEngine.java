package org.vrhel.graphics;

import org.lwjgl.opengl.GL11;

/**
 * The core class of the Graphics Engine.  Everything for general
 * function goes through this class.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public final class GraphicsEngine {

	/**
	 * The default <code>GraphicsConfiguration</code>.
	 */
	public static final GraphicsConfiguration defaultConfig = new GraphicsConfiguration();
	
	private static GraphicsEngine engine;
	
	private GraphicsConfiguration config;
	private GraphicsWindow window;
	
	private GraphicsEngine(GraphicsConfiguration config) {
		this.config = config;
		//ObjectBuffer.createBuffer();
		GraphicsWindow.create(config);
		this.window = GraphicsWindow.getWindow();
		ObjectBuffer.createBuffer();
		BufferHandler.init();
		TextureHandler.create();
	}
	
	/**
	 * Initializes the engine with a <code>GraphicsConfiguration</code>.
	 * An input of <code>null</code> will use a default
	 * configuration.
	 * 
	 * @param config The <code>GraphicsConfiguration</code>.
	 * @throws UnsupportedOperationException When this method is called
	 * when a <code>GraphicsEngine</code> object has already been created.
	 */
	public static void init(GraphicsConfiguration config) throws UnsupportedOperationException {
		if (engine != null)
			throw new UnsupportedOperationException("Two graphics engines cannot be created at the same time");
		GraphicsObject.create();
		
		if (config == null)
			config = defaultConfig;
		
		engine = new GraphicsEngine(config);
		
	}
	
	/**
	 * Destroys the current running graphics engine.
	 * If the engine is already destroyed or has never
	 * been created, this method will do nothing.
	 */
	public static void destroy() {
		if (engine != null) {
			GraphicsObject.destroyAll();
			ObjectBuffer.destroyBuffer();
			BufferHandler.destroy();
			TextureHandler.destroy();
			engine = null;
			System.gc();
		}
	}
	
	/**
	 * Gets the current running graphics engine.
	 * 
	 * @return The <code>GraphicsEngine</code>.
	 */
	public static GraphicsEngine getEngine() { 
		return engine;
	}
	
	/**
	 * Gets the associated <code>GraphicsConfiguration</code>.
	 * 
	 * @return The <code>GraphicsConfiguration</code>.
	 */
	public GraphicsConfiguration getConfiguration() {
		return config;
	}
	
	/**
	 * Starts the graphics engine
	 */
	public void start() {
		window.start();
	}
	
	/**
	 * Adds a <code>GraphicsListener</code> to the OpenGL
	 * window.
	 * 
	 * @param l The <code>GraphicsListener</code>.
	 */
	public void addGraphicsListener(GraphicsListener l) {
		window.addListener(l);
	}
	
	/**
	 * Returns the FPS of the window.
	 * 
	 * @return The FPS.
	 */
	public int getFPS() {
		return window.getFPS();
	}
	
	/**
	 * Tests whether the OpenGL window is open.
	 * 
	 * @return <code>true</code> if the window is
	 * open and <code>false</code> otherwise.
	 */
	public boolean windowOpen() {
		return window.getFPS() == -1;
	}
	
	/**
	 * Returns the <code>Camera</code> currently
	 * being used by the engine.
	 * 
	 * @return The <code>Camera</code>.
	 */
	public Camera getCamera() {
		return window.camera;
	}
	
	/**
	 * Sets the <code>Camera</code> to be used
	 * by the engine.
	 * 
	 * @param camera The camera.
	 */
	public void setCamera(Camera camera) {
		window.camera = camera;
	}
	
	/**
	 * Sets the <code>RenderListener</code> to be used
	 * by the engine.
	 * 
	 * @param list The listener.
	 */
	public void setRenderListener(RenderListener list) {
		ObjectBuffer.getBuffer().list = list;
	}
	
	/**
	 * Updates the configuration associated with the current
	 * running window.
	 * 
	 * @param config The <code>GraphicsConfiguration</code>.
	 */
	public void updateConfiguration(GraphicsConfiguration config) {
		window.destroy();
		GraphicsWindow.create(config);
		window = GraphicsWindow.getWindow();
		window.start();
	}
	
	/**
	 * Returns whether or not the engine is ready to create
	 * OpenGL related objects such as a <code>VBOObject</code>.
	 * 
	 * @return <code>true</code> if it is ready and 
	 * <code>false</code> otherwise.
	 */
	public boolean ready() {
		boolean win = window.initialized();
		return win;
	}
}
