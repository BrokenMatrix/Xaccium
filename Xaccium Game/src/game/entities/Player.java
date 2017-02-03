package game.entities;

import game.world.World;
import rendering.Window;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


public class Player {
	
	private static final float GRAVITY = -9.8f;
	private static final float WALK_SPEED = 3f;
	
	public static float x, y, z;
	public static float rX, rY, rZ;
	private static float vX, vY, vZ;
	private static float movementSpeed = 0;
	private static float movementSpeedY = 0;
	
	public static void create(){
		
		z = 0;
		y = 50;
		Mouse.setGrabbed(true);
	
	}
	
	public static void update(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			movementSpeedY = WALK_SPEED * 10 * Window.GetDelta();
		}else{
			movementSpeedY = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			movementSpeed = WALK_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			movementSpeed = -WALK_SPEED;
		}else{
			movementSpeed = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			movementSpeed *= 3;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			movementSpeed *= 20;
			movementSpeedY *= 20;
		}
		y += movementSpeedY;
		
		float distance = movementSpeed * Window.GetDelta();
		x += distance * Math.sin(Math.toRadians(rY));
		z -= distance * Math.cos(Math.toRadians(rY));
		rX -= Mouse.getDY() * Window.GetDelta();
		rY += Mouse.getDX() * Window.GetDelta();
		
		int X = (int) Math.round(x / World.VOXEL_SIZE);
		int Y = (int) Math.round(y / World.VOXEL_SIZE + 2);
		int Z = (int) Math.round(z / World.VOXEL_SIZE);
		int CX = (int)  (X / World.CHUNK_SIZE);
		X -= CX * World.CHUNK_SIZE;
		int CZ = (int)  (Z / World.CHUNK_SIZE);
		Z -= CZ * World.CHUNK_SIZE;
		float height = -100;
		if(X > -1 && Z > -1 && X < World.CHUNK_SIZE && Z < World.CHUNK_SIZE && Y > -1 && Y < World.CHUNK_SIZE_Y && CX < World.CHUNKS && CZ < World.CHUNKS && CX > -1 && CZ > -1){
			height = World.getClosedHightBelow(World.Chunks[CX][CZ], X, Y, Z) * World.VOXEL_SIZE;
		}
		
		x += vX * Window.GetDelta();
		y += vY * Window.GetDelta();
		z += vZ * Window.GetDelta();
		
		if(y < height + 1.75f){
			y = height + 1.75f;
			vY = 0;
		}else{
			vY += GRAVITY * Window.GetDelta();
		}
//		if(World.Visible.get(0).voxels[(int) x][Y - 2][(int) z] > 0){
//			y = (int) Y + World.Visible.get(0).voxels[(int) x][(int) y - 2][(int) z] + 2;
//		}
		
	}
	
}
