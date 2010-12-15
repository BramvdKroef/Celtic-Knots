import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;

public class CelticKnot extends JPanel {

    public CelticKnot (int horizontalNodes, int verticalNodes) {
        super(new GridLayout(horizontalNodes, verticalNodes, 0, 0));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(horizontalNodes * 40,
                                       verticalNodes * 40));

        boolean even_v = false;
        for (int y = 0; y < verticalNodes; y++) {
            boolean even_h = even_v;
            
            for (int x = 0; x < horizontalNodes; x++) {
                
                int direction = even_h ? CelticNode.NORTHWEST_SOUTHEAST
                    : CelticNode.SOUTHWEST_NORTHEAST;
                CelticNode node = new CelticNode(direction);
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
    }
    
}