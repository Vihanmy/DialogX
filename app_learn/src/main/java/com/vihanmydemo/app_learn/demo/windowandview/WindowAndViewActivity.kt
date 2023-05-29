package com.vihanmydemo.app_learn.demo.windowandview

import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
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
        }

    }
}

/**
 * 添加View到Activity所在的Window中, 注意避免 window leak 的问题
 *
 * @param activity
 */
private fun addView2ActivityWindow(activity: AppCompatActivity) {
    val manager = activity.getSystemService(AppCompatActivity.WINDOW_SERVICE) as WindowManager
    val addedView = LayoutInflater.from(activity).inflate(R.layout.wav_view_4_window_add, null).apply {
        setOnClickListener {
            it.isVisible = false
            Toast.makeText(activity, "添加的view被点击", Toast.LENGTH_SHORT).show()
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
