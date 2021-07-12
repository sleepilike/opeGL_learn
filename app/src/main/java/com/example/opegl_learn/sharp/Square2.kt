package com.example.opegl_learn.sharp

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import com.example.opegl_learn.config.Config
import com.example.opegl_learn.utils.GLUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * Created by zyy on 2021/7/1
 * 方形
 * 两个三角形叠加
 * https://developer.android.com/training/graphics/opengl/shapes
 */
// number of coordinates per vertex in this array


class Square2 (context :Context){

    //顶点坐标
    private val TRIANGLE_COORDS = floatArrayOf(
        //x,y,z
        1f, 1f, 0f,   // top right
        -1f, 1f, 0f,  // top left
        -1f, -1f, 0f, // bottom left
        1f, -1f, 0f,  // bottom right
    )

    //顶点绘制顺序
    private val VERTEX_INDEX = shortArrayOf(
        0,1,2,0,2,3
    )

    val TRIANGLE_COLOR = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    private val COORDS_PER_VERTEX = 3
    private val COORDS_PER_COLOR = 0
    private val VERTEX_COUNT = TRIANGLE_COORDS.size / COORDS_PER_VERTEX

    private val TOTAL_COMPONENT_COUNT = COORDS_PER_COLOR+COORDS_PER_VERTEX

    private val STRIDE = TOTAL_COMPONENT_COUNT * 4

    var mProjectionMatrix = FloatArray(16)

    //索引
    var aPosition : Int = 0;
    var uColor : Int = 0
    var uMatrix : Int = 0  //投影矩阵

    private var mVertexBuffer = ByteBuffer.allocateDirect(TRIANGLE_COORDS.size * 4).run {
        order(ByteOrder.nativeOrder())
        asFloatBuffer().apply {
            put(TRIANGLE_COORDS)
            // 将缓冲区的指针移动到头部，保证数据是从最开始处读取
            position(0)
        }
    }

    private var mVertexIndexBuffer = ByteBuffer.allocateDirect(VERTEX_INDEX.size * 2).run {
        order(ByteOrder.nativeOrder())
        asShortBuffer().apply {
            put(VERTEX_INDEX)
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
        uMatrix = GLES20.glGetUniformLocation(mProgramId,Config.U_MATRIX)
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


       //采用glDrawElements 来绘制，mVertexBuffer 指定了顶点的绘制顺序
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,VERTEX_INDEX.size,
        GLES20.GL_UNSIGNED_SHORT,mVertexIndexBuffer)
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