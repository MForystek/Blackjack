package gameLogic;

import database.Database;
import gui.GameWindow;

import java.util.Map;

public class GameManager extends Thread {
    private Game game;
    private GameWindow gameWindow;

    public GameManager(Database database, int numberOfPlayers, int numberOfDecks, GameModes gameMode) {
        game = HalfCasinoGame.createGame(database, numberOfPlayers, numberOfDecks, gameMode);
    }

    @Override
    public synchronized void start() {
        if (game.isReadyToBePlayed()) {
            super.start();
        }
    }

    @Override
    public void run() {
        game.startPlaying();
        gameWindow.showBeginningResults();
        while(!game.isEnded()) {
            game.makeTurn();
            gameWindow.showTurnResults();
        }
        game.endGame();
        gameWindow.showGameResults();
    }

    public TurnChoice getTurnChoice() {
        return (TurnChoice) game;
    }

    public Map<String, Object> getReferences() {
        return game.getBeginningResults();
    }

    protected void setDebug() {
        ((HalfCasinoGame)game).setDebug();
    }
}
