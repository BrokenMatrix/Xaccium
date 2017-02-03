package game.tools;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseHelper {

	public static int MouseY;
	private static List<Integer> buttonsPressed;
	
	public static boolean WasButtonPressed(int button){
		
		return buttonsPressed.contains(button);
		
	}
	
	public static void Create(){
		
		buttonsPressed = new ArrayList<Integer>();
		
	}
	
	public static void Update(){
		
		MouseY = Display.getHeight() - Mouse.getY();
		
		buttonsPressed.clear();
		while(Mouse.next()){
			if(Mouse.getEventButtonState()){
				buttonsPressed.add(Mouse.getEventButton());
			}
		}
		
	}
	
}
