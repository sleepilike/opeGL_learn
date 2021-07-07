package com.example.opegl_learn.activity

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opegl_learn.R
import com.example.opegl_learn.view.MyGLSurfaceView
import com.example.opegl_learn.view.Triangle2SurfaceView

class Triangle2Activity : AppCompatActivity() {

    //lateinit var 延迟初始化
    private lateinit var gLView : Triangle2SurfaceView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gLView = Triangle2SurfaceView(this)
        setContentView(gLView)
    }
}