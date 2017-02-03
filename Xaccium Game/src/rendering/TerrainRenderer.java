package rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import game.world.Chunk;
import game.world.World;
import rendering.shaders.TerrainShader;

public class TerrainRenderer {

	private static TerrainShader shader;
	
	public static void Create(Matrix4f projectionMatrix){
		
		shader = new TerrainShader();
		shader.start();
		shader.loadLight(new Vector3f(0.4f,-1f,0.8f).normalise(null), new Vector3f(1f,1f,1f));
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		
	}
	
	public static void Render(Matrix4f viewMatrix){
		
		shader.start();
		shader.loadViewMatrix(viewMatrix);
		
		for(Chunk chunk : World.Visible){
			GL30.glBindVertexArray(chunk.mesh[0]);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			
//			Matrix4f m = new Matrix4f();
//			m.setIdentity();
//			m.translate(new Vector3f(32,0,32));
			shader.loadTransformationMatrix(chunk.transformation);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, chunk.mesh[1]);
			
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
			
		}
		
		shader.stop();
		
	}
	
	public static void Destroy(){
		
		shader.destroy();
		
	}
	
}
