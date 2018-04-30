package org.vrhel.graphics;

/**
 * The <code>GraphicsListener</code> class
 * listens for events within the OpenGL window.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public interface GraphicsListener {
	
	/**
	 * Called before any OpenGL element is initialized.
	 */
	public void onFirstInit();

	/**
	 * Called on initialization after OpenGL.  Do
	 * texture initializations directly dependent 
	 * on graphics here.
	 */
	public void onInit();
	
	/**
	 * Called when the OpenGL window has been closed.
	 */
	public void onClose();
}
