package org.vrhel.graphics;

import static org.vrhel.graphics.GraphicsEngine.*;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import org.joml.Vector3f;

/**
 * Class for testing functionality.
 * 
 * @author Ethan Vrhel
 * @since 1.0
 */
public class TestMain implements Runnable, GraphicsListener {
	
	private static GraphicsEngine engine;
	private static TestMain main;
	
	private VBOObject obj5;
	private VBOObject obj4;
	private VBOObject obj3;
	private VBOObject obj2;
	private VBOObject obj;
	
	private static Thread t;
	
	public static void main(String[] args) {
		init(new GraphicsConfiguration(
				new WindowProperties(null, new Dimension(1280, 720), false, false), 
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
				
//				if (Input.getKey(Input.GLFW_KEY_O)) {
//					obj2.setShouldDraw(true);
//					System.out.println("drawing.");
//				} else {
//					obj2.setShouldDraw(false);
//					System.out.println("not drawing.");
//				}
				
				camera.setPositon(new Vector3f(camera.position.x + xvel, camera.position.y + yvel, camera.position.z));
//				DefaultShader shader = obj.getShader().getDefault();
//				TransformData data = shader.getTransform();
//				data.setTransform(data.getX() - xvel * 2, data.getY() - yvel * 2, data.getZ(), data.getScale());
				
//				DefaultShader shader2 = obj3.getShader().getDefault();
//				TransformData data2 = shader2.getTransform();
//				data2.setTransform(data2.getX() + xvel / 2, data2.getY() + yvel / 2, data2.getZ(), data2.getScale());
//				
//				DefaultShader shader3 = obj4.getShader().getDefault();
//				TransformData data3 = shader3.getTransform();
//				data3.setTransform(data3.getX() + xvel * 4, data3.getY() + yvel * 4, data3.getZ(), data3.getScale());
				xvel *= decay;
				yvel *= decay;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				
			}
			//System.out.println(engine.getFPS());
			
			if (Input.getKey(Input.GLFW_KEY_P)) {
				GraphicsEngine.destroy();
			}
			//System.out.println("FPS: " + engine.getFPS());
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
			tex = new Texture2D("res\\tex.png", Texture.LINEAR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Texture2D tex2 = null;
		try {
			tex2 = new Texture2D("res\\tex2.png", Texture.LINEAR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Texture2D tex3 = null;
		try {
			tex3 = new Texture2D("res\\tex3.png", Texture.LINEAR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Texture2D tex4 = null;
		try {
			tex4 = new Texture2D("res\\wall.png", Texture.LINEAR);
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
		
		int w = getEngine().getConfiguration().getWindowProperties().getResolution().width;
		int h = getEngine().getConfiguration().getWindowProperties().getResolution().height;
		
		// The seperate framebuffer
		//int id2 = BufferHandler.getHandler().genBuffer("Test-Buffer2", w / 4, h /4 , Shader.defaultShader, new Buffer.ClearFlag(0.5f, 0f, 0f, 0f));
		
		// The texture buffer
		int id = TextureBufferHandler.getHandler().genBuffer(1024, 1024, Texture.NEAREST);
		
		// Binds the texture buffer so these two objects are added to it
		AbstractBufferHandler.getHandler().bind(id);
		obj = VBOObjectFactory.newObject(1f, 1f, -10, tex, new UseableShader(new TransformData(0, 0, 0, 256)), new TextureTransform());
		obj2 = VBOObjectFactory.newObject(1f, 1f, 10, tex2, new UseableShader(new TransformData(0, 0, 0, 96)), new TextureTransform());
		
		// Unbinds to draw to the normal buffer
		AbstractBufferHandler.getHandler().bind(BufferHandler.UNBIND);
		obj3 = VBOObjectFactory.newObject(1f, 1f, 10, tex3, new UseableShader(new TransformData(0, 0, 0, 128)), new TextureTransform());
//		
//		// Binds to a test framebuffer
//		//AbstractBufferHandler.getHandler().bind(id2);
//		obj4 = VBOObjectFactory.newObject(1f, 1f, 10, tex4, new UseableShader(new TransformData(0, 0, 0, 512)), new TextureTransform());
		
		// Makes a new object containing the texture buffer
		obj5 = VBOObjectFactory.newObject(1f, 1f, 20, new Texture2D(new Dimension(128, 128), 6), new UseableShader(new TransformData(0, 0, 0, 640)), new TextureTransform());
		
		obj.setShouldDraw(true);
		obj2.setShouldDraw(true);
		obj3.setShouldDraw(false);
//		obj4.setShouldDraw(true);
		obj5.setShouldDraw(true);
	
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
}
