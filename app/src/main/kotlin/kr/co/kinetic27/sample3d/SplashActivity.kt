package kr.co.kinetic27.sample3d

import android.Manifest
import android.annotation.SuppressLint
import android.app.ListActivity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.splash_activity.*
import kr.co.kinetic27.sample3d.Example.*


/**
 * Main menu activity
 *
 * @author Lee, Kinetic27 modified
 */
class SplashActivity : ListActivity() {
    private val _items = arrayOf(
        ItemVo("\"Hello, Jupiter\"", ExampleRotatingPlanets::class.java),
        ItemVo("Camera & Obj by Kinetic27", ExampleCameraObjActivity::class.java),
        ItemVo("Minimal example", ExampleMostMinimal::class.java),
        ItemVo("Vertex colors", ExampleVertexColors::class.java),
        ItemVo("Texture", ExampleTextures::class.java),
        ItemVo("Usage of Vertices class", ExampleVerticesVariations::class.java),
        ItemVo("Triangles, lines, points", ExampleRenderType::class.java),
        ItemVo("Camera, frustum (trackball)", ExampleCamera::class.java),
        ItemVo("Multiple lights", ExampleMultipleLights::class.java),
        ItemVo("Animating vertices", ExampleAnimatingVertices::class.java),
        ItemVo("Rendering subset of faces", ExampleSubsetOfFaces::class.java),
        ItemVo("Assigning textures dynamically", ExampleAssigningTexturesDynamically::class.java),
        ItemVo("MIP Mapping (on vs. off)", ExampleMipMap::class.java),
        ItemVo("Texture wrapping", ExampleTextureWrap::class.java),
        ItemVo("Multiple textures", ExampleMultiTexture::class.java),
        ItemVo("Texture offset", ExampleTextureOffset::class.java),
        ItemVo("3D inside layout", ExampleInsideLayout::class.java),
        ItemVo("Fog Example", ExampleFog::class.java),
        ItemVo("Transparent GL Surface", ExampleTransparentGlSurface::class.java),
        ItemVo("Load model from .obj file", ExampleLoadObjFile::class.java),
        ItemVo("Load multiple models from .obj file", ExampleLoadObjFileMultiple::class.java),
        ItemVo("Load model from .3ds file", ExampleLoad3DSFile::class.java),
        ItemVo("Load animated .md2 file", ExampleLoadMD2File::class.java),
        ItemVo("Keyframe animation", ExampleKeyframeAnimation::class.java),
        ItemVo("Using the accelerometer", ExampleAccelerometer::class.java)
    )

    internal inner class ItemVo(var label: String, var cls: Class<*>)

    @SuppressLint("WrongConstant")
    public override fun onCreate(savedInstanceState: Bundle?) {
        val strings = arrayOfNulls<String>(_items.size)

        _items.indices.forEach {
            strings[it] = _items[it].label
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() = Unit

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                AlertDialog.Builder(this@SplashActivity).apply {
                    setCancelable(false)
                    setTitle("권한 경고")
                    setMessage("권한을 거절하면 서비스를 이용하실 수 없습니다")
                    setPositiveButton("OK") { _: DialogInterface, _: Int ->
                        finish()
                    }

                }.show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("권한을 거절하면 서비스 이용이 불가능합니다\n\n[설정] > [권한] 에서 권한을 켜주세요")
            .setPermissions(Manifest.permission.CAMERA)
            .check()

        listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings)

        Linkify.addLinks(splashTitle, 0x07)

        registerForContextMenu(listView)
    }

    public override fun onListItemClick(parent: ListView, v: View, position: Int, id: Long) =
        this.startActivity(Intent(this, _items[position].cls))
}
