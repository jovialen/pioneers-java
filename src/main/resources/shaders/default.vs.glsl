#version 430 core

layout(location = 0) in vec3 iPosition;
layout(location = 1) in vec2 iTextureCoordinate;
layout(location = 2) in vec4 iColor;

out vec2 pTextureCoordinate;
out vec4 pColor;

uniform mat4 uProjection;
uniform mat4 uView;
uniform mat4 uModel;

void main() {
    gl_Position = uProjection * uView * uModel * vec4(iPosition, 1);
    pTextureCoordinate = iTextureCoordinate;
    pColor = iColor;
}