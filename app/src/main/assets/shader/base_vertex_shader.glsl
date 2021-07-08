//顶点着色器

//定义一个attribute aPosition，类型为vec4。4个分量的向量
attribute vec4 aPosition;
//mat4 4x4矩阵 投影矩阵
uniform mat4 uMatrix;
void main() {

    //这里的gl_Position是OpenGL内置的变量。
    //左乘 得到正确位置
    gl_Position = uMatrix * aPosition;
}
