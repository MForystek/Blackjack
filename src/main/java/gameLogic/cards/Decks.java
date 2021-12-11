package gameLogic.cards;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

public class Decks {
    public static final int NUMBER_OF_CARDS_IN_ONE_DECK = 52;
    public static final int MAX_NUMBER_OF_DECKS = 8;
    public static final int MIN_NUMBER_OF_DECKS = 1;

    private Stack<Card> decks;
    private int numberOfDecks;

    public Decks(int numberOfDecks) {
        if (numberOfDecks < MIN_NUMBER_OF_DECKS || numberOfDecks > MAX_NUMBER_OF_DECKS) {
            throw new IllegalArgumentException("Number of decks should be between 1 and 4 inclusively");
        }
        this.numberOfDecks = numberOfDecks;
        fillDecksWithCards();
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
        Collections.shuffle(decks);
    }

    public int getDecksMaxSize() {
        return NUMBER_OF_CARDS_IN_ONE_DECK * numberOfDecks;
    }

    public boolean isEmpty() {
        return decks.isEmpty();
    }

    /**
     * @return returns top Card from Decks and removes it from Decks
     */
    public Card takeNextCard() {
        isThereAnyCardsLeft();
        return decks.pop();
    }

    private void isThereAnyCardsLeft() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
    }

    /**
     * @return returns top Card from Decks without removing it from Decks
     */
    public Card getNextCard() {
        isThereAnyCardsLeft();
        return decks.peek();
    }

    /**
     * @return returns int value of the next Card
     */
    public int getValueOfNextCard() {
        return getNextCard().getValue();
    }

    public CardValues getCardValueOfNextCard() {
        return getNextCard().getCardValue();
    }

    public CardColors getCardColorOfNextCard() {
        return getNextCard().getCardColor();
    }

    public void setDecks(Stack<Card> cards){
        this.decks = cards;
    }

    public int getNumberOfDecks() {
        return numberOfDecks;
    }
}
