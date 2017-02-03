#version 400 core

in vec3 position;
in float colorFragment;

out float ColorFragment;

void main(void){

	ColorFragment = colorFragment;
	gl_Position = vec4(position, 1.0);

}