package com.example.opegl_learn.utils

import android.opengl.GLES20

/**
 * Created by zyy on 2021/7/9
 *
 */
class FBOUtil {
    companion object{
        /**
         * 创建fbo纹理
         * 与建普通纹理类似
         * 这里使用的是 GLES20.glTexImage2D ，在渲染图片纹理的时候，使用的是 GLUtils.texImage2D
         */
        fun createFBPTexture(width:Int,height:Int) : IntArray{
            var texName  = IntArray(1)
            GLES20.glGenTextures(1,texName,0)

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texName[0])

            // 根据颜色参数，宽高等信息，为上面的纹理ID，生成一个2D纹理
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height,
                0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null)
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST.toFloat())
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE.toFloat())
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE.toFloat())

            // 解绑纹理ID
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0)

            return texName

        }

        /**
         * 新建frameBuffer
         */
        fun createFrameBuffer() : Int{
            var fbs = IntArray(1)
            GLES20.glGenFramebuffers(1,fbs,0)
            return fbs[0]
        }

        /**
         * 绑定FBO
         */
        fun bindFBO(fb:Int,textureId:Int){
            //绑定FBO
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,fb)
            //将FBO和上面创建的纹理通过颜色附着点 GLES20.GL_COLOR_ATTACHMENT0 绑定起来
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, textureId, 0)
        }

        /**
         * 解绑FBO
         * 解绑FBO比较简单，其实就是将FBO绑定到默认的窗口上。
         *  GLES20.GL_NONE 其实就是 0 ，也就是系统默认的窗口的 FBO 。
         */
        fun unbindFBO() {
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_NONE)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        }


        /**
         * 删除FBO
         */
        fun deleteFBO(frame: IntArray, texture:IntArray) {
            //删除Frame Buffer
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_NONE)
            GLES20.glDeleteFramebuffers(1, frame, 0)
            //删除纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
            GLES20.glDeleteTextures(1, texture, 0)
        }
    }
}