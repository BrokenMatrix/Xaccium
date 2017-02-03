package rendering;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import game.entities.Player;
import game.tools.MathHelper;

public class Renderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	private static Matrix4f ProjectionMatrix;
	private static Matrix4f ViewMatrix;
		
	public static void Create(){
		
		//Enabling Culling For the Back Size Of Objects
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		//Creating Projection Matrix
		CreateProjectionMatrix();
		//Creating Terrain Renderer
		TerrainRenderer.Create(ProjectionMatrix);
		ViewMatrix = new Matrix4f();
		
	}
	
	public static void Render(){
		
		//Enabling Depth Test
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		//Clearing Buffers
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		//Clearing Window Color
		GL11.glClearColor(1, 1, 1, 1);
		//Creating the View Matrix
//		viewMatrix = MatrixHelper.createViewMatrix(Player.x, Player.y, Player.z, Player.rX, Player.rY, Player.rZ);
		ViewMatrix.setIdentity();
		ViewMatrix = MathHelper.CreateViewMatrix(Player.x, Player.y, Player.z, Player.rX, Player.rY, Player.rZ);
		
		//Rendering Terrain
		TerrainRenderer.Render(ViewMatrix);
		
	}
	
	private static void CreateProjectionMatrix(){
		
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan(Math.toRadians((FOV / 2f)))) * aspectRatio;
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		ProjectionMatrix = new Matrix4f();
		ProjectionMatrix.m00 = x_scale;
		ProjectionMatrix.m11 = y_scale;
		ProjectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		ProjectionMatrix.m23 = -1;
		ProjectionMatrix.m32 = -((2 * FAR_PLANE + NEAR_PLANE) / frustum_length);
		ProjectionMatrix.m33 = 0;
		
	}
	
	public static void Destroy(){
		
		//Destroying Terrain Renderer
		TerrainRenderer.Destroy();
		
	}
	
}
