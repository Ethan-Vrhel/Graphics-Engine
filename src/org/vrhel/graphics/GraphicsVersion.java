package org.vrhel.graphics;

/**
 * Class containing API version information.

 * @author Ethan Vrhel
 * @since 1.0
 */
public final class GraphicsVersion {

	/**
	 * API Version
	 */
	public static final int VERSION_MAJOR = 1,
							VERSION_MINOR = 1,
							VERSION_REVISION = 0;
	
//	static {
//		System.out.println("Running graphics version: " + getVersion());
//	}
	
	private GraphicsVersion() { } 
	
	/**
	 * Gets the <code>String</code> representation of the
	 * version.
	 * 
	 * @return The <code>String</code> representation.
	 */
	public static String getVersion() {
		return VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_REVISION;
	}
}
