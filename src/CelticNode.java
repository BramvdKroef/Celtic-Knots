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

    public CelticNode (int direction, Line line) {
        this.direction = direction;
        this.line = line;
        
        border = new boolean[4];
        for (int i = 0; i < 4; i++)
            border[i] = false;
    }

    public void setBorder(int side, boolean on) {
        if (side < 0 || side > 3) {
            throw new IndexOutOfBoundsException(side + " out of range 0-3");
        }
        border[side] = on;
    }

    public boolean getBorder(int side) {
        if (side < 0 || side > 3) {
            throw new IndexOutOfBoundsException(side + " out of range 0-3");
        }
        return border[side];
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
            if (border[NORTH]) {
                // p 0 moves west
                p[0][0] = 0;
                p[0][1] = .5;
            } else if (border[WEST]) {
                // p 0 moves north
                p[0][0] = .5;
                p[0][1] = 0;
            } else {
                // p 0 is northwest
                p[0][0] = 0;
                p[0][1] = 0;
            }
            
            if(border[SOUTH]) {
                // p 2 moves east
                p[2][0] = 1;
                p[2][1] = .5;
            } else if(border[EAST]) {
                // p 2 moves south
                p[2][0] = .5;
                p[2][1] = 1;
            } else {
                // p 2 is southeast
                p[2][0] = 1;
                p[2][1] = 1;
            }
            
        } else if (direction == SOUTHWEST_NORTHEAST) {
            if ((border[SOUTH] && border[WEST]) ||
                border[NORTH] && border[EAST]) {
                // Can't draw the line if one of the points has a dead end
                return null;
            }

            if (border[SOUTH]) {
                // p 0 moves west
                p[0][0] = 0;
                p[0][1] = .5;
            } else if (border[WEST]) {
                // p 0 moves south
                p[0][0] = .5;
                p[0][1] = 1;
            } else {
                // p 0 is southwest
                p[0][0] = 0;
                p[0][1] = 1;
            }

            if (border[NORTH]) {
                // p 2 moves east
                p[2][0] = 1;
                p[2][1] = .5;
            } else if (border[EAST]) {
                // p 2 moves north
                p[2][0] = .5;
                p[2][1] = 0;
            } else {
                // p 2 is northeast
                p[2][0] = 1;
                p[2][1] = 0;
            }
        }
        p[1][0] = .5;
        p[1][1] = .5;
        return p;
    }
    
}