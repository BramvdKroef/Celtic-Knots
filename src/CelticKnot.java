import javax.swing.JComponent;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

/**
 * Fills itself with CelticNodes on a GridLayout without gaps. By default the
 * nodes on the edges are given borders to contain the knot, but they can be
 * removed. A Grid is overlayed to allow user interaction.
 *
 * @see Grid
 * @see CelticNode
 */
public class CelticKnot extends JComponent {
    private Line line = new BezierCurve();
    private Grid grid;
    private boolean interlace = true;

    /**
     * Because GridLayout has no way to retrieve a component by giving the row
     * and column we have to keep this record of node positions.
     */
    private CelticNode[][] nodes;
    private boolean showgrid = true;

    /**
     * Create a new knot.
     * The number of horizontal and vertical nodes is easiest to understand by
     * looking at the grid overlay on the knot. With 5 horizontal nodes the grid
     * shows 5 (+ 1 on the edge) dots in each row.
     *
     * @param horizontalNodes  width of the knot in nodes
     * @param verticalNodes  height of the knot in nodes
     */
    public CelticKnot (int horizontalNodes, int verticalNodes) {
        super();

        if (horizontalNodes <= 0) {
            throw new IllegalArgumentException("Number of horizontal " +
                                               "nodes needs to be above 0. " +
                                               "Number given is " +
                                               horizontalNodes);
        }
        if (verticalNodes <= 0) {
            throw new IllegalArgumentException("Number of vertical " +
                                               "nodes needs to be above 0. " +
                                               "Number given is " +
                                               verticalNodes);
        }
        
        // There are four nodes surrounding each grid dot. NW-SE, SW-NE on top
        // and SW-NE, NW-SE on the bottom, forming a diamond shape.
        horizontalNodes *= 2;
        verticalNodes *= 2;
        
        setLayout(new GridLayout(horizontalNodes, verticalNodes, 0, 0));
        nodes = new CelticNode[horizontalNodes][verticalNodes];
        setBackground(Color.WHITE);
        
        boolean even_v = false;
        for (int y = 0; y < verticalNodes; y++) {
            boolean even_h = even_v;
            
            for (int x = 0; x < horizontalNodes; x++) {
                
                int direction = even_h ? CelticNode.NORTHWEST_SOUTHEAST
                    : CelticNode.SOUTHWEST_NORTHEAST;
                CelticNode node = new CelticNode(direction, line, interlace);
                nodes[x][y] = node;
                add(node);

                // add borders to nodes on the edge of the knot
                if (y == 0) {
                    node.setBorder(CelticNode.NORTH, true);
                } else if (y == verticalNodes - 1) {
                    node.setBorder(CelticNode.SOUTH, true);
                }
                if (x == 0) {
                    node.setBorder(CelticNode.WEST, true);
                } else if (x == horizontalNodes - 1) {
                    node.setBorder(CelticNode.EAST, true);
                }
                even_h = !even_h;
            }
            even_v = !even_v;
        }

        // GridLayout and nodes should have the same number of rows
        // and columns.
        assert nodes.length == getColumns();
        assert nodes[0].length == getRows();
        
        grid = new Grid(this);
    }

    public int getColumns() {
        return ((GridLayout)getLayout()).getColumns();
    }

    public int getRows() {
        return ((GridLayout)getLayout()).getRows();
    }

    /**
     * Returns the node on column x, row y.
     */
    public final CelticNode getNode (int x, int y) {
        return nodes[x][y];
    }

    /**
     * Paints the background and the grid (if isGridShown() returns true).
     */
    public void paintComponent(Graphics g) {
        // For some reason the color from getBackground() is not used by
        // default. So we draw the background here.
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // add the grid
        if (isGridShown())
            grid.paint(g);
    }

    public final boolean isGridShown() {
        return showgrid;
    }

    /**
     * Show or hide the grid.
     * Repaints the knot.
     */
    public void setShowGrid(boolean show) {
        showgrid = show;
        repaint();
    }

    /**
     * Update the Line used to draw the knot. This will update the line on each
     * of the CelticNodes that make up the knot by calling
     * CelticNode.setLine(). 
     * This method forces a repaint of the knot.
     *
     * Technically you could set different line types for parts of the knot but
     * there is no code yet that does that.
     */
    public void setLine (Line line) {
        Component[] c = getComponents();
        for (int i = 0; i < c.length; i++) {
            if (c[i] instanceof CelticNode) {
                ((CelticNode)c[i]).setLine(line);
            }
        }
        // repaint the whole image
        repaint();
    }

    public void setInterlace (boolean b) {
        Component[] c = getComponents();
        for (int i = 0; i < c.length; i++) {
            if (c[i] instanceof CelticNode) {
                ((CelticNode)c[i]).setInterlace(b);
            }
        }
        // repaint the whole image
        repaint();
    }
    public boolean isInterlaced() {
        return interlace;
    }
}

