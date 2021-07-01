package com.example.opegl_learn.activity

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opegl_learn.R
import com.example.opegl_learn.view.MyGLSurfaceView

class OpenGL20Activity : AppCompatActivity() {

    //lateinit var 延迟初始化
    private lateinit var gLView : GLSurfaceView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gLView = MyGLSurfaceView(this)
        setContentView(gLView)
    }
}