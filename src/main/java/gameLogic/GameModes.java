package gameLogic;

public enum GameModes {
    EASY(5),
    MEDIUM(10),
    HARD(15);

    private int timeForMove;

    GameModes(int timeForMove) {
        this.timeForMove = timeForMove;
    }

    public int getTimeForMove() {
        return timeForMove;
    }
}
