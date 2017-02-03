package game.tools;

import java.awt.Rectangle;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class MathHelper {

	//Defining random, X axis, and Y axis
	public static final Vector3f X = new Vector3f(1, 0, 0);
	public static final Vector3f Y = new Vector3f(0, 1, 0);
    public static final Vector3f Z = new Vector3f(0, 0, 1);
	public static final Vector2f ZERO2 = new Vector2f();
	public static final Vector4f ZERO4 = new Vector4f();
	public static final Vector3f ONE3 = new Vector3f(1,1,1);
	public static final Random RANDOM = new Random(4237362089L);
	public static final Vector2f V64 = new Vector2f(64, 64);
	public static final Vector2f V48 = new Vector2f(48, 48);
	
	private static Matrix4f StorageM = new Matrix4f();
	private static Vector2f StorageV2 = new Vector2f();
	private static Vector2f StorageV2_2 = new Vector2f();
	private static Vector3f StorageV3 = new Vector3f();
	
	public static Matrix4f CreateViewMatrix(float x, float y, float z, float pitch, float yaw, float roll){
		
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(pitch), new Vector3f(1, 0, 0),matrix,matrix);
		Matrix4f.rotate((float)Math.toRadians(yaw), new Vector3f(0, 1, 0),matrix,matrix);
		Matrix4f.rotate((float)Math.toRadians(roll), new Vector3f(0, 0, 1),matrix,matrix);
		Matrix4f.translate(new Vector3f(-x, -y, -z),matrix,matrix);
		return matrix;
		
	}
	
	public static Vector3f Color(int r, int g, int b){
		
		return new Vector3f(r/255f,g/255f,b/255f);
		
	}
	
	public static float Max(float a, float min){
		
		if(a < min){
			return min;
		}
		return a;
		
	}
	
	public static float GetValue(float[][][] grid, int x, int y, int z, int size){
		
		float fSize = (float) size;
		float fX = x / fSize;
		float fY = y / fSize;
		float fZ = z / fSize;
		int LIX = (int) Math.floor(fX);
		int LIY = (int) Math.floor(fY);
		int LIZ = (int) Math.floor(fZ);
		
		float v1 = Interpolate(grid[LIX][LIY][LIZ], grid[LIX+1][LIY][LIZ], fX-LIX);
		float v2 = Interpolate(grid[LIX][LIY+1][LIZ], grid[LIX+1][LIY+1][LIZ], fX-LIX);
		float v3 = Interpolate(v1, v2, fY-LIY);
		
		float v4 = Interpolate(grid[LIX][LIY][LIZ+1], grid[LIX+1][LIY][LIZ+1], fX-LIX);
		float v5 = Interpolate(grid[LIX][LIY+1][LIZ+1], grid[LIX+1][LIY+1][LIZ+1], fX-LIX);
		float v6 = Interpolate(v4, v5, fY-LIY);
		return Interpolate(v3, v6, fZ-LIZ);
		
	}
	
	public static Matrix4f Billboard(Matrix4f reuse, Matrix4f view, Vector3f position, Vector3f scale){
		
		reuse.setIdentity();
		reuse.translate(position);
		reuse.m00 = view.m00;
		reuse.m01 = view.m10;
		reuse.m02 = view.m20;
		reuse.m10 = view.m01;
		reuse.m11 = view.m11;
		reuse.m12 = view.m21;
		reuse.m20 = view.m02;
		reuse.m21 = view.m12;
		reuse.m22 = view.m22;
		reuse.scale(scale);
		return reuse;
		
	}
	
	public static boolean ccw(float Ax, float Ay, float Bx, float By, float Cx, float Cy){
		
		return (Cy-Ay)*(Bx-Ax) > (By-Ay)*(Cx-Ax);
		
	}
	
//	public static boolean Intersection(float ax, float ay, float bx, float by, float cx, float cy, float dx, float dy){
//		
//		System.out.println(ax + " " + ay + " " + bx + " " + by + " " + cx + " " + cy + " " + dx + " " + dy);
//		return ccw(ax,ay,cx,cy,dx,dy) != ccw(bx,by,cx,cy,dx,dy) && ccw(ax,ay,bx,by,cx,cy);
//		
//	}
	
	public static boolean Intersection(float a0, float b0, float a1, float b1, float a2, float b2, float a3, float b3){
		
		return ((a2-a0)*(b1-b0) - (b2-b0)*(a1-a0)) * ((a3-a0)*(b1-b0) - (b3-b0)*(a1-a0)) < 0 && ((a0-a2)*(b3-b2) - (b0-b2)*(a3-a2)) * ((a1-a2)*(b3-b2) - (b1-b2)*(a3-a2)) < 0;
		
	}
	
//	public static float Intersection(float ax1, float ay1, float alx, float aly, float bx1, float by1, float blx, float bly){
//		
////		System.out.println(ax1 + " " + ay1 + " " + alx + " " + aly + " " + bx1 + " " + by1 + " " + blx + " " + bly);
//		float[] b_m_a = Subract(bx1, by1, ax1, ay1);
//		float bma_c_bl = Cross(b_m_a[0], b_m_a[1], blx, bly);
//		float al_c_b1 = Cross(alx, aly, blx, bly);
//		float v = Math.abs(bma_c_bl / al_c_b1);
//		if(v < 0 || v > 1){
//			return Float.NaN;
//		}
//		return v;
//		
//	}
	
	public static float Cross(float ax, float ay, float bx, float by){
		
		return (ax*by) - (ay*bx);
		
	}
	
	public static float[] Divide(float ax, float ay, float bx, float by){
		
		return new float[]{ax / bx, ay / by};
		
	}
	
	public static float[] Subract(float ax, float ay, float bx, float by){
		
		return new float[]{ax - bx,ay - by};
		
	}
	
	public static float Length(float x1, float y1, float x2, float y2, float sx, float sy){
		
		float x1r = x1/sx;
		float y1r = y1/sy;
		float x2r = x2/sx;
		float y2r = y2/sy;
		return (float) Math.sqrt(Math.pow(x2r - x1r, 2) + Math.pow(y2r - y1r, 2));
		
	}
	
	public static float Dot(float x1, float y1, float x2, float y2){
		
		StorageV2.x = x1;
		StorageV2.y = y1;
		StorageV2_2.x = x2;
		StorageV2_2.y = y2;
		return Vector2f.dot(StorageV2, StorageV2_2);
		
	}
	
	public static float[] GetValid(float[] original){
		
		int count = 0;
		for(float f : original){
			if(f < 0){
				count++;
			}
		}
		float[] n = new float[count];
		int current = 0;
		for(float f : original){
			if(f < 0){
				n[current++] = f;
			}
		}
		return n;
		
	}
	
	public static Vector2f[] GetValidDirections(float[] original){
		
		int count = 0;
		for(float f : original){
			if(f < 0){
				count++;
			}
		}
		Vector2f[] n = new Vector2f[count];
		int current = 0;
		int index = 0;
		Vector2f[] vectors = new Vector2f[]{new Vector2f(-1,-1), new Vector2f(-1,1), new Vector2f(1,1), new Vector2f(1,-1)};
		for(float f : original){
			if(f < 0){
				n[current++] = vectors[index];
			}
			index++;
		}
		return n;
		
	}
	
	public static boolean InBox(float x, float y, float s, float x2, float y2){
		
		Rectangle r = new Rectangle((int)(x+s/2), (int)(y+s/2), (int)(s+s/2), (int)(s+s/2));
		return r.contains(x2, y2);
		
	}
	
	public static boolean Intersection(float x, float y, float s, float x1, float y1, float s1){
		
		float s2 = s / 2;
		float s12 = s1 / 2;
		if(x+s-s2 < x1-s12) return false;
		if(x-s2 > x1+s1-s12) return false;
		if(y+s-s2 < y1-s12) return false;
		if(y-s2 > y1+s1-s12) return false;
		return true;
		
	}
	
	public static int CenterI(int target){
		
		return (int) Math.ceil(target / 2f) - 1;
		
	}
	
	public static boolean IsMouseInBox(float x, float y, float sx, float sy){
		
		return Mouse.getX() > x - sx && Mouse.getX() < x + sx && MouseHelper.MouseY > y - sy && MouseHelper.MouseY < y + sy;
		
	}
	
	//Creates a one-time use 2D matrix in a memory efficient manner from pixel coordinates
	public static Matrix4f CreateTransformationMatrixPixelTemp(float x, float y, float sx, float sy) {
		
		StorageM.setIdentity();
		StorageV2.x = ((x / Display.getWidth()) * 2) - 1;
		StorageV2.y = -(((y / Display.getHeight()) * 2) - 1);
		Matrix4f.translate(StorageV2, StorageM, StorageM);
		StorageV3.x = sx / Display.getWidth();
		StorageV3.y = sy / Display.getHeight();
		StorageV3.z = 1f;
		Matrix4f.scale(StorageV3, StorageM, StorageM);
		return StorageM;
		
	}
	
	//Creates a one-time use 2D matrix in a memory efficient manner
	public static Matrix4f CreateTransformationMatrixTemp(float x, float y, float sx, float sy) {
		
		StorageM.setIdentity();
		StorageV2.x = x;
		StorageV2.y = y;
		Matrix4f.translate(StorageV2, StorageM, StorageM);
		StorageV3.x = sx;
		StorageV3.y = sy;
		StorageV3.z = 1f;
		Matrix4f.scale(StorageV3, StorageM, StorageM);
		return StorageM;
		
	}
	
//	public static Vector2f GetOffset(Direction direction, float length){
//		
//		if(direction == Direction.UP){
//			return new Vector2f(0, length);
//		}else if(direction == Direction.DOWN){
//			return new Vector2f(0, -length);
//		}else if(direction == Direction.RIGHT){
//			return new Vector2f(length, 0);
//		}else if(direction == Direction.LEFT){
//			return new Vector2f(-length, 0);
//		}
//		return new Vector2f();
//		
//	}
	
//	public static int[] Offset(Direction direction, int x, int y){
//		
//		if(direction == Direction.UP){
//			y++;
//		}else if(direction == Direction.DOWN){
//			y--;
//		}else if(direction == Direction.RIGHT){
//			x++;
//		}else if(direction == Direction.LEFT){
//			x--;
//		}
//		return new int[]{x, y};
//		
//	}
//	
//	public static Direction GetDirection(float xOrigin, float yOrigin, float xPoint, float yPoint){
//		
//		float xDistance = xPoint - xOrigin;
//		float yDistance = yPoint - yOrigin;
//		if(Math.abs(xDistance) > Math.abs(yDistance)){
//			if(xDistance > 0){
//				return Direction.RIGHT;
//			}else{
//				return Direction.LEFT;
//			}
//		}else{
//			if(yDistance > 0){
//				return Direction.UP;
//			}else{
//				return Direction.DOWN;
//			}
//		}
//		
//	}
	
	//Converts pixel coordinates into OpenGL format
	public static Vector2f ToGL(Vector2f position){
		
		return new Vector2f(
				((position.x / Display.getWidth()) * 2) - 1,
				-(((position.y / Display.getHeight()) * 2) - 1)
		);
		
	}
	
	//Converts pixel coordinates into OpenGL scale format
	public static Vector2f ToGLScale(Vector2f position){
		
		return new Vector2f(
				position.x / Display.getWidth(),
				position.y / Display.getHeight()
		);
		
	}
	
	//Converts pixel coordinates into OpenGL format
	public static Vector2f ToGL(float x, float y){
		
		return new Vector2f(
				((x / Display.getWidth()) * 2) - 1,
				-(((y / Display.getHeight()) * 2) - 1)
		);
		
	}
	
	//Converts pixel coordinates into OpenGL scale format
	public static Vector2f ToGLScale(float x, float y){
		
		return new Vector2f(
				x / Display.getWidth(),
				y / Display.getHeight()
		);
		
	}
	
	//Creates a transformation matrix for GUI use
	public static Matrix4f CreateTransformationMatrix(Vector2f position, Vector2f scale) {
		
//		Matrix4f matrix = new Matrix4f();
//		matrix.setIdentity();
//		Matrix4f.translate(position, matrix, matrix);
//		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
//		return matrix;
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
		
	}
	
	//Creates a transformation matrix for GUI use
	public static Matrix4f CreateTransformationMatrix(Vector2f position, Vector2f scale, float rotation) {
		
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation), Z, matrix, matrix);
		return matrix;
		
	}
	
	//Generates a simplex noise field
//	public static float[][] GetNoise(OpenSimplexNoise generator, float smoothness, int SX, int SY){
//		
//		float[][] noise = new float[SX][SY];
//		
//		for(int x = 0; x < SX; x++){
//			for(int y = 0; y < SY; y++){
//				double val = generator.eval(x / smoothness, y / smoothness);
//				val += generator.eval(x / smoothness / 2, y / smoothness / 2) / 2;
//				val += generator.eval(x / smoothness / 4, y / smoothness / 4) / 4;
//				noise[x][y] = (float) val;
//			}
//		}
//		
//		return noise;
//		
//	}
	
	//Gets interpolated 2D grid value
	public static float GetValue(float[][] grid, float x, float y){
		
		int X = (int) x;
		int Y = (int) y;
		float fX = x - X;
		float fY = y - Y;
//		float v1 = GetOppositeValue(grid, X, Y);
//		float v2 = GetOppositeValue(grid, X + 1, Y);
//		float v3 = GetOppositeValue(grid, X, Y + 1);
//		float v4 = GetOppositeValue(grid, X + 1, Y + 1);
		float v1 = grid[X][Y];
		float v2 = grid[X + 1][Y];
		float v3 = grid[X][Y + 1];
		float v4 = grid[X + 1][Y + 1];
		float i1 = Interpolate(v1, v2, fX);
		float i2 = Interpolate(v3, v4, fX);
		return Interpolate(i1, i2, fY);
		
	}
	
	//Gets interpolated value based on blend factor
	public static float Interpolate(float a, float b, float blend){
		
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
		
	}
	
	//Gets barry centric interpolating value
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
		
	}
	
	//Smoothes grid and returns self, smoothness variable effects how far away the sample points are chosen
	public static float[][] SmoothGrid(float[][] grid, int smoothness){
		
		float[][] smooth_grid = new float[grid.length][grid.length];
		
		for(int x = 0; x < grid.length; x++){
			for(int y = 0; y < grid.length; y++){
				smooth_grid[x][y] = Average9(grid, x, y, smoothness);
			}
		}
		
		return smooth_grid;
		
	}
	
	//Currently unused
//	public static float[][] SmoothGrid(float[][] grid, int smoothness, int iterations){
//		
//		float[][] smooth_grid = grid;
//		
//		for(int i = 0; i < iterations; i++){
//			smooth_grid = SmoothGrid(smooth_grid, smoothness);
//		}
//		
//		return smooth_grid;
//		
//	}
	
	//Returns the weighted average of 9 sample points centered at x, y. Smoothness variable defines how far away the surrounding 8 sample points are from the center
	private static float Average9(float[][] grid, int x, int y, int smoothness){
		
		float corners = (GetValue(grid, x - smoothness, y - smoothness) + GetValue(grid, x + smoothness, y + smoothness) + GetValue(grid, x + smoothness, y - smoothness) + GetValue(grid, x - smoothness, y + smoothness)) / 16f;
		float edges = (GetValue(grid, x + smoothness, y) + GetValue(grid, x - smoothness, y) + GetValue(grid, x, y + smoothness) + GetValue(grid, x, y - smoothness)) / 8f;
		float center = GetValue(grid, x, y) / 4f;
		return corners + edges + center;
		
	}
	
	//Returns 2D grid value if in bounds, else returns 0
	private static float GetValue(float[][] grid, int x, int y){
		
		int XN = x;
		int YN = y;
		if(x < 0){
			XN = 0;
		}else if(x >= grid.length){
			XN = grid.length - 1;
		}
		if(y < 0){
			YN = 0;
		}else if(y >= grid.length){
			YN = grid.length - 1;
		}
		return grid[XN][YN];
		
	}
	
//	private static float GetValue0(float[][] grid, int x, int y){
//		
//		if(x < 0 || x >= grid.length || y < 0 || y >= grid.length){
//			return 0;
//		}
//		return grid[x][y];
//		
//	}
	
	//Returns the distance in between two 3D points
	public static float distance(Vector3f v1, Vector3f v2){
		
		float dx = v1.x - v2.x;
		float dy = v1.y - v2.y;
		float dz = v1.z - v2.z;
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		
	}

	//Returns the distance in between two 2D points
	public static float distance(Vector2f v1, Vector2f v2){
	
		float dx = v1.x - v2.x;
		float dy = v1.y - v2.y;
		return (float) Math.sqrt(dx * dx + dy * dy);
	
	}
	
	//Returns random value in between -1 and 1
	public static float nextFloat(Random random){
		
		return (random.nextFloat() * 2) - 1;
		
	}
	
	//Returns random value in between -1 and 1
	public static float nextFloat(){
		
		return (RANDOM.nextFloat() * 2) - 1;
		
	}
	
	//Returns random value in between 0 and max
	public static float nextFloat(float max, Random random){
		
		return random.nextFloat() * max;
		
	}
	
	//Returns random value in between min and max
	public static float nextFloat(float min, float max, Random random){
		
		return (random.nextFloat() * (max-min)) + min;
		
	}
	
	//Returns random value in between min and max
	public static float nextFloat(float min, float max){
		
		return (RANDOM.nextFloat() * (max-min)) + min;
		
	}
	
}
