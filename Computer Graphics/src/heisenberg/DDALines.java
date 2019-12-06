/*
 * @Jubaer
 * 
 * */
package heisenberg;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class DDALines implements GLEventListener {

	private GLU glu;

	@Override
	public void init(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();
		glu = new GLU();

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glViewport(-250, -150, 250, 150);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(-250.0, 250.0, -150.0, 150.0);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

		DDA(gl, -10, 25, -30, 125);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// do nothing
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// do nothing
	}

	private void DDA(GL2 gl, int x1, int y1, int x2, int y2) {
		// write your own code
		gl.glPointSize(1.0f);
		gl.glColor3d(1, 0, 0);
		gl.glBegin(GL2.GL_POINTS);

		// DDA Algorithm
		/*
		 * // calculate dx , dy dx = X1 - X0; dy = Y1 - Y0;
		 * 
		 * // Depending upon absolute value of dx & dy // choose number of steps to put
		 * pixel as // steps = abs(dx) > abs(dy) ? abs(dx) : abs(dy) steps = abs(dx) >
		 * abs(dy) ? abs(dx) : abs(dy);
		 * 
		 * // calculate increment in x & y for each steps Xinc = dx / (float) steps;
		 * Yinc = dy / (float) steps;
		 * 
		 * // Put pixel for each step X = X0; Y = Y0; for (int i = 0; i <= steps; i++) {
		 * putpixel (X,Y,WHITE); X += Xinc; Y += Yinc; }
		 */

		int dx = x2 - x1;
		int dy = y2 - y1;
		int steps = 0;
		double xInc, yInc;

		if (Math.abs(dx) > Math.abs(dy)) {
			steps = Math.abs(dx);
		} else {
			steps = Math.abs(dy);
		}

		xInc = dx / (double) steps;
		yInc = dy / (double) steps;
		int x = x1;
		int y = y1;
		for (int i = 0; i <= steps; i++) {
			gl.glVertex2d(Math.round(x), Math.round(y));
			x += xInc;
			y += yInc;
		}
		gl.glEnd();
	}

	public void dispose(GLAutoDrawable arg0) {
		// do nothing
	}
}