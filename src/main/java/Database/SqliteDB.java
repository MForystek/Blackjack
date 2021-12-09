package Database;
import logic.Statistics;

import java.sql.*;

public class SqliteDB implements Database {
    Connection connection = null;
    String filName = "test.db";

    public SqliteDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            openConnection();
            buildDB();
            closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void putNewUser(String nick, String password) throws SQLException {
        if (!isNickAvailable(nick)) {
            throw new SQLException("Error: user with this nick already exists");
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO users ('nick', 'password') " +
                    "VALUES('" + nick + "', '" + password + "');");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteUser(String nick) throws SQLException{
        if (!isNickAvailable(nick)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM users WHERE nick = '" + nick + "';");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            throw new SQLException("No such user");
        }
    }

    public String getPassword(String nick) throws SQLException {
        if (!isNickAvailable(nick)) {
            throw new SQLException("No such user");
        }
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            return resultSet.getString("password");
        } catch (Exception e) {
            throw e;
        }
    }

    public Statistics getStatistics(String nick) throws SQLException {
        Statistics statistics = new Statistics();
        if (!isNickAvailable(nick)) {
            throw new SQLException("No such user");
        }
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            statistics.setWinRate(resultSet.getFloat("winRate"));
            statistics.setNumberOfGames(resultSet.getInt("numberOfGames"));
            statistics.setGameTime(resultSet.getInt("gameTime"));
            statistics.setCardHistory(
                    resultSet.getInt("twoNo"),
                    resultSet.getInt("threeNo"),
                    resultSet.getInt("fourNo"),
                    resultSet.getInt("fiveNo"),
                    resultSet.getInt("sixNo"),
                    resultSet.getInt("sevenNo"),
                    resultSet.getInt("eightNo"),
                    resultSet.getInt("nineNo"),
                    resultSet.getInt("tenNo"),
                    resultSet.getInt("jackNo"),
                    resultSet.getInt("queenNo"),
                    resultSet.getInt("kingNo"),
                    resultSet.getInt("aceNo"));
            return statistics;
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean isNickAvailable(String nick) {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT nick FROM users WHERE nick = '" + nick + "'");
            return !resultSet.next();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public void buildDB() {
        try (Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS \"users\" (\n" +
                    "\t\"nick\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"password\"\tTEXT NOT NULL,\n" +
                    "\t\"winRate\"\tNUMERIC,\n" +
                    "\t\"numberOfGames\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"gameTime\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"twoNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"threeNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"fourNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"fiveNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"sixNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"sevenNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"eightNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"nineNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"tenNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"jackNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"queenNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"kingNo\"\tINTEGER DEFAULT 0,\n" +
                    "\t\"aceNo\"\tINTEGER DEFAULT 0\n" +
                    ")");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void fillForTests() {
        try (Statement statement = connection.createStatement()) {
            if (isNickAvailable("jack")) {
                statement.execute("INSERT INTO users ('nick', 'password', 'winRate', 'numOfGames', 'totalTime', 'twoNo', 'threeNo', 'fourNo', 'fiveNo', 'sixNo', 'sevenNo', 'eightNo', 'nineNo', 'tenNo', 'jackNo', 'queenNo', 'kingNo', 'aceNo') " +
                        "VALUES('jack', 'mandera', '99.99', '10000', '9090', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '15');");
            }
            if (isNickAvailable("ironMan")) {
                statement.execute("INSERT INTO users ('nick', 'password', 'winRate', 'numOfGames', 'totalTime', 'twoNo', 'threeNo', 'fourNo', 'fiveNo', 'sixNo', 'sevenNo', 'eightNo', 'nineNo', 'tenNo', 'jackNo', 'queenNo', 'kingNo', 'aceNo') " +
                        "VALUES('ironMan', 'iamironman', '100', '10000', '9090', '7898', '78', '3', '345', '5', '0', '56', '6', '12', '7', '187', '634', '1486');");
            }
            if (isNickAvailable("szymon")) {
                statement.execute("INSERT INTO users ('nick', 'password', 'winRate', 'numOfGames', 'totalTime', 'twoNo', 'threeNo', 'fourNo', 'fiveNo', 'sixNo', 'sevenNo', 'eightNo', 'nineNo', 'tenNo', 'jackNo', 'queenNo', 'kingNo', 'aceNo') " +
                        "VALUES('szymon', 'hasło', '50', '12', '4', '29', '39', '49', '59', '99', '79', '89', '99', '109', '119', '129', '139', '159');");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filName);
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

    private void printUsers() {
        try (Statement statement = connection.createStatement()){
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
