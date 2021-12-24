package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.Decks;

import java.util.Random;

public class EasyBot extends Player implements AI {
    private Decks deck;
    private Random random = new Random();

    public EasyBot(String nick, Decks deck) {
        super(nick);
        this.deck = deck;
    }

    public boolean wantToDrawCard() {
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
}
