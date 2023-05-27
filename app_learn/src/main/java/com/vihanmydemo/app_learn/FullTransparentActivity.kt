package com.vihanmydemo.app_learn

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 *# 一个全透明的activity
 * ```
 * author :Vihanmy
 * date   :2023-05-25 10:46
 * desc   :
 * ```
 * # 注意
 * ```
 *1. 状态栏的透明是通过 values-v21/styles.xml 中 DialogXFloatingWindow 指定的 statusBarColor 实现的
 *
 * ```
 */
class FullTransparentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_transparent)
    }
}