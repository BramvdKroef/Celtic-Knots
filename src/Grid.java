import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Grid implements MouseListener {
    private CelticKnot knot;
    private int[] selected = null;
    
    public Grid (CelticKnot knot) {
        this.knot = knot;
        knot.addMouseListener(this);
    }
    
    /**
     * Paint the grid.
     * Draws a dot every two nodes. The dots can be connected by clicking on two
     * dots consecutively. This will cause borders to be added to the nodes
     * along the line between the dots.
     */
    public void paint(Graphics g) {
        int columns = knot.getColumns() / 2;
        int rows = knot.getRows() / 2;
        
        int xStep = knot.getWidth() / columns;
        int yStep = knot.getHeight() / rows;

        for (int x = 0; x <= columns; x++) {
            for (int y = 0; y <= rows; y++) {
                if (selected != null && selected[0] == x &&
                    selected[1] == y) {
                    g.setColor(Color.RED);
                } else
                    g.setColor(knot.getForeground());
                g.fillOval(x * xStep - 5, y * yStep - 5, 10, 10);
                
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int columns = knot.getColumns() / 2;
        int rows = knot.getRows() / 2;
        
        int xStep = knot.getWidth() / columns;
        int yStep = knot.getHeight() / rows;

       
        int[] newselection = new int[2];
        newselection[0] = (int)Math.round((double)e.getX() / xStep);
        newselection[1] = (int)Math.round((double)e.getY() / yStep);

        // if a grid point has previously been selected
        if (selected != null) {
            // add it to the repaint list
            knot.repaint(0, selected[0] * xStep - 6,
                         selected[1] * yStep - 6,
                         12, 12);
            
            // if new selection is adjacent to the last
            if (Math.abs(selected[0] - newselection[0]) +
                Math.abs(selected[1] - newselection[1]) == 1) {
                toggleBorders(selected, newselection);
            }
        }

        selected = newselection;
        knot.repaint(0, selected[0] * xStep - 6,
                     selected[1] * yStep - 6,
                     12, 12);
    }

    private void toggleBorders (int[] p1, int[] p2) {
        if (p1[0] == p2[0]) {
            int x = p1[0] * 2;
            int y = Math.min(p1[1], p2[1]) * 2;
            for (int i = 0; i < 2;  i++) {
                if (x < knot.getColumns()) {
                    knot.getNode(x, y + i).
                        toggleBorder(CelticNode.WEST);
                }
                if (x > 0) {
                    knot.getNode(x - 1, y + i).
                        toggleBorder(CelticNode.EAST);
                }
            }
        } else {
            int x = Math.min(p1[0], p2[0]) * 2;
            int y = p1[1] * 2;

            for (int i = 0; i < 2;  i++) {
                if (y < knot.getRows()) {
                    knot.getNode(x + i, y).
                        toggleBorder(CelticNode.NORTH);
                }
                if (y > 0) {
                    knot.getNode(x + i, y - 1).
                        toggleBorder(CelticNode.SOUTH);
                }
            }
        }
    }
    
    public void	mouseEntered(MouseEvent e) {
    }
    public void	mouseExited(MouseEvent e) {
    }
    public void	mousePressed(MouseEvent e) {
    }
    public void	mouseReleased(MouseEvent e) {
    }
}