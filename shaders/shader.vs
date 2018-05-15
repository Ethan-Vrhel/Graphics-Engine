#version 120

// Rendering type
uniform int type;

// Required camera variables
attribute vec3 vertices;
attribute vec2 textures;

varying vec2 tex_coords;

uniform mat4 projection;

void cameraTransformOrtho();
void cameraTransformPersp();

void main()
{
	if (type == 0)
		cameraTransformOrtho();
	else
		cameraTransformPersp();
}

void cameraTransformOrtho()
{
	tex_coords = textures;
	gl_Position = projection  * vec4(vertices, 1);
}

void cameraTransformPersp()
{

}