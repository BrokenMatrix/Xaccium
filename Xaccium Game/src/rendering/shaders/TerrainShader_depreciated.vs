#version 400 core

in vec3 position;

out vec3 wNormal;
out vec3 toLightVector;
out vec3 wPosition;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

//uniform vec3 lightPosition;

void main(void){

	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	//gl_Position = projectionMatrix * viewMatrix * worldPosition;
	gl_Position = projectionMatrix*viewMatrix*transformationMatrix*vec4(position, 1.0);
	wNormal = (transformationMatrix * vec4(position,0.0)).xyz;
	wPosition = worldPosition.xyz;
	toLightVector = vec3(1000, 3000, 2000) - worldPosition.xyz;

}