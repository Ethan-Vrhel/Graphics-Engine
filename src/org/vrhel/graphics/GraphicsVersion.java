package org.vrhel.graphics;

/**
 * Class containing API version information.

 * @author Ethan Vrhel
 * @since 1.0
 */
public final class GraphicsVersion {
	
	static {
		System.out.println(getVersion());
	}

	/**
	 * API Version
	 */
	public static final int 	VERSION_MAJOR 		= 1,
								VERSION_MINOR 		= 1,
								VERSION_REVISION	= 3;
	
	/**
	 * The modifier of the version.
	 */
	public static final String	MODIFIER			= "pre-2";
	
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
	@SuppressWarnings("unused")
	public static String getVersion() {
		String ret = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_REVISION;
		if (MODIFIER != "")
			return ret + "-" + MODIFIER;
		else
			return ret;
	}
}
