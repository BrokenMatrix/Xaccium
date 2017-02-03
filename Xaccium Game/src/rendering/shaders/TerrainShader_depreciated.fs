#version 400 core

in vec3 wNormal;
in vec3 toLightVector;
in vec3 wPosition;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(void){

	vec3 X = dFdx(wNormal);
	vec3 Y = dFdy(wNormal);
	vec3 normal = cross(X, Y);
	vec3 unitNormal = normalize(normal);
	vec3 unitToLight = normalize(toLightVector);
	float nDot = dot(unitNormal, unitToLight);
	float brightness = max(nDot, 0.2);
	vec3 diffuse = brightness * vec3(0.2, 0.2, 0.2);
	
	vec3 blending = abs(normal);
	blending = normalize(max(blending, 0.00001));
	float b = (blending.x + blending.y + blending.z);
	blending /= vec3(b, b, b);
	float scale = 0.1;
	vec4 xAxis = texture(textureSampler, wPosition.yz * scale);
	vec4 yAxis = texture(textureSampler, wPosition.xz * scale);
	vec4 zAxis = texture(textureSampler, wPosition.xy * scale);
	vec4 tex = xAxis * blending.x + yAxis * blending.y + zAxis * blending.z;
	
	out_Color = vec4(tex.xyz + diffuse, 1);
	out_Color = vec4(1,0,0,1);

}