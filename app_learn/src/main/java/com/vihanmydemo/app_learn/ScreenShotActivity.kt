package com.vihanmydemo.app_learn

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.getRootWindowInsets
import java.lang.ref.WeakReference


/**
 *# 获取当前Activity和被压栈的Activity的截图案例
 * ```
 * author :Vihanmy
 * date   :2023-05-25 11:02
 * desc   :
 * ```
 *
 * # 注意
 * 1. 本质上是获取view截图, 获取屏幕就是获取当前 Activity.Window.DecorView 的截图
 * 2. 被压栈的Activity, 只要获取到了引用, 也可以获取其 decorView 的截图
 */
class ScreenShotActivity : AppCompatActivity() {

    companion object {
        var lastActivityRef: WeakReference<Activity>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_shot)

        //【】获取当前Activity的截图
        findViewById<TextView>(R.id.textView).setOnClickListener {
            val currentActivityDecorView = this.window.decorView as? ViewGroup ?: return@setOnClickListener
            val currentActivityDecorViewBitmap = getBitmapOfView(currentActivityDecorView) ?: return@setOnClickListener
            findViewById<ImageView>(R.id.imageView).setImageBitmap(currentActivityDecorViewBitmap)
        }

        //【】获取被压栈Activity的截图
        findViewById<TextView>(R.id.textView1).setOnClickListener {
            val lastActivity = lastActivityRef?.get() ?: return@setOnClickListener
            val currentActivityDecorView = lastActivity.window.decorView as? ViewGroup ?: return@setOnClickListener
            val currentActivityDecorViewBitmap = getBitmapOfView(currentActivityDecorView) ?: return@setOnClickListener
            findViewById<ImageView>(R.id.imageView).setImageBitmap(currentActivityDecorViewBitmap)
        }

        window.decorView.post {

            val insets = getRootWindowInsets(window.decorView);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val colorTopLeft = insets?.toWindowInsets()?.getRoundedCorner(RoundedCorner.POSITION_TOP_LEFT)
                val colorTopRight = insets?.toWindowInsets()?.getRoundedCorner(RoundedCorner.POSITION_TOP_RIGHT)
                val colorBottomRight = insets?.toWindowInsets()?.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_RIGHT)
                val colorBottomLeft = insets?.toWindowInsets()?.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_LEFT)
                Log.d("TAG", "onCreate: colorTopLeft:$colorTopLeft colorTopRight:$colorTopRight colorBottomRight:$colorBottomRight colorBottomLeft:$colorBottomLeft")
            }
        }

    }
}

/**
 * 获取view的bitmap的截图
 */
private fun getBitmapOfView(view: View): Bitmap? {
    view.buildDrawingCache()
    val rect = Rect()
    view.getWindowVisibleDisplayFrame(rect)
    view.setDrawingCacheEnabled(true)
    val bitmap = Bitmap.createBitmap(view.drawingCache, 0, 0, view.width, view.height)
    view.destroyDrawingCache()
    return bitmap
}