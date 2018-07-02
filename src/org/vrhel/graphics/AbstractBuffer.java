package org.vrhel.graphics;

abstract class AbstractBuffer {

	private static int nextID = 0;
	
	protected int id;
	
	AbstractBuffer() {
		id = nextID;
		nextID++;
	}
	
	/**
	 * Returns the ID of this buffer.
	 * 
	 * @return The ID.
	 */
	public int getID() {
		return id;
	}
	
	abstract void render();
}
