import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JButton exploreBtn;
    private JButton refreshBtn;
    private JButton statisticsBtn;
    private JButton testBtn;
    private JPanel mainPanel;
    private JButton practiceBtn;
    private JButton resetBtn;

    Based based;

    public Menu(Based based) {
        ImageIcon favicon = new ImageIcon("assets/fav.png");

        // main menu design
        setIconImage(favicon.getImage());
        setTitle("看字");
        setSize(300, 235);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        this.based = based;

        exploreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exploreBtn.setText("Explore");
                Flashcard flashcard = new Flashcard(based, "");
                flashcard.AdjustData(based.GetRandomWord(""));
            }
        });
        // states: 0, 1 (MARKED AS FORGOTTEN/SEMI-KNOWN)
        practiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flashcard flashcard = new Flashcard(based, "0, 1");
                flashcard.AdjustData(based.GetRandomWord("0, 1"));
            }
        });
        // states: 2 (MARKED AS KNOWN)
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flashcard flashcard = new Flashcard(based, "2");
                flashcard.AdjustData(based.GetRandomWord("2"));
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
        // deletes the data from HSK_WORDS_STATES
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(Menu.this,
                        "Your WHOLE progress will be deleted.\n...are you surely sure?",
                        "The garbage guy (dziobex) said...",
                        JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION) {
                    // yes option
                    based.Reset();
                }
            }
        });
    }
}
