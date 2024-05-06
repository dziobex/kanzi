import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Flashcard extends JFrame {
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

            // generate next flashcard
            Flashcard flashcard = new Flashcard(based);
            flashcard.AdjustData(based.GetRandomWord());
            flashcard.setVisible(true);

            // close it
            Flashcard.this.dispose();
        } catch (RuntimeException re) {
            System.out.println("Coś poszło nie tak :(");
        }
    }

    Flashcard(Based based) {
        this.based = based;

        setVisible(true);
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
        this.word = word;

        setTitle( String.valueOf(word.GetId()) );
        traditionalText.setText(word.GetTraditional());
        simplifiedText.setText(word.GetSimplified());
        pinyinText.setText(word.GetPinyin());
        meaningText.setText("<html><center><i>" + word.GetMeaning() + "</i></center</html>");
        mainPanel.setPreferredSize(flashcardPanel.getPreferredSize());
        pack();
    }
}
