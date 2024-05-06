import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Based based = new Based();
        based.UpdateStats();

        Menu menuWindow = new Menu(based);

        System.out.println("看字");
    }
}