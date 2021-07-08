package com.example.opegl_learn.sharp

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLU
import android.opengl.Matrix
import com.example.opegl_learn.config.Config
import com.example.opegl_learn.utils.GLUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by zyy on 2021/7/7
 *
 * 第二次编写 三角形
 * 正交投影
 * https://www.jianshu.com/p/4a014afde409
 */

//着色器代码位置

class Triangle2 (context: Context){

   // private var context  = context.applicationContext

    //顶点坐标的位置 OpenGL中的坐标系是从[-1，1]
    val TRIANGLE_COORDS = floatArrayOf(
        //x,y,z
        //0.0f,0.5f,0.0f,
        0.5f,0.5f,0.0f, //top
        -0.5f,-0.5f,0.0f, //bottom left
        0.5f,-0.5f,0.0f // bottom right
    )
    val TRIANGLE_COLOR = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    //正交投影矩阵
    var mProjectionMatrix = FloatArray(16)

    //索引
    var aPosition : Int = 0;
    var uColor : Int = 0
    var uMatrix : Int = 0

    //在数组中，一个顶点需要3个来描述其位置，需要3个偏移量
    private val COORDS_PER_VERTEX = 3
    private val COORDS_PER_COLOR = 0
    private val VERTEX_COUNT = TRIANGLE_COORDS.size / COORDS_PER_VERTEX

    //在数组中，描述一个顶点，总共的顶点需要的偏移量。这里因为只有位置顶点，所以和上面的值一样
    private val TOTAL_COMPONENT_COUNT = COORDS_PER_VERTEX + COORDS_PER_COLOR

    //一个点需要的byte偏移量 一个float 4个字节
    private val STRIDE: Int = TOTAL_COMPONENT_COUNT * 4

    /**
     * 给三角形分配内存空间
     * 给定义的数组，分配对应的本地内存的空间。ByteBuffer .allocateDirect方法
     * 0.使用ByteBuffer 来创建内存区域
     * 1.ByteOrder.nativeOrder 来保证同一个平台使用相同的顺序
     * 2.通过put方法将内存复制进去
     * 3.因为从第一个点开始就表示三角形，所以将position移动到0
     */
    private var mVertexBuffer = ByteBuffer.allocateDirect(TRIANGLE_COORDS.size * 4).run {
        order(ByteOrder.nativeOrder())
        asFloatBuffer().apply {
            put(TRIANGLE_COORDS)
            // 将缓冲区的指针移动到头部，保证数据是从最开始处读取
            position(0)
        }

    }
    //0.从assert获取着色器代码
    var vertexShaderCode : String = GLUtil.readRawShaderCode(context, Config.VERTEX_SHADER_FILE)
    var fragmentShaderCode : String = GLUtil.readRawShaderCode(context, Config.FRAGMENT_SHADER_FILE)
    //1.将代码进行编译，得到shader的id
    var vertexShaderId : Int = GLUtil.compileShaderCode(GLES20.GL_VERTEX_SHADER,vertexShaderCode)
    var fragmentShaderId : Int = GLUtil.compileShaderCode(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode)
    //2.取得program
    var mProgramId =0

    init {
        //3.将vertexShader 和 fragmentShader 链接到program
        mProgramId = GLUtil.linkProgram(vertexShaderId,fragmentShaderId)
        //4.使用这个program
        GLES20.glUseProgram(mProgramId)
    }

    fun draw(){
        //获取glsl中的索引
        aPosition = GLES20.glGetAttribLocation(mProgramId, Config.A_POSITION).also {
            //1.开始启动我们的position
            GLES20.glEnableVertexAttribArray(it)
            //2.将坐标的数据放入
            GLES20.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX, //一个顶点需要几个偏移量
                GLES20.GL_FLOAT,false,
                STRIDE,//一个顶点需要几个字节的偏移量
                mVertexBuffer
            )
        }
        uColor = GLES20.glGetUniformLocation(mProgramId,Config.U_COLOR).also {
            GLES20.glUniform4fv(
                it,
                1,
                TRIANGLE_COLOR,
                0
            )
        }
        uMatrix = GLES20.glGetUniformLocation(mProgramId,Config.U_MATRIX)

        //一定要先显示上色，再绘制图形
        //否则会导致颜色在当前这一帧使用失败，要在下一帧才生效
        //开始绘制
        /**
         * mode：绘制模式，控制绘制点、线段、三角形以及其具体的连接方式
         * first：从顶点数据读取数据的起点位置(以点作为单位，而非向量)
         * count：绘制的顶点数(以点作为单位，而非向量)
         */
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,VERTEX_COUNT)

        GLES20.glDisableVertexAttribArray(aPosition)
    }

    fun change(width : Int,height : Int){
        //边长比
        var aspectRadio : Float = if (width>height) width.toFloat()/height.toFloat()
                                    else height.toFloat() /width.toFloat()
        if(width > height){
            //横屏
            Matrix.orthoM(mProjectionMatrix,0,-aspectRadio,aspectRadio,-1f,1f,-1f,1f)
        }else{
            Matrix.orthoM(mProjectionMatrix,0,-1f,1f,-aspectRadio,aspectRadio,-1f,1f)
        }
        GLES20.glUniformMatrix4fv(uMatrix,1,false,mProjectionMatrix,0)
    }



}