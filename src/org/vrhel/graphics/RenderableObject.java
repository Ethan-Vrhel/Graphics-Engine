package org.vrhel.graphics;

import java.util.Comparator;

/**
 * The <code>RenderableObject</code> class is the final
 * class that stores information before rendering.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
abstract class RenderableObject implements Cloneable, Comparator<RenderableObject> {

	private static int nextID = 0;
	
	private boolean draw;
	private int id;
	
	private float width;
	private float height;
	
	private int zBuffer;
	
	/**
	 * Creates a new <code>RenderableObject</code>.
	 * 
	 * @param zBuffer The z-buffer.
	 * @param width The width.
	 * @param height The height.
	 */
	RenderableObject(int zBuffer, float width, float height) {
		id = nextID;
		nextID++;
		//System.out.println("creating");
		draw = false;
		this.zBuffer = zBuffer;
		this.width = width;
		this.height = height;
		ObjectBuffer.getBuffer().add(this);
	}
	
	/**
	 * <code>RenderableObject</code> comparator.
	 */
	public static final Comparator<RenderableObject> RenderableZComparator = new Comparator<RenderableObject>() {
		public int compare(RenderableObject obj1, RenderableObject obj2) {
			if (obj1 != null && obj2 != null)
				return obj1.compareTo(obj2);
			else
				return 0;
		}
	};
	
	@Override
	public final int compare(RenderableObject o1, RenderableObject o2) {
		return RenderableZComparator.compare(o1, o2);
	}
	
	/**
	 * Compares two <code>RenderableObjects</code>.
	 * 
	 * @param compareObject The object to compare to.
	 * @return The comparator difference.
	 */
	public final int compareTo(RenderableObject compareObject) 
	{
		int compareZVal = compareObject.getZBuffer();
		if (compareZVal == zBuffer) {
			return secondaryCompare(compareObject);
		}
		return this.zBuffer - compareZVal;
	}
	
	/**
	 * Called if the z-buffers are equal when comparing.
	 * 
	 * @param compareObject The object to compare to.
	 * @return The comparator difference.
	 */
	protected int secondaryCompare(RenderableObject compareObject) {
		return 0;
	}

	/**
	 * Sets whether this object should be drawn.
	 * 
	 * @param state <code>true</code> if this object
	 * should be drawn and <code>false</code> otherwise.
	 */
	public final void setShouldDraw(boolean state) {
		this.draw = state;
	}
	
	/**
	 * Returns the state of whether or not this object
	 * should be drawn.
	 * 
	 * @return <code>true</code> if it will be drawn
	 * and <code>false</code> otherwise.
	 */
	public final boolean shouldDraw() {
		return draw;
	}
	
	/**
	 * Gets the ID of this object.
	 * 
	 * @return The ID.
	 */
	protected final int getID() {
		return id;
	}
	
	/**
	 * Gets the z-buffer of this
	 * <code>RenderableObject</code>.
	 * 
	 * @return The z-buffer.
	 */
	public final int getZBuffer() {
		return zBuffer;
	}
	
	/**
	 * Gets the width of this object.
	 * 
	 * @return The width.
	 */
	public final float getWidth() {
		return width;
	}
	
	/**
	 * Gets the height of this object.
	 * 
	 * @return The height.
	 */
	public final float getHeight() {
		return height;
	}
	
	/**
	 * String representation of this object.
	 */
	@Override
	public String toString() {
		return getClass().getName() + ": id = " + id + " draw = " + draw;
	}
	
	/**
	 * Creates a clone of this <code>RenderableObject</code>
	 * so it does not need to be re-initialized.
	 */
	@Override
	public abstract RenderableObject clone();
	
	/**
	 * Draws the object.
	 */
	abstract void render();
}
