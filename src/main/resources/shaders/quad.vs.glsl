#version 430 core

layout(location = 0) in vec3 iPosition;
layout(location = 1) in vec2 iUv;

out vec2 pUv;

void main() {
    gl_Position = vec4(iPosition, 1.0);
    pUv = iUv;
}