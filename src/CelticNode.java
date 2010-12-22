import javax.swing.JComponent;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import java.awt.Color;

public class CelticNode extends JComponent {
    public static final int NORTHWEST_SOUTHEAST = 0,
        SOUTHWEST_NORTHEAST = 1;
    public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    
    private int direction;
    private Line line;
    
    private boolean[] border;

    public CelticNode (final int direction, final Line line) {
        this.direction = direction;
        this.line = line;
        
        border = new boolean[4];
        for (int i = 0; i < 4; i++)
            border[i] = false;
    }

    public void setBorder(final int side, boolean on) {
        if (side < 0 || side > 3) 
            throw new IndexOutOfBoundsException(side + " out of range 0-3");
        
        border[side] = on;
        repaint();
    }

    public boolean getBorder(final int side) {
        if (side < 0 || side > 3) 
            throw new IndexOutOfBoundsException(side + " out of range 0-3");
        
        return border[side];
    }

    public boolean toggleBorder(final int side) {
        boolean b = getBorder(side);
        setBorder(side, !b);
        return !b;
    }

    public final Line getLine () {
        return line;
    }
    public void setLine(final Line line) {
        this.line = line;
        repaint();
    }

    public void paintComponent(Graphics g) {
        double[][] p = getPoints();

        if (p == null)
            return;
        
        int[][] points = new int[3][2];
        for (int i = 0; i < 3; i++) {
            points[i][0] = (int)(getWidth() * p[i][0]);
            points[i][1] = (int)(getHeight() * p[i][1]);
        }
        
        line.drawLine(g, points[0], points[1], points[2]);
    }

    private double[][] getPoints() {
        double[][] p = new double[3][2];
        
        if (direction == NORTHWEST_SOUTHEAST) {
            if ((border[NORTH] && border[WEST]) ||
                border[SOUTH] && border[EAST]) {
                // Can't draw the line if one of the points has a dead end
                return null;
            }
            
            // line is {0,0} -> {1, 1}. Adjust if there are borders.
            p[0][0] = (border[WEST] ? .5 : 0);
            p[0][1] = (border[NORTH] ? .5 : 0);

            p[2][0] = (border[EAST] ? .5 : 1);
            p[2][1] = (border[SOUTH] ? .5 : 1);
            
        } else if (direction == SOUTHWEST_NORTHEAST) {
            if ((border[SOUTH] && border[WEST]) ||
                border[NORTH] && border[EAST]) {
                // Can't draw the line if one of the points has a dead end
                return null;
            }

            // line is {0,1} -> {1, 0}. Adjust if there are borders.
            p[0][0] = (border[WEST] ? .5 : 0);
            p[0][1] = (border[SOUTH] ? .5 : 1);

            p[2][0] = (border[EAST] ? .5 : 1);
            p[2][1] = (border[NORTH] ? .5 : 0);
        }
        p[1][0] = .5;
        p[1][1] = .5;
        return p;
    }
    
}