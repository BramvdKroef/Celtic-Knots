import java.awt.Graphics;

public interface Line {
    public final static int X = 0, Y = 1;
    
    /**
     * Draw a line from point 1 to point 3 through point 2.
     */
    public void drawLine(Graphics g, int[] p1, int[] p2, int[] p3);


}