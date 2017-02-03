#version 150

layout ( triangles ) in;
layout ( triangle_strip, max_vertices = 3) out;

in float ColorFragment[];

out vec3 diffuse;

uniform vec3 lightColor;
uniform mat4 transformation;
uniform mat4 projection;
uniform mat4 view;
uniform vec3 lightPosition;

void main(void){

	vec3 tangent = gl_in[1].gl_Position.xyz - gl_in[0].gl_Position.xyz;
	vec3 bitangent = gl_in[2].gl_Position.xyz - gl_in[0].gl_Position.xyz;
	vec3 normal = normalize(cross(tangent, bitangent));
	float brightness = max(dot(-lightPosition, normal), 0.3);
	vec3 color = lightColor * brightness * vec3(ColorFragment[0], ColorFragment[1], ColorFragment[2]);
	
	mat4 PVT = projection * view * transformation;
	
	gl_Position = PVT * gl_in[0].gl_Position;
	diffuse = color;
	EmitVertex();
	
	gl_Position = PVT * gl_in[1].gl_Position;
	diffuse = color;
	EmitVertex();
	
	gl_Position = PVT * gl_in[2].gl_Position;
	diffuse = color;
	EmitVertex();
	
	EndPrimitive();
	
}
