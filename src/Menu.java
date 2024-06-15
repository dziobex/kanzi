import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    JButton exploreBtn, refreshBtn, statisticsBtn, practiceBtn, resetBtn;
    JPanel mainPanel;

    public Menu() {
        setSize(300, 235);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        pritify();

        exploreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exploreBtn.setText("Explore");
                Flashcard flashcard = new Flashcard("");
                flashcard.adjustData(Based.getInstance().getRandomWord(""));
            }
        });

        // states: 0, 1 (MARKED AS FORGOTTEN/SEMI-KNOWN)
        practiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flashcard flashcard = new Flashcard("0, 1");
                flashcard.adjustData(Based.getInstance().getRandomWord("0, 1"));
            }
        });

        // states: 2 (MARKED AS KNOWN)
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flashcard flashcard = new Flashcard("2");
                flashcard.adjustData(Based.getInstance().getRandomWord("2"));
            }
        });

        statisticsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Based.getInstance().updateStats();

                JOptionPane.showMessageDialog(Menu.this, String.format(
                            "Words seen: %d\n" +
                            "Words known: %d\n" +
                            "Words forgotten: %d\n\n" +
                            "Estimated HSK level: %d",
                                Based.getInstance().wordsMet, Based.getInstance().wordsKnown, Based.getInstance().wordsForgotten, Based.getInstance().hskLevel),
                        "Statistics", JOptionPane.INFORMATION_MESSAGE);
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
                    Based.getInstance().reset();
                }
            }
        });
    }

    void pritify() {
        // set the favicon
        ImageIcon favicon = new ImageIcon(getClass().getClassLoader().getResource("ming.png"));
        setIconImage(favicon.getImage());

        // set the title
        setTitle("看字");

        // set some fonts
        Font myFont = new Font("DengXian", Font.PLAIN, 16);
        UIDefaults defaults = UIManager.getDefaults();
        defaults.put("Button.font", myFont);
        defaults.put("Label.font", myFont);
        defaults.put("Menu.font", myFont);
        defaults.put("MenuItem.font", myFont);
        defaults.put("TextArea.font", myFont);
        defaults.put("Table.font", myFont);
        defaults.put("List.font", myFont);

        // how buttons should look like
        defaults.put("Button.background", Color.getHSBColor(180, 180, 170));
        defaults.put("Button.focusPainted", false);
        defaults.put("Button.contentAreaFilled", false);
        defaults.put("Button.opaque", true);

        SwingUtilities.updateComponentTreeUI(this);
    }
}
