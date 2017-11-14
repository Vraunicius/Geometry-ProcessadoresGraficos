#version 330

uniform sampler2D uPosition;
uniform sampler2D uNormal;
uniform sampler2D uAlbedoSpec;

uniform vec3 uLightDir;

uniform vec3 uAmbientLight;
uniform vec3 uDiffuseLight;
uniform vec3 uSpecularLight;

uniform vec3 uCameraPosition;

in vec2 vTexCoord;

layout (location = 0) out vec4 outColor;

void main(void) 
{	
	vec3 L = normalize(uLightDir);    
	
	vec3 position = texture(uPosition, vTexCoord).rgb;	
    vec3 N = texture(uNormal, vTexCoord).rgb;
    vec3 albedo = texture(uAlbedoSpec, vTexCoord).rgb;
    float specularPower = texture(uAlbedoSpec, vTexCoord).a;
    
    float diffuseIntensity = max(dot(N, -L), 0.0);
    vec3 diffuse = diffuseIntensity * uDiffuseLight;
       
    //Calculo do componente especular
	float specularIntensity = 0.0;
	if (specularPower > 0.0) {
		vec3 V = normalize(uCameraPosition - position);
		vec3 R = reflect(L, N);
		specularIntensity = pow(max(dot(R, V), 0.0), specularPower);
	}
    vec3 specular = specularIntensity * uSpecularLight;
    
    outColor = vec4(clamp(albedo * (uAmbientLight + diffuse) + specular, 0.0, 1.0), 1.0);
}