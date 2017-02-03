package game.tools;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

public class VertexObjectHelper {
	

	//NO COMMENTS BECAUSE YOU SHOULD KNOW WHAT ALL OF THIS IS
	
	private static List<Integer> vaos;
    private static List<Integer> vbos;
    
    public static void updateVbo(int vbo, float[] data, FloatBuffer buffer){
    	
    	buffer.clear();
    	buffer.put(data);
    	buffer.flip();
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
    	GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_STREAM_DRAW);
    	GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    	
    }
    
    public static int createEmptyVbo(int floatCount){
    	
    	int vbo = GL15.glGenBuffers();
    	vbos.add(vbo);
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
    	GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4, GL15.GL_STREAM_DRAW);
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    	return vbo;
    	
    }
    
    public static void addInstancedAttribute(int vao, int vbo, int attribute, int dataSize, int instancedDataLength, int offset){
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
    	GL30.glBindVertexArray(vao);
    	GL20.glVertexAttribPointer(attribute, dataSize, GL11.GL_FLOAT, false, instancedDataLength * 4, offset * 4);
    	GL33.glVertexAttribDivisor(attribute, 1);
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    	GL30.glBindVertexArray(0);
    	
    }
    
    public static void Create(){
    	
    	vaos = new ArrayList<Integer>();
    	vbos = new ArrayList<Integer>();
    	
    }
    
    public static int[] LoadToVAO(float[] positions, float[] textureCoordinates, int[] indices){
		
		int vaoID = CreateVAO();
		BindIndicesBuffer(indices);
		StoreDataInAttributeList(0, positions, 3);
		StoreDataInAttributeList(1, textureCoordinates, 2);
		UnbindVAO();
		return new int[]{vaoID, indices.length};
		
	}
    
    public static int[] LoadToVAO(float[] vertices){
    	
    	int vaoID = CreateVAO();
    	StoreDataInAttributeList(0, vertices, 2);
    	UnbindVAO();
    	return new int[]{vaoID, vertices.length / 2};
    	
    }
    
    public static int[] LoadToVAO(float[] vertices, float[] colors){
    	
    	int vaoID = CreateVAO();
    	StoreDataInAttributeList(0, vertices, 3);
    	StoreDataInAttributeList(1, colors, 1);
    	UnbindVAO();
    	return new int[]{vaoID, vertices.length / 3};
    	
    }
    
//    public static int loadToVAO(float[] positions, float[] textureCoords, Text text){
//		
//		int vaoID = CreateVAONoAdd();
//		int vbo1 = GetAndStoreDataInAttributeList(0, 2, positions);
//		int vbo2 = GetAndStoreDataInAttributeList(1, 2, textureCoords);
//		UnbindVAO();
//		text.setVbos(vbo1, vbo2);
//		return vaoID;
//		
//	}
    
    private static void StoreDataInAttributeList(int attributeNumber, float[] data, int size){
        
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = StoreDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
       
    }
    
//    private static int GetAndStoreDataInAttributeList(int attributeNumber, int size, float[] data){
//		
//		int vboID = GL15.glGenBuffers();
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
//		FloatBuffer buffer = StoreDataInFloatBuffer(data);
//		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
//		GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, 0, 0);
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//		return vboID;
//		
//	}
    
    private static int CreateVAO(){
        
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
       
    }
    
//    private static int CreateVAONoAdd(){
//		
//		int vaoID = GL30.glGenVertexArrays();
//		GL30.glBindVertexArray(vaoID);
//		return vaoID;
//		
//	}
    
    private static FloatBuffer StoreDataInFloatBuffer(float[] data){
        
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
       
    }
   
    private static void UnbindVAO(){
       
        GL30.glBindVertexArray(0);
       
    }
    
    private static void BindIndicesBuffer(int[] indices){
		
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = StoreDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
	}
	
	private static IntBuffer StoreDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
    
    public static void Destroy(){
    	
    	 for(int vao : vaos){
             GL30.glDeleteVertexArrays(vao);
         }
        
         for(int vbo : vbos){
             GL15.glDeleteBuffers(vbo);
         }
    	
    }
	
}
