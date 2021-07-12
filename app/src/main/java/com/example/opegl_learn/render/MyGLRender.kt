package com.example.opegl_learn.render

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import android.util.Log
import androidx.core.graphics.rotationMatrix
import com.example.opegl_learn.sharp.Square2
import com.example.opegl_learn.sharp.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by zyy on 2021/7/1
 * 此类可控制在与之相关联的GLSurfaceView上的绘制内容
 * 开始执行顺序 onSurfaceCreated -> onSurfaceChanged ->onDrawFrame
 */
class MyGLRender : GLSurfaceView.Renderer {

    private lateinit var mTriangle: Triangle
    private lateinit var mSquare2: Square2

    //模型视图投影矩阵
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    //旋转矩阵 动画
    private val rotationMatrix = FloatArray(16)

    //旋转角度
    var angle : Float = 0f
    /**
     * 调用一次以设置视图的OpenGL ES环境
     */
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //清除颜色缓冲区时指定RGBA值
        // （也就是所有的颜色都会被替换成指定的RGBA值）
        // 每个值的取值范围都是0.0~1.0，超出范围的将被截断。
       // GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);

        Log.d("TAG", "onSurfaceCreated: 1")
        //初始化形状
        mTriangle = Triangle()
       // mSquare2 = Square2()


    }

    /**
     * 当视图的几何图形发生变化，如屏幕旋转 时候调用
     * 可以采用投影变换的方式 根据GLSurfaceView 的宽度和高度调整绘制对象的坐标
     */
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d("TAG", "onSurfaceChanged: 3")
        GLES20.glViewport(0,0,width,height);

        //定义投影
        fitProjectionMatrix(width,height)
    }

    /**
     * 在绘制每一帧的时候回调
     */
    override fun onDrawFrame(gl: GL10?) {

        val scratch = FloatArray(16)
        Log.d("TAG", "onDrawFrame: 2")
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)


        Matrix.multiplyMM(vPMatrix,0,projectionMatrix,0,viewMatrix,0)
       // mTriangle.draw(vPMatrix)




       // val time = SystemClock.uptimeMillis() % 4000L
       // val angle = 0.090f * time.toInt()
        Matrix.setRotateM(rotationMatrix,0,angle,0f,0f,-1.0f)
        Matrix.multiplyMM(scratch,0,vPMatrix,0, rotationMatrix,0)
        mTriangle.draw(scratch)

        //mixPV()
       //mTriangle.draw()


    }

    /**
     * 该代码填充了一个投影矩阵 mProjectionMatrix
     * 之后可以将其与 onDrawFrame() 方法中的相机视图转换合并，
     */
    private fun fitProjectionMatrix(width : Int,height: Int){
        val ratio : Float =width.toFloat() / height.toFloat()
        Matrix.frustumM(projectionMatrix,0,-ratio,ratio,-1f,1f,3f,7f)

    }

    /**
     * 相机视图转换使用 Matrix.setLookAtM() 方法进行计算，
     * 然后与之前计算的投影矩阵合并。
     * 之后，系统会将合并后的转换矩阵传递到绘制的形状。
     */
    private fun mixPV(){

        //设置相机的位置
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)


        Matrix.multiplyMM(vPMatrix,0,projectionMatrix,0,viewMatrix,0)

        mTriangle.draw(vPMatrix)
    }



}