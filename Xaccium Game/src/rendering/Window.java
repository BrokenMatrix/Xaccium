package rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class Window {
	
	//Window Aspect Ratio
    public static float AspectRatio;
    
    //Half of Window Width
    public static float WIDTH2;
    
    //Half of Window Height
    public static float HEIGHT2;
    
    //FPS Counter Helper Variables
    private static int fps;
    private static long lastFPS;
    
    //Time of Last Update
  	private static long LastFrameTime;
  	
  	//Time Difference Between Frames
    private static float Delta;
    
    //FPS Synchronization Value
    private static int FPS;
    
	public static void Create(int width, int height, int fps){
		
		ContextAttribs attribs = new ContextAttribs(3, 3)
		.withForwardCompatible(true)
		.withProfileCore(true);
		try {
			Display.setVSyncEnabled(true);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(new PixelFormat(), attribs);
		    Display.setTitle("Xaccium Awesome Shit BOi");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		LastFrameTime = GetCurrentTime();
		FPS = fps;
		AspectRatio = ((float) Display.getWidth()) / ((float) Display.getHeight());
		WIDTH2 = Display.getWidth() / 2;
		HEIGHT2 = Display.getHeight() / 2;
		lastFPS = GetCurrentTime();
		
	}
	
	public static float GetDelta(){
		
		return Delta;
		
	}
	
	private static long GetCurrentTime(){
		
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
        
    }
	
	public static void Update(){
		
		long currentFrameTime = GetCurrentTime();
		Delta = (currentFrameTime - LastFrameTime) / 1000f;
		LastFrameTime = currentFrameTime;
		Display.sync(FPS);
		Display.update();
		if(GetCurrentTime() - lastFPS > 1000){
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
		
	}
	
	public static boolean IsCloseRequested(){
		
		return Display.isCloseRequested();
		
	}
	
	public static void Destroy(){
		
		Display.destroy();
		System.exit(-1);
		
	}
	
}
