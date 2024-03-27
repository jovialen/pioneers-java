#version 430 core

in vec2 pTextureCoordinate;
in vec4 pColor;

out vec4 oColor;

void main() {
    oColor = vec4(pTextureCoordinate.xy, 0, 1) * pColor;
}