package com.example.opegl_learn.utils

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import javax.microedition.khronos.opengles.GL

/**
 * Created by zyy on 2021/7/7
 *
 * OpenGL 工具类
 * 编辑着色器
 */
class GLUtil {


    companion object{
        /**
         * 编译着色器
         * 大致流程如下：
         * 0.根据着色器的类型，创建一个shaderObjectId=>
         * 1.使用GLES20将我们的代码和ID进行绑定=>
         * 2.编译我们绑定的代码=>
         * 3.1查询编译的状态。如果失败的话，就需要释放资源。delete=>
         * 3.2成功返回我们绑定好编译后代码的shaderObjectId
         */
        fun compileShaderCode(type:Int,shaderCode:String) : Int{

            Log.d("TAG", "compileShaderCode: $shaderCode")
            //得到一个着色器的id 对id进行操作 it
            GLES20.glCreateShader(type).also { shader ->
                GLES20.glShaderSource(shader,shaderCode)
                GLES20.glCompileShader(shader)
            }
            GLES20.glCreateShader(type).also {
                if(it != 0){
                    //0.上传代码
                    GLES20.glShaderSource(it,shaderCode)
                    //1.编译代码 绑定的代码和对应的id进行编译
                    GLES20.glCompileShader(it)

                    //2.查询编译的状态 失败？
                    var status = IntArray(1)
                    //调用getShaderIv ，传入GL_COMPILE_STATUS进行查询
                    GLES20.glGetShaderiv(it,GLES20.GL_COMPILE_STATUS,status,0)
                    //Log.d("TAG", "compileShaderCode: "+status[0]);
                    //等于0 则表示失败
                    if(status[0] == 0){
                        //释放资源 即删除这个引用
                        GLES20.glDeleteShader(it)
                        Log.d("TAG", "compileShaderCode: compile failed")
                        return 0
                    }
                }
                //返回编译器的id
                return it
            }

        }

        /**
         * 从assert 中读取ShaderCode
         */
        fun readRawShaderCode(context:Context,shaderCodeName:String) : String{
            var body = StringBuilder()
            try {

                context.assets.open(shaderCodeName).also { inputStream ->
                    BufferedReader(InputStreamReader(inputStream)).also {
                        var line = it.readLine()
                        while (line != null){
                            body.append(line)
                            body.append("\n")
                            line = it.readLine()
                        }
                    }
                }
            }catch (e : IOException){
                e.printStackTrace()
            }
            return body.toString()
        }

        fun linkProgram(vertexShaderId : Int,fragmentShaderId : Int) :Int{

            val programId = GLES20.glCreateProgram()

            GLES20.glAttachShader(programId,vertexShaderId)
            GLES20.glAttachShader(programId,fragmentShaderId)

            GLES20.glLinkProgram(programId)

            var status = IntArray(1)
            GLES20.glGetProgramiv(programId,GLES20.GL_LINK_STATUS,status,0)
              if(status[0] == 0){
                  GLES20.glDeleteShader(programId)
                  Log.d("TAG", "linkProgram: linkFailed")
                  return 0
              }

            return programId
        }

    }

}