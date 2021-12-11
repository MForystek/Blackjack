package gameLogic;

public class GameManager extends Thread {
    private Game game;
    private GameParameters gameParameters;

    public GameManager() {
        gameParameters = new GameParameters();
    }

    public boolean isReadyToBePlayed() {
        return gameParameters.isComplete();
    }

    @Override
    public synchronized void start() {
        if (isReadyToBePlayed()) {
            game = new Game(
                    gameParameters.getDatabase(),
                    gameParameters.getNumberOfDecks(),
                    gameParameters.getNumberOfPlayers(),
                    gameParameters.getGameMode()
            );
            super.start();
        }
    }

    @Override
    public void run() {
        game.startGame();
        while(game.isEnded()) {
            game.makeTurn();
        }
        game.setWinners();
    }

    public GameParameters getGameParameters() {
        return gameParameters;
    }
}
