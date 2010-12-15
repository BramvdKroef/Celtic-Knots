import java.awt.Graphics;

/**
 * Draws straight lines and sharp corners. 
 */
public class StraightLine implements Line {
    
    public StraightLine() {
    }

    public void drawLine(Graphics g, int[] p1, int[] p2, int[] p3) {
        g.drawLine(p1[X], p1[Y], p2[X], p2[Y]);
        g.drawLine(p2[X], p2[Y], p3[X], p3[Y]);
    }
}