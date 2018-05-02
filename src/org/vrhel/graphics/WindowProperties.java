package org.vrhel.graphics;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.GLFW.*;
import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.Version;

/**
 * The <code>WindowProperties</code> class contains information
 * for creating the application window;
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class WindowProperties {
	
	private static final Dimension 	screenSize 	= Toolkit.getDefaultToolkit().getScreenSize();
	private static final String		defaultName	= "LWJGL " + Version.getVersion() + " - Graphics Engine " + GraphicsVersion.getVersion();
	
	/**
	 * Constant representing the native resolution.
	 */
	public static final int width = (int) screenSize.getWidth(),
							height = (int) screenSize.getHeight();

	private String name;
	private Dimension resolution;
	private boolean fullscreen;
	private boolean vysnc;
	
	/**
	 * Creates new window properties.
	 * 
	 * @param name The window name.
	 * @param resolution The screen resolution.
	 * @param fullscreen Whether fullscreen will be used.
	 * @param vysnc Whether to use vertical synchronization.
	 */
	public WindowProperties(String name, Dimension resolution, boolean fullscreen, boolean vsync) {
		if (name == null)
			name = defaultName;
		this.name = name;
		this.resolution = resolution;
		this.fullscreen = fullscreen;
		this.vysnc = vsync;
		if ((int) resolution.getHeight() <= 0 || (int) resolution.getWidth() <= 0)
			throw new IllegalArgumentException("Dimension is invalid");
	}
	
	/**
	 * Creates the default window properties.
	 */
	public WindowProperties() {
		this(defaultName, new Dimension(1280, 720), false, true);
	}
	
	/**
	 * Gets the name of the window.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the dimensions of the window.
	 * 
	 * @return The dimensions.
	 */
	public Dimension getResolution() {
		return resolution;
	}
	
	/**
	 * Returns whether the window is fullscreen.
	 * 
	 * @return <code>true</code> if the window is
	 * fullscreen and <code>false</code> otherwise.
	 */
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	/**
	 * Returns whether vertical synchronization is
	 * being used.
	 * 
	 * @return <code>true</code> if v-sync is being used
	 * and <code>false</code> otherwise.
	 */
	public boolean useVysnc() {
		return vysnc;
	}
	
	/**
	 * Gets the camera that is currently being used by
	 * the renderer.
	 * 
	 * @return The current <code>Camera</code>.
	 */
	public Camera getCamera() {
		return GraphicsWindow.getWindow().camera;
	}
	
	/**
	 * Sets the current camera.
	 * 
	 * @param camera The new <code>Camera</code>.
	 */
	public void setCamera(Camera camera) {
		GraphicsWindow.getWindow().camera = camera;
	}
	
	long create() {
		long fullscreen = NULL;
		if (this.fullscreen) {
			fullscreen = glfwGetPrimaryMonitor();
		}
		
		long window = glfwCreateWindow((int) resolution.getWidth(), 
				(int) resolution.getHeight(), 
				name, fullscreen, NULL);
		return window;
	}
}
