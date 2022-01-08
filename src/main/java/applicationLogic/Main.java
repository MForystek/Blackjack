package applicationLogic;

import database.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SqliteDB db = new SqliteDB();
        db.openConnection();
        ArrayList<String> list = db.getNicks();
        for (String s : list) {
            System.out.println(s);
        }
        db.closeConnection();
    }
}
