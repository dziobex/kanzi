import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Flashcard extends JFrame {
    String category;
    Word word;
    Based based;

    private JButton forgottenBtn;
    private JPanel flashcardPanel;
    private JPanel mainPanel;
    private JTextPane meaningText;
    private JLabel traditionalText;
    private JLabel simplifiedText;
    private JLabel pinyinText;
    private JButton gotBtn;
    private JButton almostBtn;

    void saveMe(int known) {
        try {
            // save it
            based.SaveTheWord(Flashcard.this.word, known);
            Word newWord = based.GetRandomWord(category);
            // generate next flashcard
            this.AdjustData(newWord);
        } catch (RuntimeException re) {
            System.out.println("Coś poszło nie tak :(");
        }
    }

    Flashcard(Based based, String category) {
        this.based = based;
        this.category = category;

        setResizable(false);
        setContentPane(mainPanel);
        pack();

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

    public void AdjustData(Word word) {
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

        traditionalText.setText(word.GetTraditional());
        simplifiedText.setText(word.GetSimplified());
        pinyinText.setText(word.GetPinyin());
        meaningText.setText("<html><center><i>" + word.GetMeaning() + "</i></center</html>");
        mainPanel.setPreferredSize(flashcardPanel.getPreferredSize());
        pack();
        setVisible(true);
    }
}
