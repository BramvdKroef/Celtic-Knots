import java.awt.Graphics;

public interface Line {
    public final static int X = 0, Y = 1;
    
    /**
     * Draw a straight line from point 1 to point 2.
     */
    public void drawLine(Graphics g, int[] p1, int[] p2, int[] p3);


}