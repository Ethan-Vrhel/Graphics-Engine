package org.vrhel.graphics;

abstract class AbstractBuffer {

	private static int nextID = 0;
	
	protected int id;
	protected boolean enabled;
	
	AbstractBuffer() {
		this.enabled = true;
		this.id = nextID;
		nextID++;
		AbstractBufferHandler.getHandler().add(this);
	}
	
	/**
	 * Returns the ID of this buffer.
	 * 
	 * @return The ID.
	 */
	public int getID() {
		return id;
	}
	

	/**
	 * Sets whether this buffer is enabled.
	 * 
	 * @param enabled Whether this buffer is
	 * enabled.
	 */
	public final void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Returns whether this buffer is enabled.
	 * 
	 * @return <code>true</code> if this buffer is
	 * enabled and <code>false</code> otherwise.
	 */
	public final boolean isEnabled() {
		return enabled;
	}

	ObjectBuffer getObjectBuffer() {
		return null;
	}
	
	abstract void render();
	
	abstract void destroy();
}
