import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Handling base-related stuff
public class Based {
    int wordsMet, wordsKnown, wordsForgotten;
    int hskLevel;

    Based() {
        System.out.println("Based class was created.");
        wordsMet = wordsKnown = wordsForgotten = 0;
    }

    public void UpdateStats() {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:db/HSK.db");
            Statement statement = connection.createStatement();

            wordsMet = statement.executeQuery("SELECT COUNT(*) as \"count\" FROM \"HSK_WORDS_STATES\"")
                    .getInt(1);
            wordsKnown = statement.executeQuery("SELECT COUNT(*) as \"count\" FROM \"HSK_WORDS_STATES\" WHERE state = 2")
                    .getInt(1);
            wordsForgotten = statement.executeQuery("SELECT COUNT(*) as \"count\" FROM \"HSK_WORDS_STATES\" WHERE state = 0")
                    .getInt(1);
            hskLevel = Math.floorDiv(wordsMet, 150) + 1;

            System.out.println(String.format("Words in total: %d\nKnown: %d\nForgotten: %d\n", wordsMet, wordsKnown, wordsForgotten));

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Word GetRandomWord() {
        ArrayList<Word> wordList = new ArrayList<Word>();

        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:db/HSK.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT * FROM \"HSK_WORDS\" WHERE \"LEVEL\" <= %d AND ", this.hskLevel) +
                    String.format("id NOT IN (SELECT id_word FROM \"HSK_WORDS_STATES\" WHERE state = 2)"));

            while (resultSet.next()) {
                Word word = new Word();
                word.SetId(resultSet.getInt("id"));
                word.SetTraditional(resultSet.getString("traditional"));
                word.SetSimplified(resultSet.getString("simplified"));
                word.SetPinyin(resultSet.getString("pinyin"));
                word.SetMeaning(resultSet.getString("meaning"));
                word.SetLevel(resultSet.getInt("LEVEL"));
                wordList.add(word);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wordList.get((int)(Math.random() * wordList.size()));
    }

    void SaveTheWord(Word word, int known) {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:db/HSK.db");

            String sql =
                    String.format("INSERT INTO HSK_WORDS_STATES (id_word, state) SELECT %d, %d ", word.GetId(), known) +
                    String.format("WHERE NOT EXISTS (SELECT 1 FROM HSK_WORDS_STATES WHERE id_word = %d); ", word.GetId()) +
                    String.format("UPDATE HSK_WORDS_STATES SET state = %d WHERE id_word = %d;", known, word.GetId());

            int rowsAffected = connection.prepareStatement(sql).executeUpdate();
            System.out.println(rowsAffected + " row(s) affected");

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int GetWordsMet() { return this.wordsMet; }
    public int GetWordsKnown() { return this.wordsKnown; }
    public int GetWordsForgotten() { return this.wordsForgotten; }
}
