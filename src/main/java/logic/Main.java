package logic;

import Database.*;

public class Main {
    public static void main(String[] args) {
        SqliteDB db = new SqliteDB();
        db.openConnection();
        db.printUsers();
        db.closeConnection();
    }
}
