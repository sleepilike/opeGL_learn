package com.example.opegl_learn.render

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.example.opegl_learn.sharp.Square2
import com.example.opegl_learn.sharp.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by zyy on 2021/7/1
 * 此类可控制在与之相关联的GLSurfaceView上的绘制内容
 */
class MyGLRender : GLSurfaceView.Renderer {

    private lateinit var mTriangle: Triangle
    private lateinit var mSquare2: Square2

    /**
     * 调用一次以设置视图的OpenGL ES环境
     */
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //清除颜色缓冲区时指定RGBA值
        // （也就是所有的颜色都会被替换成指定的RGBA值）
        // 每个值的取值范围都是0.0~1.0，超出范围的将被截断。
       // GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);

        //初始化形状
        mTriangle = Triangle()
        mSquare2 = Square2()


    }

    /**
     * 每次重新绘制试图的时候调用
     */
    override fun onDrawFrame(gl: GL10?) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw()
    }

    /**
     * 当视图的几何图形发生变化，如屏幕旋转 时候调用
     */
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0,0,width,height);
    }


}