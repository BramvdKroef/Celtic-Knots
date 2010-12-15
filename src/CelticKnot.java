import javax.swing.JComponent;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class CelticKnot extends JComponent {
    private Line line = new BezierCurve();
    private Grid grid;
    private CelticNode[][] nodes;
    private boolean showgrid = true;
    
    public CelticKnot (int horizontalNodes, int verticalNodes) {
        super();
        setLayout(new GridLayout(horizontalNodes, verticalNodes, 0, 0));
        nodes = new CelticNode[horizontalNodes][verticalNodes];
        setBackground(Color.WHITE);
        
        boolean even_v = false;
        for (int y = 0; y < verticalNodes; y++) {
            boolean even_h = even_v;
            
            for (int x = 0; x < horizontalNodes; x++) {
                
                int direction = even_h ? CelticNode.NORTHWEST_SOUTHEAST
                    : CelticNode.SOUTHWEST_NORTHEAST;
                CelticNode node = new CelticNode(direction, line);
                nodes[x][y] = node;
                add(node);
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

        grid = new Grid(this);
    }

    public int getColumns() {
        return ((GridLayout)getLayout()).getColumns();
    }

    public int getRows() {
        return ((GridLayout)getLayout()).getRows();
    }

    public final CelticNode getNode (int x, int y) {
        return nodes[x][y];
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        if (showgrid)
            grid.paint(g);
    }

    public final boolean isGridShown() {
        return showgrid;
    }
    public void setShowGrid(boolean show) {
        showgrid = show;
        repaint();
    }

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
}

