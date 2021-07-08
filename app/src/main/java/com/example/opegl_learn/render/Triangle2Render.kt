package com.example.opegl_learn.render

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.example.opegl_learn.sharp.Triangle
import com.example.opegl_learn.sharp.Triangle2
import com.example.opegl_learn.utils.GLUtil
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by zyy on 2021/7/7
 *
 */
class Triangle2Render(context: Context) : GLSurfaceView.Renderer{


    private var context : Context = context
    lateinit var triangle : Triangle2

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        triangle = Triangle2(context)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0,0,width,height)
        triangle.change(width,height)

    }

    override fun onDrawFrame(gl: GL10?) {
        // 设置刷新屏幕时候使用的颜色值,顺序是RGBA，值的范围从0~1。这里不会立刻刷新，只有在GLES20.glClear调用时使用该颜色值才刷新。
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        // 使用glClearColor设置的颜色，刷新Surface
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        triangle.draw()
    }
}