package Database;
import logic.Statistics;

import java.sql.*;

public class SqliteDB implements Database {
    Connection connection = null;
    String filName = "test.db";
    String users = "users";
    String nick = "nick";
    String password = "password";
    String winRate = "winRate";
    String numberOfGames = "numberOfGames";
    String gameTime = "gameTime";
    String twoNo = "twoNo";
    String threeNo = "threeNo";
    String fourNo = "fourNo";
    String fiveNo = "fiveNo";
    String sixNo = "sixNo";
    String sevenNo = "sevenNo";
    String eightNo = "eightNo";
    String nineNo = "nineNo";
    String tenNo = "tenNo";
    String jackNo = "jackNo";
    String queenNo = "queenNo";
    String kingNo = "kingNo";
    String aceNo = "aceNo";


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
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + this.users);
            statistics.setWinRate(resultSet.getFloat(this.winRate));
            statistics.setNumberOfGames(resultSet.getInt(this.numberOfGames));
            statistics.setGameTime(resultSet.getInt(this.gameTime));
            statistics.setCardHistory(
                    resultSet.getInt(this.twoNo),
                    resultSet.getInt(this.threeNo),
                    resultSet.getInt(this.fourNo),
                    resultSet.getInt(this.fiveNo),
                    resultSet.getInt(this.sixNo),
                    resultSet.getInt(this.sevenNo),
                    resultSet.getInt(this.eightNo),
                    resultSet.getInt(this.nineNo),
                    resultSet.getInt(this.tenNo),
                    resultSet.getInt(this.jackNo),
                    resultSet.getInt(this.queenNo),
                    resultSet.getInt(this.kingNo),
                    resultSet.getInt(this.aceNo));
            return statistics;
        } catch (Exception e) {
            throw e;
        }
    }

    public void setStatistics(String nick, Statistics statistics) throws SQLException {
        if (!isNickAvailable(nick)) {
            throw new SQLException("No such user");
        }
//        try (Statement statement = connection.createStatement()) {
//            statement.execute("INSERT INTO users ('nick', 'password') " +
//                    "VALUES('" + nick + "', '" + password + "');");
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }

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
            statement.execute("CREATE TABLE IF NOT EXISTS " + this.users + " (\n" +
                    "\t" + this.nick + "\tTEXT NOT NULL UNIQUE,\n" +
                    "\t" + this.password + "\tTEXT NOT NULL,\n" +
                    "\t" + this.winRate + "\tNUMERIC,\n" +
                    "\t" + this.numberOfGames + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.gameTime + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.twoNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.threeNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.fourNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.fiveNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.sixNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.sevenNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.eightNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.nineNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.tenNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.jackNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.queenNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.kingNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + this.aceNo + "\tINTEGER DEFAULT 0\n" +
                    ")");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void fillForTests() {
        try (Statement statement = connection.createStatement()) {
            if (isNickAvailable("jack")) {
                statement.execute("INSERT INTO users ('nick', 'password', 'winRate', 'numberOfGames', 'gameTime', 'twoNo', 'threeNo', 'fourNo', 'fiveNo', 'sixNo', 'sevenNo', 'eightNo', 'nineNo', 'tenNo', 'jackNo', 'queenNo', 'kingNo', 'aceNo') " +
                        "VALUES('jack', 'mandera', '99.99', '10000', '9090', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '15');");
            }
            if (isNickAvailable("ironMan")) {
                statement.execute("INSERT INTO users ('nick', 'password', 'winRate', 'numberOfGames', 'gameTime', 'twoNo', 'threeNo', 'fourNo', 'fiveNo', 'sixNo', 'sevenNo', 'eightNo', 'nineNo', 'tenNo', 'jackNo', 'queenNo', 'kingNo', 'aceNo') " +
                        "VALUES('ironMan', 'iamironman', '100', '10000', '9090', '7898', '78', '3', '345', '5', '0', '56', '6', '12', '7', '187', '634', '1486');");
            }
            if (isNickAvailable("szymon")) {
                statement.execute("INSERT INTO users ('nick', 'password', 'winRate', 'numberOfGames', 'gameTime', 'twoNo', 'threeNo', 'fourNo', 'fiveNo', 'sixNo', 'sevenNo', 'eightNo', 'nineNo', 'tenNo', 'jackNo', 'queenNo', 'kingNo', 'aceNo') " +
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
