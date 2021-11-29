package logic;

import java.util.HashMap;
import java.util.Map;

public class Deck {
    private Map<CardColors, Map<CardValues, Card>> deck;

    public Deck() {
        fillDeck();
    }

    public void fillDeck() {
        deck = new HashMap<>();
        for (CardColors cardColor : CardColors.values()) {
            if (cardColor != CardColors.NULL) {
                deck.put(cardColor, new HashMap<>());
                for (CardValues cardValue : CardValues.values()) {
                    if (cardValue != CardValues.NULL) {
                        deck.get(cardColor).put(cardValue, new Card(cardColor, cardValue));
                    }
                }
            }
        }
    }

    public boolean isEmpty() {
        return getAmountOfCardsInDeck() > 0;
    }

    public int getAmountOfCardsInDeck() {
        return (int) deck.values().stream().mapToLong(collection -> collection.values().size()).sum();
    }

    public Card takeCardFromDeck(CardColors cardColor, CardValues cardValue) {
        if (isCardInDeck(cardColor, cardValue)) {
            return deck.get(cardColor).remove(cardValue);
        } else {
            return Card.getNullCard();
        }
    }

    public boolean isCardInDeck(CardColors cardColor, CardValues cardValue) {
        if (cardColor == CardColors.NULL) {
            return false;
        }
        return deck.get(cardColor).containsKey(cardValue);
    }
}
