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


class Square2 {

    private val SQUARE_COLOR_COORDS = floatArrayOf( //Order of coordinates: X, Y, Z, R,G,B,
    -0.5f, 0.5f, 0.0f, 1f, 0f, 0f,  //  0.top left RED
    -0.5f, -0.5f, 0.0f, 0f, 0f, 1f,  //  1.bottom right Blue
    0.5f, 0.5f, 0.0f, 1f, 1f, 1f,  //  3.top right WHITE
    0.5f, -0.5f, 0.0f, 0f, 1f, 0f)

    private val COORDS_PER_VERTEX = 3
    private val COORDS_PER_COLOR = 3

    private val TOTAL_COMPONENT_COUNT = COORDS_PER_COLOR+COORDS_PER_VERTEX
    private val STRIDE = TOTAL_COMPONENT_COUNT * 4


}