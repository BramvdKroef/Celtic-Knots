import java.awt.Graphics;

/**
 * Draws straight lines and sharp corners. 
 */
public class StraightLine implements Line {
    
    public StraightLine() {
    }

    /**
     * Draw a straight line from point 1 to point 2 and one from
     * point 2 to point 3.
     */
    public void drawLine(Graphics g, int[] p1, int[] p2, int[] p3) {
        g.drawLine(p1[X], p1[Y], p2[X], p2[Y]);
        g.drawLine(p2[X], p2[Y], p3[X], p3[Y]);
    }
}