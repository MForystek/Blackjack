package Database;

import applicationLogic.Statistics;

import java.sql.SQLException;

public interface Database {
    void buildDB();
    void openConnection();
    void closeConnection();
    void register(String nick, String password) throws SQLException;
    boolean login(String nick, String password) throws SQLException;
    void deleteUser(String nick) throws SQLException;
    String getPassword(String nick) throws SQLException;
    Statistics getStatistics(String nick) throws SQLException;
    void setStatistics(String nick, Statistics statistics) throws SQLException;
}
