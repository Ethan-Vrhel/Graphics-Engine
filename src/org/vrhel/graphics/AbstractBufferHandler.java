package org.vrhel.graphics;

import java.util.ArrayList;

class AbstractBufferHandler {

	private static AbstractBufferHandler handler;
	
	private ArrayList<AbstractBuffer> buffers;
	
	private AbstractBufferHandler() { 
		buffers = new ArrayList<AbstractBuffer>();
	}
	
	static void init() {
		handler = new AbstractBufferHandler();
	}
	
	static AbstractBufferHandler getHandler() {
		return handler;
	}
	
	static void destroyHandler() {
		handler.destroy();
		handler = null;
	}
	
	private void destroy() {
		
	}
}
