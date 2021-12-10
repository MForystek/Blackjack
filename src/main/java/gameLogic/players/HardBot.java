package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.Decks;

import java.util.EmptyStackException;

public class HardBot extends Player implements AI {
    private static final int MAX_SCORE = 21;

    private Decks deck;

    public HardBot(String nick, Decks deck) {
        super(nick);
        this.deck = deck;
    }

    //TODO tutaj to samo co w klasie EasyBot
    public boolean pickCard() {
        if (deck.isEmpty() || isEnded()) {
            setIsEnded(true);
            return false;
        }

        int score = getCardsValue();
        try {
            int cardValue = deck.getValueOfNextCard();

            if (Math.abs(MAX_SCORE - (score + cardValue)) < Math.abs(MAX_SCORE - score)) {
                addCard(deck.takeNextCard());
                return true;
            } else {
                setIsEnded(true);
                return false;
            }
        } catch (EmptyStackException e) {
            return false;
        }
    }

    public int getCardsValue() {
        if (getCards() == null) {
            return 0;
        }

        int value = 0;
        for (Card card : getCards()) {
            value = value + card.getValue();
        }
        return value;
    }
}
