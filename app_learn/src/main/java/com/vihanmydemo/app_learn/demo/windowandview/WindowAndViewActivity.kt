package com.vihanmydemo.app_learn.demo.windowandview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.vihanmydemo.app_learn.R

/**
 *# 演示window和view的关系
 * ```
 * author :Vihanmy
 * date   :2023-05-28 22:54
 * desc   :
 * ```
 */
class WindowAndViewActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window_and_view)

        findViewById<View>(R.id.textView).setOnClickListener {
            addView2ActivityWindow(this)
            animateViewScale((this.window.decorView), 0.9f)
            Log.d(TAG, "onCreate: ${this.window.decorView.parent}")
        }
    }
}


fun animateViewScale(view: View, scale: Float) {
    val centerX = view.width / 2f
    val centerY = view.height / 2f

    val scaleX = ObjectAnimator.ofFloat(view, "scaleX", scale)
    val scaleY = ObjectAnimator.ofFloat(view, "scaleY", scale)

    scaleX.setDuration(300)
    scaleY.setDuration(300)

    scaleX.setFloatValues(view.scaleX, scale)
    scaleY.setFloatValues(view.scaleY, scale)

    scaleX.setInterpolator(AccelerateDecelerateInterpolator())
    scaleY.setInterpolator(AccelerateDecelerateInterpolator())

    view.pivotX = centerX
    view.pivotY = centerY

    val animatorSet = AnimatorSet()
    animatorSet.playTogether(scaleX, scaleY)
    animatorSet.start()
}

private fun addView2ActivityWindow(activity: AppCompatActivity) {
    val manager = activity.getSystemService(AppCompatActivity.WINDOW_SERVICE) as WindowManager
    val addedView = LayoutInflater.from(activity).inflate(R.layout.wav_view_4_window_add, null).apply {
        setOnClickListener {
            it.isVisible = false
            Toast.makeText(activity, "测测", Toast.LENGTH_SHORT).show()
        }
    }
    val layoutParams = WindowManager.LayoutParams().apply {
        gravity = Gravity.CENTER_VERTICAL
        format = PixelFormat.TRANSPARENT
    }
    activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            try {
                manager.removeViewImmediate(addedView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    })

    manager.addView(addedView, layoutParams)
}
