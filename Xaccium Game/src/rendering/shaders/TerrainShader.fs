#version 400 core

in vec3 diffuse;

out vec4 out_Color;

void main(void){

	out_Color = vec4(diffuse, 1.0);

}