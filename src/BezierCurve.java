import java.awt.Graphics;

/**
 * Draws a (quadratic) Bézier curve for each line
 * http://en.wikipedia.org/wiki/B%C3%A9zier_curve
 */
public class BezierCurve implements Line {
    
    public BezierCurve() {
    }

    public void drawLine(Graphics g, int[] p1, int[] p2, int[] p3) {
        int points = 100;
        int[] bx = new int[points + 1], by = new int[points + 1];

        double t = 0;
        for (int i = 0; i <= points; i++) {
            bx[i] = (int) 
                (Math.pow(1 - t, 2) * p1[X] +
                 2 * (1 - t) * t * p2[X] +
                 Math.pow(t, 2) * p3[X]);
            by[i] = (int)
                (Math.pow(1 - t, 2) * p1[Y] +
                 2 * (1 - t) * t * p2[Y] +
                 Math.pow(t, 2) * p3[Y]);
            t += .01;
        }

        g.drawPolyline(bx, by, points + 1);
    }
}