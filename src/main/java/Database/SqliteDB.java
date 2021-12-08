package Database;
import java.sql.*;

public class SqliteDB implements Database {
    Connection connection = null;
    Statement statement = null;

    public SqliteDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sqlitedb.db");
            System.out.println("Connected to DB");
        } catch (Exception e) {
            System.out.println("Error connecting to DB: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection to DB closed");
        } catch (Exception e) {
            System.out.println("Error closing connection to DB: " + e.getMessage());
        }
    }

    public void printUsers() {
        try {
            this.statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String nick = resultSet.getString("nick");
                String password = resultSet.getString("password");

                System.out.println(id + nick + password);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
