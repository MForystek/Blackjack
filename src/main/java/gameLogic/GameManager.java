package gameLogic;

import gui.GameWindow;

import java.util.concurrent.atomic.AtomicInteger;

public class GameManager extends Thread {
    private Game game;
    private GameWindow gameWindow;

    public GameManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        game = HalfCasinoGame.createGame();
    }

    @Override
    public synchronized void start() {
        while (!game.isReadyToBePlayed()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
        super.start();
    }

    @Override
    public void run() {
        game.startPlaying();
        gameWindow.showBeginningResults();
        while(!game.isEnded()) {
            game.makeTurn();
        }
        game.endGame();
        gameWindow.showGameResults();
    }

    public TurnChoice getTurnChoice() {
        return (TurnChoice) game;
    }

    public AtomicInteger getCountdown() {
        return game.getCountdown();
    }

    public int getIndexOfCurrentPlayer() {
        return game.getIndexOfCurrentPlayer();
    }

    public boolean isEnded() {
        return game.isEnded();
    }

    public boolean isDealerTurn(){
        return game.isDealerTurn();
    }

    public boolean isHumanTurn(){
        return game.isHumanTurn();
    }

    //For tests
    protected void setDebug() {
        ((HalfCasinoGame)game).setDebug();
    }
}
