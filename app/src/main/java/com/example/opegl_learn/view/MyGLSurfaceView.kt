package com.example.opegl_learn.view

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.opegl_learn.render.MyGLRender

/**
 * Created by zyy on 2021/7/1
 *
 */
class MyGLSurfaceView(context : Context) :GLSurfaceView(context) {

    private var renderer : MyGLRender


    init {
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        renderer = MyGLRender();
        setRenderer(renderer);
    }
}