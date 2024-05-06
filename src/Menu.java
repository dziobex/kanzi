import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JFrame {
    private JButton exploreBtn;
    private JButton reviewBtn;
    private JButton statisticsBtn;
    private JButton testBtn;
    private JPanel mainPanel;

    Based based;

    public Menu(Based based) {
        setTitle("看字");
        setSize(300, 200);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        this.based = based;

        exploreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flashcard flashcard = new Flashcard(based);
                flashcard.AdjustData(based.GetRandomWord());
                flashcard.setVisible(true);
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
                based.UpdateStats();

                JOptionPane.showMessageDialog(Menu.this, String.format(
                        "Words seen: %d\n" +
                                "Words known: %d\n" +
                                "Words forgotten: %d\n\n" +
                                "Estimated HSK level: %d",
                        based.wordsMet, based.wordsKnown, based.wordsForgotten, based.hskLevel),
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
