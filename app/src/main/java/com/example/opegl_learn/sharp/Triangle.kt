package com.example.opegl_learn.sharp

import android.opengl.GLES20
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created by zyy on 2021/7/1
 *  三角形类
 *  官方文档 https://developer.android.com/training/graphics/opengl/shapes
 *  方法说明 https://www.cnblogs.com/msnow/p/5220381.html
 */
// number of coordinates per vertex in this array
const val COORDS_PER_VERTEX = 3


class Triangle {

    // Set color with red, green, blue and alpha (opacity) values
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    //为坐标定义浮点数的顶点数组
    // 为了最大限度地提高工作效率，可以将这些坐标写入 ByteBuffer 中，它会传递到 OpenGL ES 图形管道进行处理。
    var triangleCoords = floatArrayOf(     // in counterclockwise order:
        0.0f, 0.622008459f, 0.0f,      // top x,y,z
        -0.5f, -0.311004243f, 0.0f,    // bottom left
        0.5f, -0.311004243f, 0.0f      // bottom right
    )
    //着色程序包含 OpenGL 着色语言 (GLSL) 代码，必须先对其进行编译，然后才能在 OpenGL ES 环境中使用
    //顶点着色程序
    private val vertexShaderCode =
        "uniform mat4 uMVPMatrix;" +
        "attribute vec4 vPosition;" +
        "void main(){" +
                // uMVPMatrix需要作为第一个因子
        "   gl_Position = uMVPMatrix * vPosition;" +
        "}"
    //用于访问和设置视图转换
    private var vPMatrixHandle : Int =0
    //片段着色程序
    private val fragmentShaderCode =
        "precision mediump float;"+
         "uniform vec4 vColor;" +
         "void main() {" +
         "  gl_FragColor = vColor;" +
         "}"
    private var vertexBuffer: FloatBuffer =
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
            // use the device hardware's native byte order
            order(ByteOrder.nativeOrder())

            // create a floating point buffer from the ByteBuffer
            asFloatBuffer().apply {
                // add the coordinates to the FloatBuffer
                put(triangleCoords)
                // set the buffer to read the first coordinate
                position(0)
            }
        }
    private var mProgram: Int

    init {

        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)


        // create empty OpenGL ES Program
        //创建程序对象 返回着色程序的id
        mProgram = GLES20.glCreateProgram().also {

            // add the vertex shader to program
            GLES20.glAttachShader(it, vertexShader)
            // add the fragment shader to program
            GLES20.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            //链接程序
            GLES20.glLinkProgram(it)
        }
    }

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    //顶点个数
    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX
    //每个顶点的字节数
    private val vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    /**
     * 比例不适配
     */
    fun draw() {
        // Add program to OpenGL ES environment
        //　如果将program设置为0，表示使用固定功能管线。
        //使用链接好的程序
        GLES20.glUseProgram(mProgram)

        // get handle to vertex shader's vPosition member
        //获取着色器程序中，指定为attribute类型变量的id
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {

            //允许使用顶点坐标数组
            GLES20.glEnableVertexAttribArray(it)

            // 顶点位置数据传入着色器

            GLES20.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )

            // get handle to fragment shader's vColor member
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

                // Set color for drawing the triangle
                //为当前程序对象指定Uniform变量的值
                /**
                 * location :指明要更改的uniform变量的位置
                 * count：指明要更改的矩阵的个数
                 * v:使用的新值
                 * offset :
                 */
                GLES20.glUniform4fv(colorHandle, 1, color, 0)
            }

            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

            // Disable vertex array
            //禁用index指定的通用顶点属性数组
            // 默认情况下，禁用所有客户端功能，包括所有通用顶点属性数组。
            // 如果启用，将访问通用顶点属性数组中的值，并在调用顶点数组命令（如glDrawArrays或glDrawElements）时用于呈现。
            GLES20.glDisableVertexAttribArray(it)
        }

    }

    /**
     * 比例适配
     * 传入变换矩阵
     */
    fun draw(mvpMatrix: FloatArray) { // pass in the calculated transformation matrix

        // get handle to shape's transformation matrix
        //获取着色器程序中，指定为attribute类型变量的id
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
        draw()
    }

    //编译着色程序
    private fun loadShader(type : Int, shaderCode : String) : Int{
        //创建shader容器
        //返回shader容器的id
        return GLES20.glCreateShader(type).also { shader ->
            GLES20.glShaderSource(shader,shaderCode)
            GLES20.glCompileShader(shader)

            var status = IntArray(1)
            //调用getShaderIv ，传入GL_COMPILE_STATUS进行查询
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0)
            Log.d("TAG", "compileShaderCode: " + status[0])
        }
    }
}