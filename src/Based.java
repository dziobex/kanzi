import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Handling base-related stuff

public class Based {
    static Based baseObject = null;

    int wordsMet, wordsKnown, wordsForgotten;
    int hskLevel;

    private Based() {
        System.out.println("BASE class was created!");
        wordsMet = wordsKnown = wordsForgotten = 0;
        UpdateStats();
    }

    public static Based getInstance() {
        if ( baseObject == null )
            baseObject = new Based();
        return baseObject;
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

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Word GetRandomWord(String stateIn) {
        Word word = null;

        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:db/HSK.db");
            Statement statement = connection.createStatement();
            String query =
                    String.format("SELECT * FROM HSK_WORDS WHERE LEVEL <= %d AND id ", this.hskLevel) +
(stateIn == "" ?    String.format("NOT IN (SELECT id_word FROM HSK_WORDS_STATES)") :
                    String.format("IN (SELECT id_word FROM HSK_WORDS_STATES WHERE state IN (%s)) ", stateIn)) +
                    "ORDER BY RANDOM() LIMIT 1";
            System.out.println("State IN: " + stateIn + "\nQUERY: " + query);

            ResultSet resultSet = statement.executeQuery(query);

            if ( !resultSet.isBeforeFirst() )
                return null;    // no data was found

            word = new Word();
            word.SetId(resultSet.getInt("id"));
            word.SetTraditional(resultSet.getString("traditional"));
            word.SetSimplified(resultSet.getString("simplified"));
            word.SetPinyin(resultSet.getString("pinyin"));
            word.SetMeaning(resultSet.getString("meaning"));
            word.SetLevel(resultSet.getInt("LEVEL"));

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return word;
    }

    void SaveTheWord(Word word, int known) {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:db/HSK.db");

            System.out.println(String.valueOf(known) + ", " + String.valueOf(word.GetId()));

            String sql =
                    String.format("INSERT INTO HSK_WORDS_STATES (id_word, state) SELECT %d, %d ", word.GetId(), known) +
                    String.format("WHERE NOT EXISTS (SELECT 1 FROM HSK_WORDS_STATES WHERE id_word = %d);", word.GetId());

            connection.prepareStatement(sql).executeUpdate();
            sql = String.format("UPDATE HSK_WORDS_STATES SET state = %d WHERE id_word = %d", known, word.GetId());
            connection.prepareStatement(sql).executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Reset() {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:db/HSK.db");
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM HSK_WORDS_STATES");

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int GetWordsMet() { return this.wordsMet; }
    public int GetWordsKnown() { return this.wordsKnown; }
    public int GetWordsForgotten() { return this.wordsForgotten; }
}
