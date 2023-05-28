package com.vihanmydemo.app_learn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vihanmydemo.app_learn.demo.FullTransparentActivity
import com.vihanmydemo.app_learn.demo.ScreenShotActivity
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.tvView).setOnClickListener {
            startActivity(Intent(this, FullTransparentActivity::class.java))
        }
        findViewById<View>(R.id.tvView1).setOnClickListener {
            startActivity(Intent(this, ScreenShotActivity::class.java))
        }

        ScreenShotActivity.lastActivityRef = WeakReference(this)
    }
}