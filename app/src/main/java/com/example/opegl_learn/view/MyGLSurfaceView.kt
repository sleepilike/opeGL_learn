package com.example.opegl_learn.view

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.example.opegl_learn.render.MyGLRender

/**
 * Created by zyy on 2021/7/1
 *
 */
private  const val TOUCH_SCALE_FACTOR : Float = 180.0f / 320f

private var previousX : Float = 0f
private var previousY : Float = 0f
class MyGLSurfaceView(context : Context) :GLSurfaceView(context) {

    private var renderer : MyGLRender



    init {
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        renderer = MyGLRender();
        setRenderer(renderer);
        //默认渲染方式为RENDERMODE_CONTINUOUSLY
        // 当设置为RENDERMODE_CONTINUOUSLY时渲染器会不停地渲染场景，
        // 当设置为RENDERMODE_WHEN_DIRTY时只有在创建和调用requestRender()时才会刷新。
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val x : Float = event.x
        val y : Float = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                var dx : Float = x - previousX
                var dy : Float = y - previousY

                if(y>height/2){
                    dx *= -1
                }
                if(x<width/2){
                    dy *= -1
                }
                renderer.angle += (dx+dy) * TOUCH_SCALE_FACTOR
                requestRender()
            }
        }
        previousX = x
        previousY = y
        return true
    }
}