#version 430 core

in vec3 pColor;

out vec4 oColor;

void main() {
    oColor = vec4(pColor, 1.0);
}