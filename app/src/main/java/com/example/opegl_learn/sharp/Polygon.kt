package com.example.opegl_learn.sharp

import android.content.Context
import android.opengl.GLES20
import com.example.opegl_learn.config.Config
import com.example.opegl_learn.utils.GLUtil
import java.nio.FloatBuffer

/**
 * Created by zyy on 2021/7/8
 * 多边形
 */
class Polygon(context: Context) {

    private val POSITION_COMPONENT_COUNT = 2;
    //多边形顶点与中心点的距离
    private val RADIUS = 0.5f
    //起始点的弧度

    //索引
    private var uColor : Int = 0
    private var aPosition : Int = 0

    //顶点坐标
    private var mVertexData : FloatBuffer? = null

    //顶点数
    private var mPolygonVertexCount = 3

    //绘制所需要的顶点数
    private lateinit var mPointData : FloatArray
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
        //获取glsl中的索引
        aPosition = GLES20.glGetAttribLocation(mProgramId, Config.A_POSITION).also {
            //开始启动我们的position
            GLES20.glEnableVertexAttribArray(it)
        }
        uColor = GLES20.glGetUniformLocation(mProgramId,Config.U_COLOR)

        GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f)
    }

    //fun update
}