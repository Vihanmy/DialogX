package com.vihanmydemo.app_learn.demo.windowandview

import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
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
        findViewById<View>(R.id.textView1).setOnClickListener {
            addView2RootFrameLayout(this)
        }
        findViewById<View>(R.id.textView2).setOnClickListener {
            changeNavigationBarOrStatusBarBackground(this)
        }
    }
}

/**
 * 添加View到Activity所在的Window中, 注意避免 window leak 的问题
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

/**
 * Activity 的 DecorView 是一个 FrameLayout, 可以通过向这个FrameLayout添加View以实现显示弹窗的功能
 * 添加的区域能够覆盖到状态栏和导航栏区域,但是被导航栏和状态栏覆盖
 */
private fun addView2RootFrameLayout(activity: AppCompatActivity) {
    val addedView = LayoutInflater.from(activity).inflate(R.layout.wav_view_4_window_add, null).apply {
        setOnClickListener {
            it.isVisible = false
            Toast.makeText(activity, "添加的view被点击", Toast.LENGTH_SHORT).show()
        }
    }
    val decorView: FrameLayout? = (activity.window.decorView as? FrameLayout)
    decorView?.addView(addedView)
}

/**
 * 改变导航栏和状态栏占位view的背景色
 */
private fun changeNavigationBarOrStatusBarBackground(
    activity: AppCompatActivity
) {
    val decorView = activity.window.decorView

    val statusBarPlaceholderView = decorView.findViewById<View>(android.R.id.statusBarBackground)
    val navigationBarPlaceholderView = decorView.findViewById<View>(android.R.id.navigationBarBackground)

    statusBarPlaceholderView.setBackgroundColor(Color.MAGENTA)
    navigationBarPlaceholderView.setBackgroundColor(Color.MAGENTA)
}