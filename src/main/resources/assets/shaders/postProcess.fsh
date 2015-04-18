#version 330

in vec2 texCoords0;
in vec4 baseColor;

uniform float time;
uniform vec2 screenSize;
uniform sampler2D diffuse;

out vec4 color;

float rand(vec2 seed) {
    float dotResult = dot(seed.xy, vec2(12.9898,78.233));
    float sin = sin(dotResult) * 43758.5453;
    return fract(sin);
}

void main() {
    vec4 sample = texture2D(diffuse, texCoords0);
    vec4 lineColor = vec4(1.0/3.0,0,1.0/3.0,1);

    float minFactor = 0.05;
    float maxFactor = 0.20;

    float totalLineNumber = screenSize.y/1.5;

    float y = int(texCoords0.y * totalLineNumber)/totalLineNumber;
    float factor = rand(vec2(0,y)*(time)) * (maxFactor-minFactor) + minFactor;
    vec4 finalColor = mix(sample, lineColor, factor);
    color = finalColor;
}

