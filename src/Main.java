import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Menu menuWindow = new Menu();

        List<Word> wordList = new ArrayList<Word>();

        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:db/HSK.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"HSK 2\"");

            while (resultSet.next()) {
                Word word = new Word();
                word.SetId(resultSet.getInt("id"));
                word.SetTraditional(resultSet.getString("traditional"));
                word.SetSimplified(resultSet.getString("simplified"));
                word.SetPinyin(resultSet.getString("pinyin"));
                word.SetMeaning(resultSet.getString("meaning"));
                wordList.add(word);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int randomIndex = (int)(Math.random() * wordList.size());
        System.out.println(wordList.get(randomIndex).GetData());

        System.out.println("看字");
    }
}