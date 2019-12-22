package kr.co.kinetic27.sample3d

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.hardware.camera2.CameraCharacteristics.*
import android.util.Size
import android.view.Surface
import java.util.*


class Camera2APIs constructor(
    private val mInterface: Camera2Interface,
    private val mCaptureCallback: CameraCaptureSession.CaptureCallback
) {
    private var mCameraSize: Size? = null
    private var mCaptureSession: CameraCaptureSession? = null
    private var mCameraDevice: CameraDevice? = null
    private var mPreviewRequestBuilder: CaptureRequest.Builder? = null

    private val mCameraDeviceStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            mCameraDevice = camera
            mInterface.onCameraDeviceOpened(camera, mCameraSize)
        }

        override fun onDisconnected(camera: CameraDevice) = camera.close()

        override fun onError(camera: CameraDevice, error: Int) = camera.close()
    }

    private val mCaptureSessionCallback = object : CameraCaptureSession.StateCallback() {
        override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
            mCaptureSession = cameraCaptureSession
            mPreviewRequestBuilder!![CaptureRequest.CONTROL_AF_MODE] =
                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE

            cameraCaptureSession.setRepeatingRequest(mPreviewRequestBuilder!!.build(), mCaptureCallback, null)
        }

        override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) = Unit
    }


    interface Camera2Interface {
        fun onCameraDeviceOpened(cameraDevice: CameraDevice, cameraSize: Size?)
    }

    fun cameraManager(activity: Activity): CameraManager =
        activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    fun cameraCharacteristics(cameraManager: CameraManager): String? {
        try {
            cameraManager.cameraIdList.forEach { cameraId ->
                val characteristics = cameraManager.getCameraCharacteristics(cameraId)

                if (characteristics[LENS_FACING] == LENS_FACING_BACK) {
                    val sizes =
                        characteristics[SCALER_STREAM_CONFIGURATION_MAP]!!.getOutputSizes(SurfaceTexture::class.java)

                    mCameraSize = sizes[0]

                    sizes.asSequence()
                        .filter { it.width > mCameraSize!!.width }
                        .forEach { mCameraSize = it }

                    return cameraId
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        return null
    }

    @SuppressLint("MissingPermission")
    fun cameraDevice(cameraManager: CameraManager, cameraId: String) =
        cameraManager.openCamera(cameraId, mCameraDeviceStateCallback, null)

    fun captureSession(cameraDevice: CameraDevice, surface: Surface) =
        cameraDevice.createCaptureSession(Collections.singletonList(surface), mCaptureSessionCallback, null)

    fun captureRequest(cameraDevice: CameraDevice, surface: Surface) {
        mPreviewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
            addTarget(surface)
        }
    }

    fun closeCamera() {
        mCaptureSession?.close()
        mCaptureSession = null

        mCameraDevice?.close()
        mCameraDevice = null
    }
}