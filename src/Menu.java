import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JButton exploreBtn;
    private JButton reviewBtn;
    private JButton statisticsBtn;
    private JButton testBtn;
    private JPanel mainPanel;

    public Menu() {
        setTitle("看字");
        setSize(300, 200);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        exploreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flashcard flashcard = new Flashcard();
                flashcard.setVisible(true);
                // JOptionPane.showMessageDialog(Menu.this, "KUPA", "New word!", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        reviewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Menu.this, "GÓWNO",
                        "Old word!", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        statisticsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Menu.this,
                        "Words seen: 2137\nWords known: 666\nWords partially-known: 665\nWords forgotten: 69\n\nEstimated HSK level: 3",
                        "Statistics", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Menu.this, "做的好！",
                        "Test results", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
