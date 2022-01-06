package gameLogic;

import database.Database;

public class GameManager extends Thread {
    private Game game;

    public GameManager(Database database, int numberOfPlayers, int numberOfDecks, GameModes gameMode) {
        game = Game.createGame(database, numberOfPlayers, numberOfDecks, gameMode);
    }

    @Override
    public synchronized void start() {
        if (game.isReadyToBePlayed()) {
            super.start();
        }
    }

    @Override
    public void run() {
        game.startGame();
        while(!game.isEnded()) {
            game.makeTurn();
        }
        game.setWinners();
    }

    public Drawator getDrawator() {
        return game;
    }

    protected void setDebug() {
        game.setDebug();
    }
}
