/*
 * @Jubaer
 * 
 * */
package beethoven;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class MidPointCircle implements GLEventListener {

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
		DrawMPL(gl, 0, 0, 30);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// do nothing
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// do nothing
	}

	int dx, dy, b;

	private void DrawMPL(GL2 gl, int x1, int y1, int r) {
		// write your own code
		gl.glPointSize(1.0f);
		gl.glColor3d(1, 0, 0);

		gl.glBegin(GL2.GL_POINTS);
		
		double x = 0;
		double y = r;
		double d = 1 - r;
		gl.glVertex2d(x+x1,y+y1);
		gl.glVertex2d(y+x1,x+y1);
		gl.glVertex2d(y+x1,-x+y1);
		gl.glVertex2d(x+x1,-y+y1);
		gl.glVertex2d(-x+x1,-y+y1);
		gl.glVertex2d(-y+x1,-x+y1);
		gl.glVertex2d(-y+x1,x+y1);
		gl.glVertex2d(-x+x1,y+y1);
		
		while((int)y > (int)x) {
			if((int)d < 0) {
				d += 2*x + 3;
			}
			else {
				d += 2*(x - y) + 5;
				y--;
			}
			x++;
			gl.glVertex2d(x+x1,y+y1);
			gl.glVertex2d(y+x1,x+y1);
			gl.glVertex2d(y+x1,-x+y1);
			gl.glVertex2d(x+x1,-y+y1);
			gl.glVertex2d(-x+x1,-y+y1);
			gl.glVertex2d(-y+x1,-x+y1);
			gl.glVertex2d(-y+x1,x+y1);
			gl.glVertex2d(-x+x1,y+y1);
		}
		
		
		gl.glEnd();
	}

	public void dispose(GLAutoDrawable arg0) {
		// do nothing
	}
}

//public class MidPointCircle {
//	void midPointCircle(int r) {
//		int x = 0;
//		int y = r;
//		double d = 1 - r;
//		CirclePoint(x, y);
//
//		while (y > x) {
//			if (d < 0) {
//				d += 2 * x + 3;
//			} else {
//				d += 2 * (x - y) + 5;
//				y--;
//			}
//			x++;
//			CirclePoint(x, y); // gl.vertex2d(x, y)
//		}
//	}
//}
