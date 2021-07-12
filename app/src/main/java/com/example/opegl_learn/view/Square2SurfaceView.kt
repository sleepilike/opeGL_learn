package com.example.opegl_learn.view

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.opegl_learn.render.Square2Render

class Square2SurfaceView(context: Context) : GLSurfaceView(context) {
    private var render : Square2Render
    init {
        setEGLContextClientVersion(2);
        render = Square2Render(context)
        setRenderer(render)
    }
}