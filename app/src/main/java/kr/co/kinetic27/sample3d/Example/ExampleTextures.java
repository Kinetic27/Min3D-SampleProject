package kr.co.kinetic27.sample3d.Example;

import kr.co.kinetic27.min3d.Shared;
import kr.co.kinetic27.min3d.Utils;
import kr.co.kinetic27.min3d.core.Object3dContainer;
import kr.co.kinetic27.min3d.core.RendererActivity;
import kr.co.kinetic27.min3d.objectPrimitives.Box;
import kr.co.kinetic27.min3d.vos.Light;
import kr.co.kinetic27.min3d.vos.TextureVo;
import android.graphics.Bitmap;
import kr.co.kinetic27.sample3d.R;

/**
 * Basic example using a texture on an object
 */
public class ExampleTextures extends RendererActivity
{
	Object3dContainer _cube;
	
	public void initScene() 
	{
		scene.lights().add(new Light());
		
		_cube = new Box(1.5f,1.5f,1.5f);
		scene.addChild(_cube);

		// Create a Bitmap. Here we're generating it from an embedded resource,
		// but the Bitmap could be created in any manner (eg, dynamically).
		
		Bitmap b = Utils.makeBitmapFromResourceId(this, R.drawable.uglysquares);
		
		// Upload the Bitmap via TextureManager and assign it a 
		// textureId ("uglysquares").
		
		Shared.textureManager().addTextureId(b, "uglysquares", false);
		
		// Unless you have a specific reason for doing so, recycle the Bitmap,
		// as it is no longer necessary.
		
		b.recycle();

		// Create a TextureVo using the textureId that was previously added 
		// to the TextureManager ("uglysquares").
		
		TextureVo texture = new TextureVo("uglysquares");
		
		// Add it to the TexturesList held by the Object3d, 
		// and it will be duly rendered.

		_cube.textures().add(texture);
	}
	
	@Override 
	public void updateScene() 
	{
		_cube.rotation().y +=1;
		_cube.rotation().z += 0.2f;
	}
}
