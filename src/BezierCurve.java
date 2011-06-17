import java.awt.Graphics;

/**
 * Draws a (quadratic) Bézier curve for each line
 * @see http://en.wikipedia.org/wiki/B%C3%A9zier_curve
 */
public class BezierCurve implements Line {
    
    public BezierCurve() {
    }

    /**
     * Draw a curve from point 1, trough point 2, ending in point 3.
     */
    public void drawLine(Graphics g, int[] p1, int[] p2, int[] p3) {
        // The number of points is the horizontal distance between p1 and p3.
        // 1 point for each pixel between p1 and p3
        int points = p3[0] - p1[0];
        
        int[] bx = new int[points + 1], by = new int[points + 1];
        double interval = 1 / (double)points;

        // for each t in range 0...1 
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
            t += interval;
        }

        g.drawPolyline(bx, by, points + 1);
    }
}