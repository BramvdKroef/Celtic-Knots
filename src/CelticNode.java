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
    private boolean interlace = true;
    
    private boolean[] border;

    public CelticNode (final int direction, final Line line) {
        this(direction, line, true);
    }
    
    public CelticNode (final int direction, final Line line,
                       final boolean interlace) {
        this.direction = direction;
        this.line = line;
        this.interlace = interlace;
        
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

    public void setInterlace (boolean b) {
        interlace = b;
    }
    public boolean isInterlaced() {
        return interlace;
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

    /**
     * Locates the three points that the line for this node runs
     * through.
     *
     * Point 2, the middle, is always the center. Point 1 and 3 depend
     * on wether this.direction is NORTHWEST_SOUTHEAST (point 1 is
     * northwest and point 3 southeast) or SOUTHWEST_NORTHEAST (point
     * 1 is southwest and point 3 northeast).
     *
     * If the node has active borders (a boolean in this.border[] is
     * set to true) the line is adjusted.
     *
     * For example if point 1 is northwest and the northern border is
     * active then point 1 is changed to west, thus avoiding the
     * border. Instead if the western border is active the point is
     * changed to north.
     *
     * If interlacing is on the northeast -> southeast lines are
     * shortened so they don't touch the other nodes.
     *
     * @return an array containing an array for each of the three
     *   points. For each point an x and a y value are given. The
     *   range for the values is 0-1.
     */
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

            if (interlace) {
                if (!border[WEST] && !border[NORTH]) {
                    p[0][0] = .05;
                    p[0][1] = .05;
                }

                if (!border[EAST] && !border[SOUTH]) {
                    p[2][0] = .95;
                    p[2][1] = .95;
                }
            }
            
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