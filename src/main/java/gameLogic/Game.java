package gameLogic;

import java.util.concurrent.atomic.AtomicInteger;

public interface Game {
    boolean isReadyToBePlayed();
    boolean isEnded();
    void startPlaying();
    void makeTurn();

    void endGame();
    AtomicInteger getCountdown();
    int getIndexOfCurrentPlayer();
    boolean isDealerTurn();
    boolean isHumanTurn();
}
