package database;

import applicationLogic.Hasher;
import gameLogic.cards.CardValues;
import applicationLogic.Statistics;
import java.sql.*;
import java.util.ArrayList;

public class SqliteDB implements Database {
    private Connection connection = null;
    private static final String fileName = "test.db";
    private static final String users = "users";
    private static final String nick = "nick";
    private static final String password = "password";
    private static final String winRate = "winRate";
    private static final String numberOfGames = "numberOfGames";
    private static final String gameTime = "gameTime";
    private static final String twoNo = "twoNo";
    private static final String threeNo = "threeNo";
    private static final String fourNo = "fourNo";
    private static final String fiveNo = "fiveNo";
    private static final String sixNo = "sixNo";
    private static final String sevenNo = "sevenNo";
    private static final String eightNo = "eightNo";
    private static final String nineNo = "nineNo";
    private static final String tenNo = "tenNo";
    private static final String jackNo = "jackNo";
    private static final String queenNo = "queenNo";
    private static final String kingNo = "kingNo";
    private static final String aceNo = "aceNo";


    public SqliteDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            openConnection();
            buildDB();
            fillForTests();
            closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + fileName);
            System.out.println("Connected to DB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection to DB closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildDB() {
        try (Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS " + SqliteDB.users + " (\n" +
                    "\t" + SqliteDB.nick + "\tTEXT NOT NULL UNIQUE,\n" +
                    "\t" + SqliteDB.password + "\tTEXT NOT NULL,\n" +
                    "\t" + SqliteDB.winRate + "\tNUMERIC,\n" +
                    "\t" + SqliteDB.numberOfGames + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.gameTime + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.twoNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.threeNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.fourNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.fiveNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.sixNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.sevenNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.eightNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.nineNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.tenNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.jackNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.queenNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.kingNo + "\tINTEGER DEFAULT 0,\n" +
                    "\t" + SqliteDB.aceNo + "\tINTEGER DEFAULT 0\n" +
                    ")");
            makeBots();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void fillForTests() throws SQLException {
            Statistics tmp = new Statistics();

            if (isNickAvailable("jack")) {
                register("jack", "frost");
                tmp.setCardHistory(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
                tmp.setGameTime(1234);
                tmp.setWinRate(99.99f);
                tmp.setNumberOfGames(465);
                setStatistics("jack", tmp);
            }

            if (isNickAvailable("tony")) {
                register("tony", "stark");
                tmp.setCardHistory(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
                tmp.setGameTime(9999);
                tmp.setWinRate(100f);
                tmp.setNumberOfGames(42);
                setStatistics("tony", tmp);
            }

            if (isNickAvailable("pope")) {
                register("pope", "jp2");
                tmp.setCardHistory(2, 22, 222, 2222, 22222, 2, 22, 2222, 222, 2, 22, 22, 2);
                tmp.setGameTime(2137);
                tmp.setWinRate(21.37f);
                tmp.setNumberOfGames(7312);
                setStatistics("pope", tmp);
            }
    }

    public void makeBots() {
        try (Statement statement = connection.createStatement()) {

            if (isNickAvailable("Dealer")) {
                statement.execute("INSERT INTO " + SqliteDB.users + " ('" + SqliteDB.nick + "', '" + SqliteDB.password + "') VALUES('Dealer', 'bot');");
            }
            if (isNickAvailable("EasyBot")) {
                statement.execute("INSERT INTO " + SqliteDB.users + " ('" + SqliteDB.nick + "', '" + SqliteDB.password + "') VALUES('EasyBot', 'bot');");
            }
            if (isNickAvailable("MediumBot")) {
                statement.execute("INSERT INTO " + SqliteDB.users + " ('" + SqliteDB.nick + "', '" + SqliteDB.password + "') VALUES('MediumBot', 'bot');");
            }
            if (isNickAvailable("HardBot")) {
                statement.execute("INSERT INTO " + SqliteDB.users + " ('" + SqliteDB.nick + "', '" + SqliteDB.password + "') VALUES('HardBot', 'bot');");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private boolean isNickAvailable(String nick) {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT " + SqliteDB.nick + " FROM " + SqliteDB.users + " WHERE " + SqliteDB.nick + " = '" + nick + "'");
            return !resultSet.next();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public void register(String nick, String password) throws SQLException {
        if (!isNickAvailable(nick)) {
            throw new SQLException("Error: user with nick " + nick + " already exists");
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO " + SqliteDB.users + "('nick', 'password') " +
                    "VALUES('" + nick + "', '" + Hasher.hashPassword(password) + "');");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean login(String nick, String password) throws SQLException {
        if (isNickAvailable(nick)) {
            throw new SQLException("No such user: " + nick);
        }
        return Hasher.hashPassword(password).equals(getPassword(nick));
    }

    public void deleteUser(String nick) throws SQLException{
        if (!isNickAvailable(nick)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM " + SqliteDB.users + " WHERE " + SqliteDB.nick + " = '" + nick + "';");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            throw new SQLException("No such user: " + nick);
        }
    }

    public ArrayList<String> getAllNicks() {
        ArrayList<String> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT " + nick + " FROM " + users + " ORDER BY " + winRate + " DESC");

            while(resultSet.next()) {
                list.add(resultSet.getString(nick));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    private String getPassword(String nick) throws SQLException {
        if (isNickAvailable(nick)) {
            throw new SQLException("No such user: " + nick);
        }
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + SqliteDB.users + " WHERE " + SqliteDB.nick + " = '" + nick + "'");
            return resultSet.getString(password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void changePassword(String nick, String password) throws SQLException {
        if (isNickAvailable(nick)) {
            throw new SQLException("No such user: " + nick);
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("UPDATE " + SqliteDB.users + " SET " + SqliteDB.password + " = '" + Hasher.hashPassword(password) + "' WHERE " + SqliteDB.nick + " = '" + nick + "'");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Statistics getStatistics(String nick) throws SQLException {
        Statistics statistics = new Statistics();
        if (isNickAvailable(nick)) {
            throw new SQLException("No such user: " + nick);
        }
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " +  SqliteDB.users + " WHERE " +  SqliteDB.nick + " = '" + nick + "';");
            statistics.setWinRate(resultSet.getFloat( SqliteDB.winRate));
            statistics.setNumberOfGames(resultSet.getInt( SqliteDB.numberOfGames));
            statistics.setGameTime(resultSet.getInt( SqliteDB.gameTime));
            statistics.setCardHistory(
                    resultSet.getInt( SqliteDB.twoNo),
                    resultSet.getInt( SqliteDB.threeNo),
                    resultSet.getInt( SqliteDB.fourNo),
                    resultSet.getInt( SqliteDB.fiveNo),
                    resultSet.getInt( SqliteDB.sixNo),
                    resultSet.getInt( SqliteDB.sevenNo),
                    resultSet.getInt( SqliteDB.eightNo),
                    resultSet.getInt( SqliteDB.nineNo),
                    resultSet.getInt( SqliteDB.tenNo),
                    resultSet.getInt( SqliteDB.jackNo),
                    resultSet.getInt( SqliteDB.queenNo),
                    resultSet.getInt( SqliteDB.kingNo),
                    resultSet.getInt( SqliteDB.aceNo));
            return statistics;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void setStatistics(String nick, Statistics statistics) throws SQLException {
        if (isNickAvailable(nick)) {
            throw new SQLException("No such user: " + nick);
        }
        try (Statement statement = connection.createStatement()) {
            statement.execute("UPDATE " + SqliteDB.users + " SET " +
                    SqliteDB.winRate + " = " + statistics.getWinRate() + ", " +
                    SqliteDB.numberOfGames + " = " + statistics.getNumberOfGames() + ", " +
                    SqliteDB.gameTime + " = " + statistics.getGameTime() + ", " +
                    SqliteDB.twoNo + " = " + statistics.getCardOccurrence(CardValues.TWO) + ", " +
                    SqliteDB.threeNo + " = " + statistics.getCardOccurrence(CardValues.THREE) + ", " +
                    SqliteDB.fourNo + " = " + statistics.getCardOccurrence(CardValues.FOUR) + ", " +
                    SqliteDB.fiveNo + " = " + statistics.getCardOccurrence(CardValues.FIVE) + ", " +
                    SqliteDB.sixNo + " = " + statistics.getCardOccurrence(CardValues.SIX) + ", " +
                    SqliteDB.sevenNo + " = " + statistics.getCardOccurrence(CardValues.SEVEN) + ", " +
                    SqliteDB.eightNo + " = " + statistics.getCardOccurrence(CardValues.EIGHT) + ", " +
                    SqliteDB.nineNo + " = " + statistics.getCardOccurrence(CardValues.NINE) + ", " +
                    SqliteDB.tenNo + " = " + statistics.getCardOccurrence(CardValues.TEN) + ", " +
                    SqliteDB.jackNo + " = " + statistics.getCardOccurrence(CardValues.JACK) + ", " +
                    SqliteDB.queenNo + " = " + statistics.getCardOccurrence(CardValues.QUEEN) + ", " +
                    SqliteDB.kingNo + " = " + statistics.getCardOccurrence(CardValues.KING) + ", " +
                    SqliteDB.aceNo + " = " + (statistics.getCardOccurrence(CardValues.ACE11) +
                        statistics.getCardOccurrence(CardValues.ACE1)) +
                    " WHERE " + SqliteDB.nick + " = \"" + nick + "\";"
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
