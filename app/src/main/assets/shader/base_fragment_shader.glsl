
//设置片元着色器的精度。这里值要兼容性能和效率。通常都是选择mediump
//精度有lowp、mediump、highp三种，但只有部分硬件支持片段着色器使用highp。(顶点着色器默认highp)
precision mediump float;
//定义个常量 uColor
uniform vec4 uColor;
void main() {

    gl_FragColor = uColor;
}
