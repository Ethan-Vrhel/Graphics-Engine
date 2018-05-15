#version 120

// Rendering type
uniform int type;

// Required camera variables
uniform sampler2D sampler;
varying vec2 tex_coords;

void applyTexOrtho();
void applyTexPersp();

void main() 
{
	//gl_FragColor = texture2D(sampler, tex_coords);
	if (type == 0)
		applyTexOrtho();
	else
		applyTexPersp();
}

void applyTexOrtho() 
{
	gl_FragColor = texture2D(sampler, tex_coords);
}

void applyTexPersp() 
{

}