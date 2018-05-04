package org.vrhel.graphics;

import java.util.ArrayList;

class ObjectBuffer {
	
	/**
	 * Update settings for the buffer.
	 */
	public static final boolean CULL_ON_UPDATE	= true,
								SORT_ON_UPDATE	= false;
	
	/**
	 * Addition settings for the buffer.
	 */
	public static final boolean CULL_ON_ADD		= false,
								SORT_ON_ADD		= true;

	private static ObjectBuffer buffer;
	
	private ArrayList<RenderableObject> objs;
	
	private ObjectBuffer() {
		objs = new ArrayList<RenderableObject>();
	}
	
	static void createBuffer() throws UnsupportedOperationException {
		if (buffer != null)
			throw new UnsupportedOperationException("Cannot create new object buffer");
		buffer = new ObjectBuffer();
	}
	
	static ObjectBuffer getBuffer() {
		return buffer;
	}
	
	static void destroyBuffer() {
		if (buffer != null)
			buffer.destroy();
		buffer = null;
	}
	
	private void destroy() {
		objs.removeAll(objs);
		objs = null;
		buffer = null;
	}

	void add(RenderableObject obj) {
		objs.add(obj);
		if (SORT_ON_ADD)
			objs.sort(RenderableObject.RenderableZComparator);
		if (CULL_ON_ADD)
			cull();
	}
	
	void remove(RenderableObject obj) {
		
	}
	
	void cull() {
		if (objs.size() == 0)
			return;
		for (int i = objs.size() - 1; i >= 0; i--) {
			if (objs.get(i) == null) {
				objs.remove(i);
			}
		}
		objs.trimToSize();
	}
	
	RenderableObject[] getObjects() {
		cull();
		ArrayList<RenderableObject> nSize = new ArrayList<RenderableObject>();
		for (int i = 0; i < objs.size(); i++) {
			if (objs.get(i).shouldDraw())
				nSize.add(objs.get(i));
		}
		if (nSize.size() == 0)
			return null;
		RenderableObject[] rObjs = new RenderableObject[nSize.size()];
		rObjs = nSize.toArray(rObjs);
		return rObjs;
	}
	
	void render() {
		if (CULL_ON_UPDATE)
			cull();
		if (SORT_ON_UPDATE)
			objs.sort(RenderableObject.RenderableZComparator);
		//if (objs.size() == 0)
		//	System.out.println("nothing rendering!");
		for (int i = 0; i < objs.size(); i++) {
			if (objs.get(i) != null) {
				objs.get(i).render();
			}
		}
	}
}
