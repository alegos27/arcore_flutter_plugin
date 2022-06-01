package com.difrancescogianmarco.arcore_flutter_plugin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import com.difrancescogianmarco.arcore_flutter_plugin.utils.ArCoreUtils
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Node
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView

open class BaseArCoreView(
    val activity: Activity,
    context: Context,
    messenger: BinaryMessenger,
    id: Int,
    protected val debug: Boolean
) : PlatformView, MethodChannel.MethodCallHandler {

    lateinit var activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks
    protected val methodChannel: MethodChannel =
        MethodChannel(messenger, "arcore_flutter_plugin_$id")
    protected var arSceneView: ArSceneView? = null

    //    protected val activity: Activity = (context.applicationContext as FlutterApplication).currentActivity
    protected val RC_PERMISSIONS = 0x123
    protected var installRequested: Boolean = false
    private val TAG: String = BaseArCoreView::class.java.name
    protected var isSupportedDevice = false

    init {
        methodChannel.setMethodCallHandler(this)
        if (ArCoreUtils.checkIsSupportedDeviceOrFinish(activity)) {
            isSupportedDevice = true
            arSceneView = ArSceneView(context)
            ArCoreUtils.requestCameraPermission(activity, RC_PERMISSIONS)
            setupLifeCycle()
        }
    }

    protected fun debugLog(message: String) {
        if (debug) {
            debugLog(message)
        }
    }

    private fun setupLifeCycle() {
        activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                debugLog("onActivityCreated")
            }

            override fun onActivityStarted(activity: Activity) {
                debugLog("onActivityStarted")
            }

            override fun onActivityResumed(activity: Activity) {
                debugLog("onActivityResumed")
                onResume()
            }

            override fun onActivityPaused(activity: Activity) {
                debugLog("onActivityPaused")
                onPause()
            }

            override fun onActivityStopped(activity: Activity) {
                debugLog("onActivityStopped")
                onPause()
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                debugLog("onActivityDestroyed")
//                onDestroy()
            }
        }

        activity.application
            .registerActivityLifecycleCallbacks(this.activityLifecycleCallbacks)
    }

    override fun getView(): View {
        return arSceneView as View
    }

    override fun dispose() {
        if (arSceneView != null) {
            onPause()
            onDestroy()
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {

    }

    open fun onResume() {

//        if (arSceneView?.session == null) {
//
//            // request camera permission if not already requested
//            if (!ArCoreUtils.hasCameraPermission(activity)) {
//                ArCoreUtils.requestCameraPermission(activity, RC_PERMISSIONS)
//            }
//
//            // If the session wasn't created yet, don't resume rendering.
//            // This can happen if ARCore needs to be updated or permissions are not granted yet.
//            try {
//                val session = ArCoreUtils.createArSession(activity, installRequested, isAugmentedFaces)
//                if (session == null) {
//                    installRequested = ArCoreUtils.hasCameraPermission(activity)
//                    return
//                } else {
//                    val config = Config(session)
//                    if (isAugmentedFaces) {
//                        config.augmentedFaceMode = Config.AugmentedFaceMode.MESH3D
//                    }
//                    config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
//                    session.configure(config)
//                    arSceneView?.setupSession(session)
//                }
//            } catch (e: UnavailableException) {
//                ArCoreUtils.handleSessionException(activity, e)
//            }
//        }
//
//        try {
//            arSceneView?.resume()
//        } catch (ex: CameraNotAvailableException) {
//            ArCoreUtils.displayError(activity, "Unable to get camera", ex)
//            activity.finish()
//            return
//        }
    }


    fun removeNode(node: Node) {
        arSceneView?.scene?.removeChild(node)
        debugLog("removed ${node.name}")
    }

    fun onPause() {
        debugLog("onPause()")
        if (arSceneView != null) {
            arSceneView?.pause()
        }
    }

    open fun onDestroy() {
        if (arSceneView != null) {
            arSceneView?.destroy()
            arSceneView = null
        }
    }
}