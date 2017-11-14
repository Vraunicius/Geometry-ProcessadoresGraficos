
#version 330
layout (location = 0) out vec3 outPosition;
layout (location = 1) out vec3 outNormal;
layout (location = 2) out vec4 outAlbedoSpec;

uniform float uSpecularPower;

uniform sampler2D uTex0;
uniform sampler2D uTex1;
uniform sampler2D uTex2;
uniform sampler2D uTex3;

in vec3 vPosition;
in vec3 vNormal;
in vec2 vTexCoord;
in vec4 vTexWeight;
in float vDepth;

void main() {
   	//Multi textura
   	vec2 nearCoord = vec2(vTexCoord.s, vTexCoord.t) * 50.0;   	
    vec4 texelNear = texture(uTex0, nearCoord) * vTexWeight.w + 
                 texture(uTex1, nearCoord) * vTexWeight.z +
                 texture(uTex2, nearCoord) * vTexWeight.y +
                 texture(uTex3, nearCoord) * vTexWeight.x;

    vec2 farCoord = vec2(vTexCoord.s, vTexCoord.t) * 10.0;   
	vec4 texelFar = texture(uTex0, farCoord) * vTexWeight.w + 
	                texture(uTex1, farCoord) * vTexWeight.z +
	                texture(uTex2, farCoord) * vTexWeight.y +
	                texture(uTex3, farCoord) * vTexWeight.x;

    //Interpolação da textura
    float blendDistance = 0.99;
    float blendWidth = 100.0;
    float blendFactor = clamp((vDepth - blendDistance) * blendWidth, 0.0, 1.0);
	vec3 texel = mix(texelNear, texelFar, blendFactor).rgb;
	
   outPosition = vPosition;
   outNormal = normalize(vNormal);;
   outAlbedoSpec = vec4(texel, uSpecularPower);
}