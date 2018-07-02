package org.vrhel.graphics;

import java.util.ArrayList;

public class TextureBufferHandler {
	
	private static TextureBufferHandler handler;

	static void init() {
		handler = new TextureBufferHandler();
	}
	
	static void destroyHandler() {
		handler.destroy();
		handler = null;
	}
	
	private ArrayList<TextureBuffer> buffers;
	
	public int genBuffer(int width, int height, int format) {
		TextureBuffer buff =  new TextureBuffer(width, height, format);
		buffers.add(buff);
		return buff.getID();
	}
	
	private TextureBufferHandler() {
		
	}
	
	private void destroy() {
		for (int i = 0; i < buffers.size(); i++) {
			buffers.get(i).destroy();
		}
	}
}
