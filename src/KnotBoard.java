import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JColorChooser;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Creates a frame to display a celtic knot in and adds a few controls to change
 * the line style, the foreground and background colors and to toggle the grid.
 */
public class KnotBoard extends JPanel implements ActionListener {
    private CelticKnot knot;
    private JComboBox line;
    private JCheckBox showgrid, interlace;
    private JButton fg, bg;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        int x = 5, y = 5;

        if (args.length == 2) {
            try {
                x = Integer.parseInt(args[0]);
                y = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Error: Two arguments were given but they " +
                                   "could not be parsed as integers.");
                return;
            }
        }
        
        frame.add(new KnotBoard(x, y));

        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public KnotBoard (int horizontalNodes, int verticalNodes) {
        super(new BorderLayout());
        knot = new CelticKnot(horizontalNodes, verticalNodes);
        JPanel controls = new JPanel();
        
        line = new JComboBox();
        line.addItem("B\u00E9zier Curves");
        line.addItem("Straight");
        
        controls.add(new JLabel("Line Type"));
        controls.add(line);
        line.addActionListener(this);
        
        interlace = new JCheckBox("Interlace", knot.isInterlaced());
        controls.add(interlace);
        interlace.addActionListener(this);

        showgrid = new JCheckBox("Show Grid", knot.isGridShown());
        controls.add(showgrid);
        showgrid.addActionListener(this);

        fg = new JButton("FG");
        controls.add(fg);
        fg.addActionListener(this);
        
        bg = new JButton("BG");
        controls.add(bg);
        bg.addActionListener(this);
                
        add(knot);
        add(controls, BorderLayout.SOUTH);
    }

    public void	actionPerformed(ActionEvent e) {
        if (e.getSource() == line) {
            if (line.getSelectedItem().equals("Straight")) {
                knot.setLine(new StraightLine());
            } else {
                knot.setLine(new BezierCurve());
            }
        } else if (e.getSource() == showgrid) {
            knot.setShowGrid(showgrid.isSelected());
        } else if (e.getSource() == interlace) {
            knot.setInterlace(interlace.isSelected());
        } else if (e.getSource() == fg) {
            Color newColor =
                JColorChooser.showDialog(this,
                                         "Choose Foreground Color",
                                         knot.getForeground());
            if (newColor != null) 
                knot.setForeground(newColor);
        } else if (e.getSource() == bg) {
            Color newColor =
                JColorChooser.showDialog(this,
                                         "Choose Background Color",
                                         knot.getBackground());
            if (newColor != null) 
                knot.setBackground(newColor);
        }
    }
}