package com.cloudx.ktx.core

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

/**
 * @Author petterp
 * @Date 2020/6/11-12:14 PM
 * @Email ShiyihuiCloud@163.com
 * @Function
 */
object ApplicationActivity {
    var ACTIVITY: Activity? = null
    private var appCount = 0
    private var isResume = false
    private var iLifecycleCallbacks: ILifecycleCallbacks? = null
    fun setLifecycleCallbacks(iLifecycleCallbacks: ILifecycleCallbacks?): ApplicationActivity {
        this.iLifecycleCallbacks = iLifecycleCallbacks
        return this
    }


    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityResumed(activity: Activity) {
                ACTIVITY=activity
            }

        })
    }
}

interface ILifecycleCallbacks {
    fun lifecycleResume()
    fun lifecycleStop()
}
