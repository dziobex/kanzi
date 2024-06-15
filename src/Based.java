import java.net.URL;
import java.sql.*;

// Handling base-related stuff
// SINGLETON

public class Based {
    static Based baseObject = null;

    public static Based getInstance() {
        if ( baseObject == null )
            baseObject = new Based();
        return baseObject;
    }

    int wordsMet, wordsKnown, wordsForgotten, hskLevel;

    private Based() {
        System.out.println("BASE class was created!");
        wordsMet = wordsKnown = wordsForgotten = 0;
        updateStats();
    }

    public void updateStats() {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db/HSK.db");
                 Statement statement = connection.createStatement()) {

                wordsMet = getCount(statement, "SELECT COUNT(*) as count FROM HSK_WORDS_STATES");
                wordsKnown = getCount(statement, "SELECT COUNT(*) as count FROM HSK_WORDS_STATES WHERE state = 2");
                wordsForgotten = getCount(statement, "SELECT COUNT(*) as count FROM HSK_WORDS_STATES WHERE state = 0");

                hskLevel = Math.floorDiv(wordsMet, 150) + 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // returns row "count" from each given query
    int getCount(Statement statement, String query) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery(query)) {
            return resultSet.getInt("count");
        }
    }

    public Word getRandomWord(String stateIn) {
        Word word = null;

        // random word from the database
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db/HSK.db");
                 Statement statement = connection.createStatement()) {
                String query =
                        String.format("SELECT * FROM HSK_WORDS WHERE LEVEL <= %d AND id ", this.hskLevel) +
                                (stateIn == "" ? String.format("NOT IN (SELECT id_word FROM HSK_WORDS_STATES)") :
                                        String.format("IN (SELECT id_word FROM HSK_WORDS_STATES WHERE state IN (%s)) ", stateIn)) +
                                "ORDER BY RANDOM() LIMIT 1";
                // System.out.println("State IN: " + stateIn + "\nQUERY: " + query);

                ResultSet resultSet = statement.executeQuery(query);

                if (!resultSet.isBeforeFirst())
                    return null;    // no data was found

                // setting up the word
                word = new Word();
                word.setId(resultSet.getInt("id"));
                word.setTraditional(resultSet.getString("traditional"));
                word.setSimplified(resultSet.getString("simplified"));
                word.setPinyin(resultSet.getString("pinyin"));
                word.setMeaning(resultSet.getString("meaning"));
                word.setLevel(resultSet.getInt("LEVEL"));

                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return word;
    }

    public void saveTheWord(Word word, int known) {
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db/HSK.db")) {
                // insert into the database info about the word (it was met, but how?)
                String sql =
                        String.format("INSERT INTO HSK_WORDS_STATES (id_word, state) SELECT %d, %d ", word.getId(), known) +
                                String.format("WHERE NOT EXISTS (SELECT 1 FROM HSK_WORDS_STATES WHERE id_word = %d);", word.getId());
                connection.prepareStatement(sql).executeUpdate();
                sql = String.format("UPDATE HSK_WORDS_STATES SET state = %d WHERE id_word = %d", known, word.getId());
                connection.prepareStatement(sql).executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        // THROW AWAY THE DATA!!!!
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db/HSK.db");
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM HSK_WORDS_STATES");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getWordsMet() { return this.wordsMet; }
    public int getWordsKnown() { return this.wordsKnown; }
    public int getWordsForgotten() { return this.wordsForgotten; }
}