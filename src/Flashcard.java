import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Flashcard extends JFrame {
    String category;
    Word word;

    JPanel flashcardPanel, mainPanel;
    JTextPane meaningText;
    JTextArea simplifiedText, traditionalText;
    JLabel pinyinText;
    JButton forgottenBtn, gotBtn, almostBtn;

    void saveMe(int known) {
        try {
            // save it
            Based.getInstance().saveTheWord(Flashcard.this.word, known);
            Word newWord = Based.getInstance().getRandomWord(category);
            // generate next flashcard
            this.adjustData(newWord);
        } catch (RuntimeException re) {
            System.out.println("Coś poszło nie tak :(");
        }
    }

    Flashcard(String category) {
        this.category = category;

        String dynastyLogo = (this.category == "" ? "song" : this.category == "2" ? "tang" : "han") + ".png";
        ImageIcon favicon = new ImageIcon(getClass().getClassLoader().getResource(dynastyLogo));

        // flashcard design
        setIconImage(favicon.getImage());
        setResizable(false);
        setContentPane(mainPanel);
        pack();

        simplifiedText.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        gotBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMe(2);
            }
        });

        almostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMe(1);
            }
        });

        forgottenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMe(0);
            }
        });
    }

    public void adjustData(Word word) {
        if ( word == null ) {
            this.setVisible(false);
            this.dispose();
            String message = String.format("Hm! It looks like there aren't any cards, which you can %s!",
                    this.category == "" ? "explore" : this.category == "2" ? "refresh" : "practice");
            JOptionPane.showMessageDialog(null,
                    message,
                    "OOPS... NO CARDS!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.word = word;

        if ( this.category == "" )
            setTitle( "New word!" );
        else if ( this.category == "2" )
            setTitle( "Learned by xin!" );
        else
            setTitle("You have seen this... Remember?");

        traditionalText.setText(word.getTraditional());
        simplifiedText.setText(word.getSimplified());
        pinyinText.setText(word.getPinyin());
        meaningText.setText("<html><center><i>" + word.getMeaning() + "</i></center</html>");
        mainPanel.setPreferredSize(flashcardPanel.getPreferredSize());
        pack();
        setVisible(true);
    }
}
