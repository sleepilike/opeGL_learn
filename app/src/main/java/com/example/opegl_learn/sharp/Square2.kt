package com.example.opegl_learn.sharp

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

var squareCoords = floatArrayOf(
    -0.5f,  0.5f, 0.0f,      // top left
    -0.5f, -0.5f, 0.0f,      // bottom left
    0.5f, -0.5f, 0.0f,      // bottom right
    0.5f,  0.5f, 0.0f       // top right
)
class Square2 {

    //绘制顶点的顺序
    private val drawOrder = shortArrayOf(0,1,2,0,2,3);

    // initialize vertex byte buffer for shape coordinates
    private val vertexBuffer : FloatBuffer =
        ByteBuffer.allocateDirect(squareCoords.size * 4).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(squareCoords)
                position(0)
            }
        }

    private val drawListBuffer : ShortBuffer =
        ByteBuffer.allocateDirect(drawOrder.size * 2).run {
            order(ByteOrder.nativeOrder())
            asShortBuffer().apply {
                put(drawOrder)
                position(0)
            }
        }

}