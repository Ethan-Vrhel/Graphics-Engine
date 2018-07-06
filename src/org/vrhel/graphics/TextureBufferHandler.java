package org.vrhel.graphics;

import java.util.ArrayList;

/**
 * The <code>TextureBufferHandler</code> handles textures
 * that can be drawn into.
 * 
 * @author Ethan Vrhel
 * @since 1.2
 */
public class TextureBufferHandler {
	
	private static TextureBufferHandler handler;

	static void init() {
		handler = new TextureBufferHandler();
	}
	
	static void destroyHandler() {
		handler.destroy();
		handler = null;
	}
	
	/**
	 * Gets the current texture buffer handler.
	 * 
	 * @return The current texture buffer handler.
	 */
	public static TextureBufferHandler getHandler() {
		return handler;
	}
	
	private ArrayList<TextureBuffer> buffers;
	
	public TextureBuffer getBuffer(int id) {
		for (int i = 0; i < buffers.size(); i++) {
			if (buffers.get(i).getID() == id) {
				return buffers.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Creates a new texture buffer.
	 * 
	 * @param width The width.
	 * @param height The height.
	 * @param format The format of the buffer.
	 * @return The id of the buffer.
	 */
	public int genBuffer(int width, int height, int format) {
		TextureBuffer buff =  new TextureBuffer(width, height, format);
		buffers.add(buff);
		return buff.getID();
	}
	
	private TextureBufferHandler() {
		buffers = new ArrayList<TextureBuffer>();
	}
	
	private void destroy() {
		
	}
}
