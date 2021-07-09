package com.example.opegl_learn.render

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import com.example.opegl_learn.R
import com.example.opegl_learn.config.Config
import com.example.opegl_learn.utils.GLUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by zyy on 2021/7/9
 *
 */
class TextureRender(context: Context) : GLSurfaceView.Renderer {

    var context : Context = context
    var mTexName : Int = 0

    //截取纹理的区域
    private val TEX_VERTEX = floatArrayOf(
        1.0f,0.0f,
        0.0f,0.0f,
        0.0f,1.0f,
        1.0f,1.0f
    )
    var mVTexVertexBuffer  = ByteBuffer.allocateDirect(TEX_VERTEX.size * 4).run {
        order(ByteOrder.nativeOrder())
        asFloatBuffer().apply {
            put(TEX_VERTEX)
            position(0)
        }

    }

    var vertexShaderCode : String = GLUtil.readRawShaderCode(context, Config.VERTEX_SHADER_FILE)
    var fragmentShaderCode : String = GLUtil.readRawShaderCode(context, Config.FRAGMENT_SHADER_FILE)
    //1.将代码进行编译，得到shader的id
    var vertexShaderId : Int = GLUtil.compileShaderCode(GLES20.GL_VERTEX_SHADER,vertexShaderCode)
    var fragmentShaderId : Int = GLUtil.compileShaderCode(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode)
    //2.取得program
    var mProgramId =0

    //索引
    var vPosition : Int = 0
    var aTexCoord : Int = 0
    var uMVPMatrix : Int = 0
    var sTexture : Int = 0

    init {

        vPosition = GLES20.glGetAttribLocation(mProgramId,Config.V_POSITION)
        aTexCoord = GLES20.glGetAttribLocation(mProgramId,Config.A_TEXCOORD)
        uMVPMatrix = GLES20.glGetUniformLocation(mProgramId,Config.U_MVPMATRIX)
        sTexture = GLES20.glGetUniformLocation(mProgramId,Config.S_TEXTURE)
    }
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

        loadPicture()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {

    }

    override fun onDrawFrame(gl: GL10?) {

    }

    /**
     * 加载图片并保存至OpenGL纹理系统
     */
    fun loadPicture(){

        var texNames = IntArray(1)
        //0.创建纹理
        GLES20.glGenTextures(1,texNames,0)
        mTexName = texNames[0]


        //1.激活指定编号的纹理
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        //2.将新建的纹理和编号绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,mTexName)
        //3.设置纹理过滤参数 :解决纹理缩放过程中的锯齿问题。若不设置，则会导致纹理为黑色
        // 进行裁剪策略、缩放策略等
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_LINEAR);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
            GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
            GLES20.GL_REPEAT);

        //4.将图片数据拷贝到纹理中
        var bitmap : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.p_300px)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0)
    }
}