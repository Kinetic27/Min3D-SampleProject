package kr.co.kinetic27.sample3d.Example;

import kr.co.kinetic27.min3d.core.Object3dContainer;
import kr.co.kinetic27.min3d.core.RendererActivity;
import kr.co.kinetic27.min3d.objectPrimitives.Box;
import kr.co.kinetic27.min3d.vos.Light;
import android.view.View;
import android.widget.LinearLayout;
import kr.co.kinetic27.sample3d.R;

/**
 * Example of adding an OpenGL scene within a conventional Android application layout.
 * Entails overriding RenderActivity's onCreateSetContentView() function, and
 * adding _glSurfaceView to the appropriate View...  
 * 
 * @author Lee
 */
public class ExampleInsideLayout extends RendererActivity implements View.OnClickListener
{
	Object3dContainer _cube;
	
	@Override
	protected void onCreateSetContentView()
	{
		setContentView(R.layout.custom_layout_example);
		
        LinearLayout ll = this.findViewById(R.id.scene1Holder);
        ll.addView(_glSurfaceView);
	}

    public void onClick(View $v)
    {
    	finish();
    }
    
    //
	
	public void initScene() 
	{
		scene.lights().add(new Light());
		
		scene.backgroundColor().setAll(0xff444444);
		_cube = new Box(1,1,1);
		scene.addChild(_cube);
	}

	@Override 
	public void updateScene() 
	{
		_cube.rotation().y++;
	}
	
}

