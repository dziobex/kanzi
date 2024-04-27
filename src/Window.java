import javax.swing.*;

public class Window extends JFrame {

    private JPanel panel1;
    private JTextField charText;
    private JLabel infoLabel;

    public Window() {
        setTitle("KANZI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
