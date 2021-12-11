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

    //TODO Możnaby to rozbić na funkcję sprawdzającą czy skończył grę lub czy deck jest pusty
    //TODO oraz kolejną która tylko bierze kartę z i jest typu void
    public boolean pickCard() {
        if(deck.isEmpty() || isEnded()) {
            setEnded();
            return false;
        }

        if (random.nextFloat() < 0.5f) {
            setEnded();
            return false;
        } else {
            return true;
        }
    }

    public int getCardsValue() {
        if(getCards() == null) {
            return 0;
        }

        int value = 0;
        for (Card card : getCards()) {
            value = value + card.getValue();
        }
        return value;
    }
}
