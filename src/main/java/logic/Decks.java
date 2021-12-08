package logic;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

public class Decks {
    public static final int NUMBER_OF_CARDS_IN_ONE_DECK = 52;

    private Stack<Card> decks;
    private int numberOfDecks;

    public Decks(int numberOfDecks) {
        if (numberOfDecks < 1 || numberOfDecks > 4) {
            throw new IllegalArgumentException("Number of decks should be between 1 and 4 inclusively");
        }
        this.numberOfDecks = numberOfDecks;
        fillDecksWithCards();
        shuffle();
    }

    public void fillDecksWithCards() {
        decks = new Stack<>();
        for (int i = 0; i < numberOfDecks; i++) {
            for (CardColors cardColor : CardColors.values()) {
                for (CardValues cardValue : CardValues.values()) {
                    decks.push(new Card(cardColor, cardValue));
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(decks);
    }

    public int getDecksMaxSize() {
        return NUMBER_OF_CARDS_IN_ONE_DECK * numberOfDecks;
    }

    public boolean isEmpty() {
        return decks.isEmpty();
    }

    public Card takeCardFromTheTop() {
        isThereAnyCardsLeft();
        return decks.pop();
    }

    private void isThereAnyCardsLeft() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
    }

    public Card lookAtNextCardFromTheTop() {
        isThereAnyCardsLeft();
        return decks.peek();
    }

    protected void setDecks(Stack <Card> cards){
        this.decks = cards;
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }
}
