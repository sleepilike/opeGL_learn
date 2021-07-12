package com.example.opegl_learn.view

import android.content.Context
import android.opengl.GLSurfaceView
import com.example.opegl_learn.render.MyGLRender
import com.example.opegl_learn.render.Triangle2Render

/**
 * Created by zyy on 2021/7/7
 *
 */
class Triangle2SurfaceView (context: Context)  : GLSurfaceView(context){
    private var renderer : Triangle2Render

    init {
        setEGLContextClientVersion(2)
        renderer =Triangle2Render(context)
        setRenderer(renderer);

    }
}