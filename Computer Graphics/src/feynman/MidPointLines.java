/*
 * @Jubaer
 * 
 * */
package feynman;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class MidPointLines implements GLEventListener {

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
		/*
		 * put your code here
		 */
		// points should be in the same zone
//		DrawMPL(gl, 10, 10, 60, 50);
//		DrawMPL(gl, 10, -10, 60, -50);
//		DrawMPL(gl, -30, -10, -100, -40);
//		DrawMPL(gl, -10, 25, -30, 125);
		DrawMPL(gl, 0, 0, 200, 200);
		DrawMPL(gl, 200, 200, 400, 4);
		DrawMPL(gl, 400, 4, 60, 120);
		DrawMPL(gl, 60, 120, 320, 120);
		DrawMPL(gl, 320, 120, 0, 0);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// do nothing
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// do nothing
	}

	int dx, dy, b;

	private void DrawMPL(GL2 gl, int x1, int y1, int x2, int y2) {
		// write your own code
		gl.glPointSize(1.0f);
		gl.glColor3d(1, 0, 0);

		gl.glBegin(GL2.GL_POINTS);
		int zone = findZone(x1, y1, x2, y2);
		int x11 = convertX(x1, y1, zone);
		int y11 = convertY(x1, y1, zone);
		int x22 = convertX(x2, y2, zone);
		int y22 = convertY(x2, y2, zone);
//
//		System.out.print(x11);
//		System.out.print(y11);
//		System.out.print(x22);
//		System.out.print(y22);
//		System.out.println();
		
		int dx = x22 - x11;
		int dy = y22 - y11;
		int dltE = 2 * dy;
		int dltNE = 2 * (dy - dx);
		int d = 2 * dy - dx;
		int x = x11;
		int y = y11;
//		String p = "";
//		String q = "";
		while (x <= x22) {
			if (zone == 1) {
//				p += x; q += y;
				gl.glVertex2d(y, x);
			} else if (zone == 2) {
//				p += x; q += y;
				gl.glVertex2d(-y, x);
			} else if (zone == 3) {
//				p += x; q += y;
				gl.glVertex2d(-x, y);
			} else if (zone == 4) {
//				p += x; q += y;
				gl.glVertex2d(-x, -y);
			} else if (zone == 5) {
//				p += x; q += y;
				gl.glVertex2d(-y, -x);
			} else if (zone == 6) {
//				p += x; q += y;
				gl.glVertex2d(y, -x);
			} else if (zone == 7) {
//				p += x; q += y;
				gl.glVertex2d(x, -y);
			} else {
//				p += x; q += y;
				gl.glVertex2d(x, y);
			}
			if (d < 0) {
				x++;
				d += dltE;
			} else {
				x++;
				y++;
				d += dltNE;
			}

		}
//		System.out.println(p);
//		System.out.println(q);

		gl.glEnd();
	}


	int findZone(int x1, int y1, int x2, int y2) {
		int zone = 0;
		int dx = x2 - x1;
		int dy = y2 - y1;

		if (Math.abs(dx) >= Math.abs(dy)) {
			// Zone 0, 3, 4, 7
			if (dx >= 0 && dy >= 0) {
				zone = 0;
			} else if (dx <= 0 && dy >= 0) {
				zone = 3;
			} else if (dx <= 0 && dy <= 0) {
				zone = 4;
			} else if (dx >= 0 && dy <= 0) {
				zone = 7;
			}
		} else {
			// Zone 1, 2, 5, 6
			if (dx >= 0 && dy >= 0) {
				zone = 1;
			} else if (dx <= 0 && dy >= 0) {
				zone = 2;
			} else if (dx <= 0 && dy <= 0) {
				zone = 5;
			} else if (dx >= 0 && dy <= 0) {
				zone = 6;
			}
		}

		return zone;
	}

	int t1;

	int convertX(int x, int y, int zone) {
		int convertedX = 0;
		if (zone == 1) {
			t1 = x;
			x = y;
			y = t1;
			convertedX = x;
		} else if (zone == 2) {
			t1 = x;
			x = y;
			y = -t1;
			convertedX = x;
		} else if (zone == 3) {
			x = -x;
			y = y;
			convertedX = x;
		} else if (zone == 4) {
			x = -x;
			y = -y;
			convertedX = x;
		} else if (zone == 5) {
			t1 = x;
			x = -y;
			y = -t1;
			convertedX = x;
		} else if (zone == 6) {
			t1 = x;
			x = -y;
			y = t1;
			convertedX = x;
		} else if (zone == 7) {
			x = x;
			y = -y;
			convertedX = x;
		}
		else {
			convertedX = x;
		}

		return convertedX;
	}

	int convertY(int x, int y, int zone) {
		int convertedY = 0;
		if (zone == 1) {
			t1 = x;
			x = y;
			y = t1;
			convertedY = y;
		} else if (zone == 2) {
			t1 = x;
			x = y;
			y = -t1;
			convertedY = y;
		} else if (zone == 3) {
			x = -x;
			y = y;
			convertedY = y;
		} else if (zone == 4) {
			x = -x;
			y = -y;
			convertedY = y;
		} else if (zone == 5) {
			t1 = x;
			x = -y;
			y = -t1;
			convertedY = y;
		} else if (zone == 6) {
			t1 = x;
			x = -y;
			y = t1;
			convertedY = y;
		} else if (zone == 7) {
			x = x;
			y = -y;
			convertedY = y;
		}
		else {
			convertedY = y;
		}
		return convertedY;
	}

	public void dispose(GLAutoDrawable arg0) {
		// do nothing
	}
}
