package gameLogic;

import java.util.Map;

public interface Game {
    boolean isReadyToBePlayed();
    boolean isEnded();
    void startPlaying();
    void makeTurn();

    void endGame();
    Map<String, Object> getBeginningResults();
}
