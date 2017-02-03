package game.world;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

import game.tools.OpenSimplexNoise;
import game.tools.VoxelMeshConstructor;

public class World {
	
	public static final int CHUNKS = 8;
	public static final int VOXEL_SIZE = 4;
	public static final int CHUNK_SIZE = 8;
	private static final int CHUNK_SIZE1 = CHUNK_SIZE-1;
	public static final int CHUNK_SIZE_Y = 32;
	
	public static List<Chunk> Visible;
	public static Chunk[][] Chunks;
	
	public static void Create(){
		
		Visible = new ArrayList<Chunk>();
		Chunks = new Chunk[CHUNKS][CHUNKS];
		
		OpenSimplexNoise generator = new OpenSimplexNoise(4237362089L);
		for(int x = 0; x < CHUNKS; x++){
			for(int y = 0; y < CHUNKS; y++){
				float[][][] voxels = GenerateChunk(x * CHUNK_SIZE1, y * CHUNK_SIZE1, generator);
				Chunk chunk = new Chunk(voxels, VoxelMeshConstructor.constuctMesh(voxels, CHUNK_SIZE, CHUNK_SIZE_Y, VOXEL_SIZE), new Vector3f(x * CHUNK_SIZE * VOXEL_SIZE, 0, y * CHUNK_SIZE * VOXEL_SIZE));
				Visible.add(chunk);
				Chunks[x][y] = chunk;
			}
		}
		
	}
	
	public static float getClosedHightBelow(Chunk chunk, int x, int Y, int z){
		
		for(int y = Y; y > Y - 5; y--){
			if(y > -1){
				if(chunk.voxels[x][y][z] > 0.5f){
					return y + chunk.voxels[x][y][z];
				}
			}
		}
		return 0;
		
	}
	
	private static float[][][] GenerateChunk(int xO, int yO, OpenSimplexNoise generator){
		
		float[][][] voxelData = new float[CHUNK_SIZE][CHUNK_SIZE_Y][CHUNK_SIZE];
		for(int x = 0; x < CHUNK_SIZE; x++){
			for(int z = 0; z < CHUNK_SIZE; z++){
				float height = (float) (1-Math.abs(generator.eval((x + xO)/5.0, (z + yO)/5.0))) + 16;
				height += (1-Math.abs(generator.eval((x + xO)/30.0, (z + yO)/30.0)))*8;
				height += 1-Math.abs(generator.eval((x + xO)/1.0, (z + yO)/1.0))/4;
				int y = (int)Math.floor(height);
				int py = (int) Math.ceil(height);
				float density = height;
				if(y > height){
					density = y - height;
				}else{
					density = height - y;
				}
				voxelData[x][py][z] = density;
				for(int Y = 0; Y < py; Y++){
//					float noise3D = NoiseHelper3D.Simple3D(new Vector3f(x + xO, Y, z + yO), 0.02f);
//					float noise3D = (float) (Math.abs(generator.eval((x + xO) * 0.1, Y*0.1, (z + yO) * 0.1)));
//					voxelData[x][Y][z] = 1 - noise3D;
					voxelData[x][Y][z] = 1;
				}
//				if(x > 2 && z > 2 && x < 5 && z < 5){
//					voxelData[x][py][z] = 0;
//				}
			}
		}
		return voxelData;
		
	}
	
}
