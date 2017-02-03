package game.world;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Chunk {

	public int[] mesh;
	public Matrix4f transformation;
	public float[][][] voxels;
	
	public Chunk(float[][][] voxels, int[] mesh, Vector3f position){
		
		this.voxels = voxels;
		this.mesh = mesh;
		Matrix4f transformation = new Matrix4f();
		transformation.setIdentity();
		transformation.translate(position);
		this.transformation = transformation;
		
	}
	
}
