import javax.swing.JComponent;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import java.awt.Color;

public class CelticNode extends JComponent {
    public static final int NORTHWEST_SOUTHEAST = 0,
        SOUTHWEST_NORTHEAST = 1;
    public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    
    private int direction;
    private boolean[] border;

    public CelticNode (int direction) {
        this.direction = direction;
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
       
        if (direction == NORTHWEST_SOUTHEAST) {
            if ((border[NORTH] && border[WEST]) ||
                border[SOUTH] && border[EAST]) {
                // Can't draw the line if one of the points has a dead end
                return;
            } else if (border[NORTH] && border[EAST]) {
                // arc from west to south
                g.drawArc(0 -(int)(getWidth() * .5), (int)(getHeight() * .5),
                          getWidth(), getHeight(),
                          0, 90);
            } else if (border[SOUTH] && border[WEST]) {
                // arc from east to north
                g.drawArc((int)(getWidth() * .5),
                          0 - (int)(getHeight() * .5),
                          getWidth(), getHeight(),
                          180, 360);
            } else if(border[NORTH] && border[SOUTH]) {
                // line from east to west
                int y = (int)(getHeight() * .5);
                g.drawLine(0, y, getWidth(), y);
            } else if(border[EAST] && border[WEST]) {
                // line from north to south
                int x = (int)(getWidth() * .5);
                g.drawLine(x, 0, x, getHeight());
            } else if (border[NORTH]) {
                // arc from west to southeast
                g.drawArc(0 - (int)(getWidth() * 1.5), (int)(getHeight() * .5),
                          (int)(getWidth() * 2.5), getHeight() * 2,
                          0, 360);
            } else if (border[EAST]) {
                // arc from northwest to south
                g.drawArc(0, getHeight(),
                          (int)(getWidth() * .5), getHeight(),
                          0, 90);
            } else if (border[SOUTH]) {
                // arc from northwest to east
                g.drawArc(0, 0,
                          getWidth(), (int)(getHeight() * .5),
                          180, -90);
            } else if (border[WEST]) {
                // arc from north to southeast
                g.drawArc(getWidth(), 0,
                          (int)(getWidth() * .5), getHeight(),
                          270, -90);
            } else {
                // line from northwest to southeast
                g.drawLine(0, 0, getWidth(), getHeight());
            }
            
        } else if (direction == SOUTHWEST_NORTHEAST) {
            // Can't draw the line if one of the points has a dead end
            if ((border[SOUTH] && border[WEST]) ||
                border[NORTH] && border[EAST]) {
                return;
            } else if (border[SOUTH] && border[EAST]) {
                // arc from west to north
                g.drawArc(0 - (int)(getWidth() * .5), 0 - (int)(getHeight() * .5),
                          getWidth(), getHeight(),
                          270, 90);
            } else if (border[NORTH] && border[WEST]) {
                // arc from east to south
                g.drawArc((int)(getWidth() * .5),
                          (int)(getHeight() * .5),
                          getWidth(), getHeight(),
                          90, 90);
            } else {
                int[] p1 = new int[2], p2 = new int[2];
                p1[0] = (border[WEST] ? (int)(getWidth() * .5) : 0);
                p1[1] = (border[SOUTH] ? (int)(getHeight() * .5) : getHeight());
                p2[0] = (border[EAST] ? (int)(getWidth() * .5) : getWidth());
                p2[1] = (border[NORTH] ? (int)(getHeight() * .5) : 0);
                g.drawLine(p1[0], p1[1], p2[0], p2[1]);
            }
        }
    }

    
}