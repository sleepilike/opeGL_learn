package com.example.opegl_learn.config

/**
 * Created by zyy on 2021/7/8
 *
 */
class Config {
    companion object{
        const val VERTEX_SHADER_FILE = "shader/base_vertex_shader.glsl"
        const val FRAGMENT_SHADER_FILE = "shader/base_fragment_shader.glsl"

        const val A_POSITION = "aPosition"
        const val U_COLOR = "uColor"
        const val U_MATRIX = "uMatrix"


        const val TEXTURE_VERTEX_SHADER = "shader/texture_vertex_shader.glsl"
        const val TEXTURE_FRAGMENT_SHADER = "shader/texture_fragment_shader.glsl"

        const val V_POSITION = "vPosition"
        const val A_TEXCOORD = "a_texCoord"
        const val U_MVPMATRIX = "uMVPMatrix"
        const val S_TEXTURE = "s_texture"

    }
}