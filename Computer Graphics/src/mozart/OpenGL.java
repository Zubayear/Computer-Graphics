/*
 * @Jubaer
 * 
 * */
package mozart;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

public class OpenGL {

	public static void main(String[] args) {

		// getting the capabilities object of GL2 profile
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		// The canvas
		final GLCanvas glcanvas = new GLCanvas(capabilities);
		Cohen drawing = new Cohen();
		glcanvas.addGLEventListener(drawing);
		glcanvas.setSize(700, 600);

		// creating frame
		final JFrame frame = new JFrame("straight Line");

		// adding canvas to frame
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);

	}

}
