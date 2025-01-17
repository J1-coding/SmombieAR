package com.example.smombie.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

@SuppressLint("ViewConstructor")
open class OverlayView(
    val mContext: Context, val mLifecycle: LifecycleOwner
) : FrameLayout(mContext), DefaultLifecycleObserver {

    private val mWindowManager: WindowManager
    private val mParams: WindowManager.LayoutParams

    init {
        mWindowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = GravityCompat.getAbsoluteGravity(Gravity.TOP, ViewCompat.LAYOUT_DIRECTION_LTR)
            windowAnimations = android.R.style.Animation_Translucent
        }
    }

    open fun show() {
        if (isShown) return
        mWindowManager.addView(this, mParams)
    }

    open fun hide() {
        if (isShown.not()) return
        mWindowManager.removeView(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        mWindowManager.removeView(this)
    }
}