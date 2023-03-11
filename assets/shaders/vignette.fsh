#ifdef GL_ES
#define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_resolution;
uniform sampler2D u_sampler2D;
uniform vec2 u_playerPos;


void main() {
	vec4 color = texture2D(u_sampler2D, v_texCoord0)*v_color;
	vec2 relativePositionPlayer = u_playerPos.xy/u_resolution.xy;
	vec2 relativePosition = gl_FragCoord.xy/u_resolution.xy - relativePositionPlayer.xy;
	relativePosition.x *= u_resolution.x/u_resolution.y;
	float len = length(relativePosition);
	
	float vignette = smoothstep(0.2,-0.2,len);
	color.rgb = mix(color.rgb, color.rgb*vignette, .95);

    gl_FragColor = color;

}
