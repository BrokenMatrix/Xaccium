package game;

import game.entities.Player;
import game.tools.KeyboardListener;
import game.tools.MouseHelper;
import game.tools.TextureLoader;
import game.tools.VertexObjectHelper;
import game.tools.VoxelSubMeshConstructor;
import game.world.World;
import rendering.Renderer;
import rendering.Window;

public class Main {

	public static void main(String[] args) {
		
		Window.Create(1300, 700, 30);
		VertexObjectHelper.Create();
		TextureLoader.Create();
		MouseHelper.Create();
		VoxelSubMeshConstructor.Create();
		Renderer.Create();
		World.Create();
		Player.create();
		
		while(!Window.IsCloseRequested()){
			
			MouseHelper.Update();
			KeyboardListener.Update();
			Player.update();
			
			Renderer.Render();
			
			Window.Update();
			
		}
		
		Renderer.Destroy();
		TextureLoader.Destroy();
		VertexObjectHelper.Destroy();
		Window.Destroy();

	}

}
