#version 330

in vec2 aPosition;
in vec3 aColor;

out VS_OUT {
	vec3 color;
} vs_out;

void main()
{
    gl_Position = vec4(aPosition, 0.0f, 1.0f);
    vs_out.color = aColor; 
}