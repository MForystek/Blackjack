package gameLogic;

import Database.Database;

public class GameParameters {
    private int numberOfPlayers;
    private int numberOfDecks;
    private Database database;

    public boolean isComplete() {
        return checkNumberOfPlayers() && checkNumberOfDecks() && checkDatabase();
    }

    private boolean checkNumberOfPlayers() {
        return numberOfPlayers >= 1 && numberOfPlayers <= 4;
    }

    private boolean checkNumberOfDecks() {
        return numberOfDecks >= 1 && numberOfDecks <= 8;
    }

    private boolean checkDatabase() {
        return database != null;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public void setNumberOfDecks(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

}
