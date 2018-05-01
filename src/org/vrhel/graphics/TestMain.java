package org.vrhel.graphics;

import static org.vrhel.graphics.GraphicsEngine.*;

import java.awt.Dimension;
import java.io.IOException;

import org.joml.Vector3f;

/**
 * Class for testing functionality.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 * @deprecated Not to be used as an entry point
 */
@Deprecated
public class TestMain implements Runnable, GraphicsListener {
	
	private static GraphicsEngine engine;
	private static TestMain main;
	
	private VBOObject obj4;
	private VBOObject obj3;
	private VBOObject obj2;
	private VBOObject obj;
	
	private static Thread t;
	
	@Deprecated
	public static void main(String[] args) {
		init(new GraphicsConfiguration(
				new WindowProperties("test", new Dimension(1280, 720), false, true), 
				GraphicsConfiguration.SuggestedType.Dimension2));
		engine = getEngine();
		TestMain main = new TestMain();
		engine.addGraphicsListener(main);
		
		
		engine.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t = new Thread(main);
		t.setDaemon(true);
		t.start();
		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		GraphicsEngine.destroy();
	}
	
	@Override
	public void run() {
		//System.out.println("thread started.");
		float xvel = 0;
		float yvel = 0;
		float decay = 0.99f;
		float accel = 0.025f;
		while (true) {
			if (GraphicsWindow.getWindow().camera != null) {
				Camera camera = GraphicsWindow.getWindow().camera;
				if (Input.getKey(Input.GLFW_KEY_RIGHT)) {
					xvel += accel;
					
				} else if (Input.getKey(Input.GLFW_KEY_LEFT)) {
					xvel -= accel;
				}
				
				if (Input.getKey(Input.GLFW_KEY_UP)) {
					yvel += accel;
				} else if (Input.getKey(Input.GLFW_KEY_DOWN)) {
					yvel -= accel;
				}
				
				camera.setPositon(new Vector3f(camera.position.x + xvel, camera.position.y + yvel, camera.position.z));
				DefaultShader shader = obj.getShader().getDefault();
				TransformData data = shader.getTransform();
				data.setTransform(data.getX() - xvel * 2, data.getY() - yvel * 2, data.getZ(), data.getScale());
				
				DefaultShader shader2 = obj3.getShader().getDefault();
				TransformData data2 = shader2.getTransform();
				data2.setTransform(data2.getX() + xvel / 2, data2.getY() + yvel / 2, data2.getZ(), data2.getScale());
				
				DefaultShader shader3 = obj4.getShader().getDefault();
				TransformData data3 = shader3.getTransform();
				data3.setTransform(data3.getX() + xvel * 4, data3.getY() + yvel * 4, data3.getZ(), data3.getScale());
				xvel *= decay;
				yvel *= decay;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				
			}
			//System.out.println(engine.getFPS());
		}
	}

	@Override
	public void onFirstInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInit() {
		Texture2D tex = null;
		try {
			tex = new Texture2D("res\\source.png", Texture.LINEAR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Texture2D tex2 = null;
		try {
			tex2 = new Texture2D("res\\flare_small.png", Texture.LINEAR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Texture2D tex3 = null;
		try {
			tex3 = new Texture2D("res\\flare_large.png", Texture.LINEAR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Texture2D tex4 = null;
		try {
			tex4 = new Texture2D("res\\flare_verylarge.png", Texture.LINEAR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Texture2D tex5 = null;
		try {
			tex5 = new Texture2D("res\\wall.png", Texture.LINEAR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		VBOObject obj5;
		
		obj = VBOObjectFactory.newObject(1f, 1f, -10, tex, new UseableShader(new TransformData(0, 0, 0, 256)), new TextureTransform());
		obj2 = VBOObjectFactory.newObject(1f, 1f, 10, tex2, new UseableShader(new TransformData(0, 0, 0, 96)), new TextureTransform());
		obj3 = VBOObjectFactory.newObject(1f, 1f, 10, tex3, new UseableShader(new TransformData(0, 0, 0, 128)), new TextureTransform());
		obj4 = VBOObjectFactory.newObject(1f, 1f, 10, tex4, new UseableShader(new TransformData(0, 0, 0, 512)), new TextureTransform());
		obj5 = VBOObjectFactory.newObject(1f, 1f, 0, tex5, new UseableShader(new TransformData(0, -1256, 0, 2048)), new TextureTransform());
		
		obj.setShouldDraw(true);
		obj2.setShouldDraw(true);
		obj3.setShouldDraw(true);
		obj4.setShouldDraw(true);
		obj5.setShouldDraw(true);
		//t.start();
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
}
