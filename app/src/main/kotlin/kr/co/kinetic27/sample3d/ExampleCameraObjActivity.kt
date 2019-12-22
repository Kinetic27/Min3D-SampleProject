package kr.co.kinetic27.sample3d

import android.graphics.PixelFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.util.Size
import android.view.Surface
import android.view.TextureView
import kotlinx.android.synthetic.main.camera_layout_example.*
import kr.co.kinetic27.min3d.core.Object3dContainer
import kr.co.kinetic27.min3d.core.RendererActivity
import kr.co.kinetic27.min3d.parser.IParser
import kr.co.kinetic27.min3d.parser.Parser
import kr.co.kinetic27.min3d.vos.Light


class ExampleCameraObjActivity : RendererActivity(), Camera2APIs.Camera2Interface, TextureView.SurfaceTextureListener {
    private var mCamera: Camera2APIs? = null
    private var objModel: Object3dContainer? = null

    override fun onCreateSetContentView() {

        setContentView(R.layout.camera_layout_example)
        sceneHolder.addView(_glSurfaceView)

        textureView.surfaceTextureListener = this
    }

    override fun glSurfaceViewConfig() = with(_glSurfaceView) {
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        holder.setFormat(PixelFormat.TRANSLUCENT)

        setZOrderOnTop(true)

    }

    override fun initScene() {
        scene.backgroundColor().setAll(0x00000000)
        scene.lights().add(Light())

        val parser: IParser? = Parser.createParser(
            Parser.Type.OBJ,
            resources, "kr.co.kinetic27.sample3d:raw/camaro_obj", true
        )?.apply {
            parse()
        }

        objModel = parser?.parsedObject?.apply {
            with(scale()) {
                z = .7f
                y = .7f
                x = .7f
            }
        }

        scene.addChild(objModel)
    }

    override fun updateScene() {
        with(objModel!!.rotation()) {
            x++
            z++
        }
    }


    override fun onCameraDeviceOpened(cameraDevice: CameraDevice, cameraSize: Size?) {
        textureView.surfaceTexture.setDefaultBufferSize(cameraSize!!.width, cameraSize.height)

        val surface = Surface(textureView.surfaceTexture)

        with(mCamera!!) {
            captureSession(cameraDevice, surface)
            captureRequest(cameraDevice, surface)
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        textureView.surfaceTextureListener = this

        mCamera = Camera2APIs(this, object : CameraCaptureSession.CaptureCallback() {}).apply {
            val cameraManager = cameraManager(this@ExampleCameraObjActivity)
            val cameraId = cameraCharacteristics(cameraManager)
            cameraDevice(cameraManager, cameraId!!)
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) = Unit

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        mCamera?.closeCamera()
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) = Unit
}
