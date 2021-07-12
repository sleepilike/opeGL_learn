package com.example.opegl_learn.activity

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opegl_learn.R
import com.example.opegl_learn.view.MyGLSurfaceView
import com.example.opegl_learn.view.Square2SurfaceView

class Square2Activity : AppCompatActivity() {
    //lateinit var 延迟初始化
    private lateinit var squareView : Square2SurfaceView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        squareView = Square2SurfaceView(this)
        setContentView(squareView)
    }
}