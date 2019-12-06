/*
 * @Jubaer
 * 
 * */
package mozart;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Cohen implements GLEventListener {

	private GLU glu;

	@Override
	public void init(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();
		glu = new GLU();

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glViewport(-450, -450, 450, 450);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluOrtho2D(-450.0, 450.0, -450.0, 450.0);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

		// Box
		DDA(gl, -100, -50, -100, 100);
		DDA(gl, -100, 100, 100, 100);
		DDA(gl, 100, 100, 100, -50);
		DDA(gl, 100, -50, -100, -50);

		// Lines
		DDA(gl, -50, 0, 0, 50); // Completely Inside
		DDA(gl, -200, 0, -150, 50); // Completely Outside
		DDA(gl, -150, 0, 0, 150); // Partially
		DDA(gl, -50, -100, 150, 50); // Partially

//		int res1 = computeOUTCODE(-150, 0);
//		int res2 = computeOUTCODE(0, 150);
//		int res = computeOUTCODE(-100, 50);
//		
//		System.out.print(res1 + ", " + res2);
//		System.out.println(res);
//		clipLINE(gl, 50, 200, 200, 350);
//		clipLINE(gl, -150, 0, 0, 150); // Partially
//		cohensutherland(gl, -150, 0, 0, 150);
		/*
		 * put your code here
		 */
		// points should be in the same zone
//		DrawMPL(gl, 10, 10, 60, 50);
//		DrawMPL(gl, 10, -10, 60, -50);
//		DrawMPL(gl, -30, -10, -100, -40);
//		DrawMPL(gl, -10, 25, -30, 125);
//		DrawMPL(gl, 0, 0, 30);

		// Clipping
		cohensutherland(gl, -50, 0, 0, 50); // Completely Inside
		cohensutherland(gl, -200, 0, -150, 50); // Completely Outside
		cohensutherland(gl, -150, 0, 0, 150); // Partially
		cohensutherland(gl, -50, -100, 150, 50); // Partially
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// do nothing
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// do nothing
	}

	private void DDA(GL2 gl, double x1, double y1, double x2, double y2) {
		// write your own code
		gl.glPointSize(1.0f);
		gl.glColor3d(0, 0, 1);
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

		double dx = x2 - x1;
		double dy = y2 - y1;
		double steps = 0;
		double xInc, yInc;

		if (Math.abs(dx) > Math.abs(dy)) {
			steps = Math.abs(dx);
		} else {
			steps = Math.abs(dy);
		}

		xInc = dx / (double) steps;
		yInc = dy / (double) steps;
		double x = x1;
		double y = y1;
		for (int i = 0; i <= steps; i++) {
			gl.glVertex2d(Math.round(x), Math.round(y));
			x += xInc;
			y += yInc;
		}
		gl.glEnd();
	}

	private int computeOUTCODE(double x, double y) {
		int outcode = 0;

		if (y > ymax) {
			outcode |= TOP;
		} else if (y < ymin) {
			outcode |= BOTTOM;
		} else if (x > xmax) {
			outcode |= RIGHT;
		} else if (x < xmin) {
			outcode |= LEFT;
		}
		return outcode;
	}

	int dx, dy, b;

	int xmin = -100;
	int xmax = 100;
	int ymin = -50;
	int ymax = 100;

	final int LEFT = 1;
	final int RIGHT = 2;
	final int BOTTOM = 4;
	final int TOP = 8;

	private void cohensutherland(GL2 gl, double x1, double y1, double x2, double y2) {

		double x = 0, y = 0;
		int code;
		int code1 = computeOUTCODE(x1, y1);
		int code2 = computeOUTCODE(x2, y2);
//		System.out.print(code1 + "---" + code2);
		boolean accept = false;
		while (true) {
			if ((code1 == 0) && (code2 == 0)) { // Totally inside
				accept = true;

				break;
			} else if ((code1 & code2) != 0) { // Completely Outside
				accept = false;
				break;
			} else {
				if (code1 != 0) {
					code = code1;
				} else {
					code = code2;
				}
				if ((code & TOP) == TOP) {
					y = ymax;
					x = (x1 + (ymax - y1) * (x2 - x1) / (y2 - y1));
				} else if ((code & BOTTOM) == BOTTOM) {
					y = ymin;
					x = (x1 + (ymin - y1) * (x2 - x1) / (y2 - y1));
				} else if ((code & RIGHT) == RIGHT) {
					x = xmax;
					y = (y1 + (xmax - x1) * (y2 - y1) / (x2 - x1));
				} else if ((code & LEFT) == LEFT) {
					x = xmin;
					y = (y1 + (xmin - x1) * (y2 - y1) / (x2 - x1));
				}
				if (code == code1) {
					x1 = x;
					y1 = y;
					code1 = computeOUTCODE(x1, y1);
				} else {
					x2 = x;
					y2 = y;
					code2 = computeOUTCODE(x2, y2);
				}
			}
		}
		if (accept == true) {
			DDA(gl, 100, 150, 100, 300);
			DDA(gl, 100, 300, 300, 300);
			DDA(gl, 300, 300, 300, 150);
			DDA(gl, 300, 150, 100, 150);
			DDA(gl, x1 + 200, y1 + 200, x2 + 200, y2 + 200);
		}
	}

	public void dispose(GLAutoDrawable arg0) {
		// do nothing
	}
}
