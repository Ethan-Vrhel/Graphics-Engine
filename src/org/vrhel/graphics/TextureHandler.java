package org.vrhel.graphics;

import java.util.ArrayList;

/**
 * The <code>TextureHandler</code> stores
 * all textures.
 * 
 * @author Ethan Vrhel
 * @since 1.2
 */
public class TextureHandler {

	private static TextureHandler tex;
	
	static void create() throws UnsupportedOperationException {
		if (tex != null)
			throw new UnsupportedOperationException("The TextureHandler already exists");
		tex = new TextureHandler();
	}
	
	static void destroy() {
		tex.destroyAll();
		for (int i = 0; i < tex.textures.size(); i++) {
			tex.textures.set(i, null);
		}
		tex.textures = null;
		tex = null;
	}
	
	public static TextureHandler getHandler() {
		return tex;
	}
	
	private ArrayList<Texture> textures;
	
	private TextureHandler() {
		textures = new ArrayList<Texture>();
	}
	
	void add(Texture tex) {
		if (! textures.contains(tex))
			textures.add(tex);
	}
	
	void destroyAll() {
		for (int i = 0; i < textures.size(); i++) {
			if (textures != null) {
				textures.get(i).destroy();
			}
		}
	}
	
	/**
	 * Gets a texture by its name.
	 * 
	 * @param tex The name of the texture.
	 * @return The respective texture.  <code>null</code>
	 * is returned if it does not exist or <code>tex</code>
	 * is <code>null</code>.
	 */
	public Texture get(String tex) {
		if (tex == null)
			return null;
		
		for (int i = 0; i < textures.size(); i++) {
			if (textures.get(i) != null) {
				if (textures.get(i).getName().equals(tex)) {
					return textures.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets a texture by its id.
	 * 
	 * @param id The id of the texture.
	 * @return The respective texture.  <code>null</code>
	 * is returned if it does not exist.
	 */
	public Texture get(int id) {
		if (id < 0)
			return null;
		
		for (int i = 0; i < textures.size(); i++) {
			if (textures.get(i) != null) {
				if (textures.get(i).getID() == id) {
					return textures.get(i);
				}
			}
		}
		return null;
	}
}
