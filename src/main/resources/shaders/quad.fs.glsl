#version 430 core

in vec2 pUv;

out vec4 oColor;

uniform sampler2D uTexture;

void main() {
    oColor = texture(uTexture, pUv);
}