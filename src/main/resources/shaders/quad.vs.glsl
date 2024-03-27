#version 430 core

layout(location = 0) in vec3 iPosition;
layout(location = 1) in vec2 iTextureCoordinate;
layout(location = 2) in vec4 iColor;

out vec2 pTextureCoordinate;
out vec4 pColor;

void main() {
    gl_Position = vec4(iPosition, 1);
    pTextureCoordinate = iTextureCoordinate;
    pColor = iColor;
}