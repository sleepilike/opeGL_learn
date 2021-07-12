package com.example.opegl_learn.render

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.example.opegl_learn.sharp.Square2
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class Square2Render(context: Context) : GLSurfaceView.Renderer {

    lateinit var square : Square2
    var context = context
    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        square = Square2(context)
    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        GLES20.glViewport(0,0,p1,p2)
        square.change(p1,p2)
    }

    override fun onDrawFrame(p0: GL10?) {
        // 设置刷新屏幕时候使用的颜色值,顺序是RGBA，值的范围从0~1。这里不会立刻刷新，只有在GLES20.glClear调用时使用该颜色值才刷新。
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        // 使用glClearColor设置的颜色，刷新Surface
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        square.draw()
    }
}