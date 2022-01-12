package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.Decks;

import java.util.Random;

public class EasyBot extends Player implements AI {
    private Random random = new Random();
    private String gameNick;

    public EasyBot(String nick) {
        super("EasyBot");
        gameNick = nick;
    }

    public EasyBot() {
        super("EasyBot");
        gameNick = "EasyBot";
    }

    public boolean wantToDrawCard() {
        if (getTotalPoints() >= 21) {
            setEnded();
            return false;
        }
        if (isEnded()) {
            return false;
        }
        if (random.nextFloat() < 0.5f) {
            setEnded();
            return false;
        } else {
            return true;
        }
    }

    public String getGameNick() {
        return gameNick;
    }

    public void setGameNick(String gameNick) {
        this.gameNick = gameNick;
    }
}
