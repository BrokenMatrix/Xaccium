package game.tools;

import game.tools.VertexObjectHelper;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class VoxelMeshConstructor {
	
	private static final Vector3f GRASS = MathHelper.Color(119, 191, 32);
	
	public static int[] constuctMesh(float[][][] voxelData, int size, int sizeY, float vSize){
		
		//Creating List to Store Meshes From All the Voxels
		List<List<Vector3f[]>> meshes = new ArrayList<List<Vector3f[]>>();
		//For XYZ in voxelData...
		for(int x = 0; x < size; x++){
			for(int y = 0; y < sizeY; y++){
				for(int z = 0; z < size; z++){
					//Create Grid for Voxel Mesh Calculation
					float[] grid = new float[8];
					//Fill Grid for Voxel Mesh Calculation
					for(int i = 0; i < 8; i++){
						//Get Position for Current Index in Grid
						int ox = (int) (x + VoxelSubMeshConstructor.GetCornerTable()[i].x);
						int oy = (int) (y + VoxelSubMeshConstructor.GetCornerTable()[i].y);
						int oz = (int) (z + VoxelSubMeshConstructor.GetCornerTable()[i].z);
						if(ox < 0){
							ox = 0;
						}if(oy < 0){
							oy = 0;
						}if(oz < 0){
							oz = 0;
						}
						if(ox >= size){
							ox = size - 1;
						}
						if(oy >= sizeY){
							oy = sizeY - 1;
						}
						if(oz >= size){
							oz = size - 1;
						}
						//Set Voxel Grid Slot Accordingly
						grid[i] = voxelData[ox][oy][oz];
					}
					//Construct Volume Element Mesh and Add to List of Meshes
					meshes.add(VoxelSubMeshConstructor.ConstructSubMesh(grid, 0.5f, new Vector3f(x, y, z)));
				}
			}
		}
		
		//Calculate Needed Length for Vertex Array
		int verticesLength = 0;
		//For Mesh in Meshes...
		for(List<Vector3f[]> mesh : meshes){
			//If Location Has a Mesh...
			if(mesh != null){
				//Add (Size of Mesh) * (Vertices in a Triangle) * (Vertex Dimension Size) to Vertex Array Lenght
				verticesLength += mesh.size() * 3 * 3;
			}
		}

		//Vertex Array Index
		int vertexPointer = 0;
		//Create Vertex Array Using Calculate Length
		float[] vertices = new float[verticesLength];
		//For Mesh in Meshes...
		for(List<Vector3f[]> mesh : meshes){
			//If Location Has a Mesh...
			if(mesh != null){
				//For Triangle in Mesh...
				for(Vector3f[] triangle : mesh){
					//For Vertex in Triangle...
					for(Vector3f vertex : triangle){
						//Set X
						vertices[vertexPointer] = vertex.x * vSize;
						//Set Y
						vertices[vertexPointer + 1] = vertex.y * vSize;
						//Set Z
						vertices[vertexPointer + 2] = vertex.z * vSize;
						//Increase Vertex Array Index by 3
						vertexPointer += 3;
					}
				}
			}
		}
		float[] colors = new float[vertices.length/3];
		for(int i = 0; i < colors.length/3; i++){
			float t = MathHelper.nextFloat()/20f;
			colors[i*3] = GRASS.x+t;
			colors[i*3+1] = GRASS.y+t;
			colors[i*3+2] = GRASS.z+t;
		}
		//Return VAO ID and Vertex Count
		return VertexObjectHelper.LoadToVAO(vertices, colors);
		
	}
	
}
