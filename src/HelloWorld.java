import javax.swing.JFrame;


public class HelloWorld  {

    public static void main(String[] args) {
        CelticKnot k = new CelticKnot(6,6);
        JFrame frame = new JFrame();
        frame.add(k);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}