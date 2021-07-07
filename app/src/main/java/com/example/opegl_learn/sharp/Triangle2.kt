package com.example.opegl_learn.sharp

import android.content.Context
import android.opengl.GLES20
import com.example.opegl_learn.utils.GLUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by zyy on 2021/7/7
 *
 * 第二次编写 三角形
 * https://www.jianshu.com/p/4a014afde409
 */

//着色器代码位置
const val VERTEX_SHADER_FILE = "assets/shader/base_vertex_shader.glsl"
const val FRAGMENT_SHADER_FILE = "assets/shader/base_fragment_shader"

class Triangle2 (context: Context){

    private var context : Context = context.applicationContext

    //顶点坐标的位置 OpenGL中的坐标系是从[-1，1]
    val TRIANGLE_COORDS = floatArrayOf(
        //x,y,z
        0.5f,0.5f,0.0f, //top
        -0.5f,-0.5f,0.0f, //bottom left
        0.5f,-0.5f,0.0f // bottom right
    )
    val TRIANGLE_COLOR = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    var aPosition : Int = 0;
    var uColor : Int = 0

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
            position(0)
        }

    }
    //0.从assert获取着色器代码
    var vertexShaderCode : String = GLUtil.readRawShaderCode(context, VERTEX_SHADER_FILE)
    var fragmentShaderCode : String = GLUtil.readRawShaderCode(context, FRAGMENT_SHADER_FILE)
    //1.将代码进行编译，得到shader的id
    var vertexShaderId : Int = GLUtil.compileShaderCode(GLES20.GL_VERTEX_SHADER,vertexShaderCode)
    var fragmentShaderId : Int = GLUtil.compileShaderCode(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode)
    //2.取得program
    var mProgramId : Int = GLES20.glCreateProgram()

    init {
        //将shader绑定到program中
        GLES20.glAttachShader(mProgramId,vertexShaderId)
        GLES20.glAttachShader(mProgramId,fragmentShaderId)
        //3.启动GL link program
        GLES20.glLinkProgram(mProgramId)
        //4.使用这个program
        GLES20.glUseProgram(mProgramId)
    }

    fun draw(){
        //0.取出定义的位置
        aPosition = GLES20.glGetAttribLocation(mProgramId,"aPosition").also {
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
        uColor = GLES20.glGetUniformLocation(mProgramId,"uColor").also {
            GLES20.glUniform4fv(
                it,
                1,
                TRIANGLE_COLOR,
                0
            )
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,VERTEX_COUNT)
        GLES20.glDisableVertexAttribArray(aPosition)
    }



}