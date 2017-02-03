package game.tools;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

public class KeyboardListener {
	
	public static List<Integer> KeysPressedThisTick = new ArrayList<Integer>();
	
	public static void Update(){
		
		KeysPressedThisTick.clear();
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				//System.out.println(Keyboard.getKeyName(Keyboard.getEventKey()));
				KeysPressedThisTick.add(Keyboard.getEventKey());
			}
		}
		
	}
	
}
