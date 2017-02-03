package rendering.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TerrainShader extends Shader {

	private int location_transformation;
	private int location_projection;
	private int location_view;
	private int location_lightPosition;
	private int location_lightColor;
	
	public TerrainShader() {
		
		super("TerrainShader", true);
		
	}

	@Override
	protected void getAllUniformLocations() {
		
		location_transformation = super.getUniformLocation("transformation");
		location_projection = super.getUniformLocation("projection");
		location_view = super.getUniformLocation("view");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColor = super.getUniformLocation("lightColor");
		
	}

	@Override
	protected void bindAttributes() {
		
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "colorFragment");
		
	}
	
	public void loadLight(Vector3f position, Vector3f color){
		
		super.loadVector3(location_lightPosition, position);
		super.loadVector3(location_lightColor, color);
		
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		
		super.loadMatrix(location_transformation, matrix);
		
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		
		super.loadMatrix(location_projection, matrix);
		
	}
	
	public void loadViewMatrix(Matrix4f matrix){
		
		super.loadMatrix(location_view, matrix);
		
	}
	
	
	
}
