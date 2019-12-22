package kr.co.kinetic27.sample3d

import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.scratch_layout.*
import kr.co.kinetic27.min3d.core.Object3dContainer
import kr.co.kinetic27.min3d.core.RendererActivity
import kr.co.kinetic27.min3d.objectPrimitives.Box
import kr.co.kinetic27.min3d.objectPrimitives.Sphere
import kr.co.kinetic27.min3d.vos.Light

/**
 * 'Scratch' - ignore.
 *
 * @author Lee
 */
class ScratchActivity : RendererActivity() {

    private lateinit var _o1: Object3dContainer
    private lateinit var _o2: Object3dContainer
    private lateinit var _o3: Object3dContainer
    private lateinit var _k: Object3dContainer
    private lateinit var _light: Light

    override fun onCreateSetContentView() {
        setContentView(R.layout.scratch_layout)

        sceneHolder.addView(_glSurfaceView)
    }

    override fun initScene() {
        scene.backgroundColor().setAll(0x0)

        _light = Light().apply {
            position.setAll(0f, 0f, +3f)
            diffuse.setAll(255, 255, 255, 255)
            ambient.setAll(0, 0, 0, 0)
            specular.setAll(0, 0, 0, 0)
            emissive.setAll(0, 0, 0, 0)
        }

        scene.lights().add(_light)

        _o1 = Box(1f, 1f, 1f)
        scene.addChild(_o1)

        _o2 = Sphere(0.5f, 10, 10).apply {
            position().x = 1.0f
        }
        _o1.addChild(_o2)

        _o3 = Sphere(0.5f, 10, 10).apply {
            position().x = 0.75f
        }
        _o2.addChild(_o3)

        _k = _o1.clone().apply {
            position().y = -2f
        }

        scene.addChild(_k)
    }

    override fun updateScene() {
        _o1.rotation().y += 0.33f
        _k.rotation().y -= 0.33f
    }
}
