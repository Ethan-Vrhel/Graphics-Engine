package org.vrhel.graphics;

import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

class GraphicsWindow implements Runnable {

	private static GraphicsWindow win;
	
	// The window handle
	private long window;
	private GraphicsConfiguration config;
	private WindowProperties properties;
	private Thread windowThread;
	private boolean validCreation;
	private volatile long fps;
	private boolean alive;
	private boolean mouseIn;
	
	private ArrayList<GraphicsListener> listeners;
	
	Camera camera;
	
	private GraphicsWindow(GraphicsConfiguration config) {
		this.config = config;
		this.properties = config.getWindowProperties();
		this.fps = 0;
		this.alive = true;
		this.listeners = new ArrayList<GraphicsListener>();
	}
	
	synchronized static void create(GraphicsConfiguration config) throws UnsupportedOperationException {
		if (win != null)
			throw new UnsupportedOperationException("A window is already in use");
		//System.out.println("Running: LWJGL " + Version.getVersion() + "!");
		win = new GraphicsWindow(config);
	}
	
	static GraphicsWindow getWindow() {
		return win;
	}
	
	void destroy() {
		glfwSetWindowShouldClose(window, true);
		while (alive) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		win = null;
	}
	
	synchronized void updateConfiguration(GraphicsConfiguration config) {
		destroy();
		while (alive) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		create(config);
		start();
		glfwFocusWindow(window);
	}
	

	void start() { 
		validCreation = true;
		windowThread = new Thread(this);
		//windowThread.setPriority(Thread.NORM_PRIORITY);
		windowThread.start();
	}
	
	synchronized void addListener(GraphicsListener l) {
		listeners.add(l);
	}
	
	@Override
	public void run() throws UnsupportedOperationException {
		if (! validCreation)
			throw new UnsupportedOperationException("Invalid window dispatch");
		validCreation = false;

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		alive = false;
	}

	private synchronized void init() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onFirstInit();
		}
		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable
		//glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
		//glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
		
		// Create the window
		window = properties.create();
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		if (properties.useVysnc())
			glfwSwapInterval(GLFW_TRUE);
		else
			glfwSwapInterval(GLFW_FALSE);

		// Make the window visible
		glfwShowWindow(window);
		
		glfwSetCursorEnterCallback(window, new GLFWCursorEnterCallback() {
			@Override
			public void invoke(long window, boolean entered) {
				mouseIn = entered;
			}
		});
	}

	private synchronized void loop() {		
		
		glfwFocusWindow(window);
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		glEnable(GL_TEXTURE_2D);
		
		// Set the clear color
		glClearColor(0.529f, 0.808f, 0.922f, 0.0f);
		
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onInit();
		}
		
		camera = new CameraOrtho((int) config.getWindowProperties().getResolution().getWidth(), 
				(int) config.getWindowProperties().getResolution().getHeight());
		
//		float[] vertices = new float[] {
//			-0.5f, 0.5f, 0,	// 0
//			0.5f, 0.5f, 0,	// 1
//			0.5f, -0.5f, 0,	// 2
//			-0.5f, -0.5f, 0	// 3
//		};
//		
//		float[] texture = new float[] {
//			0, 0,
//			1, 0,
//			1, 1,
//			0, 1
//		};
//		TextureData data = new TextureData(texture, 1f, 0f, 0f);
//		data.transform();
//		
//		int[] indicies = new int[] {
//			0, 1, 2,
//			2, 3, 0
//		};
		
//		Model model = new Model(vertices, texture, indicies);
//		
//		Matrix4f scale = new Matrix4f()
//				.translate(new Vector3f(100, 0, 0))
//				.scale(256);
//		Matrix4f target = new Matrix4f();
// 
//		
//		Texture2D tex2 = null;
//		try {
//			tex2 = new Texture2D("res\\tex.png", Texture.NEAREST);
//		} catch (IOException e) {
//			e.printStackTrace(System.err);
//		}
//		
//		Texture2D tex = null;
//		try {
//			tex = new Texture2D("res\\tex2.png", Texture.NEAREST);
//		} catch (IOException e) {
//			e.printStackTrace(System.err);
//		}
		
		camera.setPositon(new Vector3f(0, 0, 0));
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
		
		while (! glfwWindowShouldClose(window)) {
			long start = System.nanoTime();
			
			
			if (camera != null) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
				
				//target = scale;
							
				//tex.bind();
//				Shader.defaultShader.setUniform("sampler", 1);
//				Shader.defaultShader.setUniform("projection", camera.projection().mul(target));
//				//tex.bind(0);
//				tex2.bind(1);
//				Shader.defaultShader.bind();
				render();
				
				glfwSwapBuffers(window);
			}
	
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			long renTime = System.nanoTime() - start;
			fps = 1000000000 / renTime;
		}
		
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onClose();
		}
	}	 
	
	private void render() {
		//System.out.println("render");
		ObjectBuffer.getBuffer().render();
	}
	
	boolean mouseInWindow() {
		return mouseIn;
	}
	
	int getFPS() {
		if (alive)
			return (int) fps;
		else
			return -1;
	}
	
	long getWindowID() {
		return window;
	}
}