package gameLogic.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static gameLogic.cards.Decks.*;
import static org.junit.jupiter.api.Assertions.*;

class DecksTest {
    private Decks decks;

    @BeforeEach
    void before() {
        decks = new Decks(MAX_NUMBER_OF_DECKS);
    }

    @Test
    void areCardsShuffled() {
        int cardsShuffled = 0;

        for (int i = 0; i < decks.getNumberOfDecks(); i++) {
            for (CardColors cardColor : CardColors.values()) {
                for (CardValues cardValue : CardValues.values()) {
                    if (!cardValue.equals(CardValues.ACE1)) {
                        Card nextCard = decks.takeNextCard();
                        if (!nextCard.equals(new Card(cardColor, cardValue))) {
                            cardsShuffled++;
                        }
                    }
                }
            }
        }

        //0.75 is arbitrary threshold I assumed would be right
        assertTrue(cardsShuffled >= decks.getDecksMaxSize()*0.75);
    }

    @Test
    void areAllCardsPresentTest() {
        List<Card> sortedCards = new ArrayList<>();

        for (int i = MIN_NUMBER_OF_DECKS; i <= MAX_NUMBER_OF_DECKS; i++) {
            decks = new Decks(i);
            sortedCards.clear();
            for (int j = 0; j < decks.getDecksMaxSize(); j++) {
                sortedCards.add(decks.takeNextCard());
            }
            Collections.sort(sortedCards);


            Iterator<Card> sortedCardsIterator = sortedCards.listIterator();
            boolean areEveryCardPresent = true;
            Card nextCard;

            for (CardColors cardColor : CardColors.values()) {
                for (CardValues cardValue : CardValues.values()) {
                    if (!cardValue.equals(CardValues.ACE1)) {
                        for (int j = 0; j < decks.getNumberOfDecks(); j++) {
                            if (sortedCardsIterator.hasNext()) {
                                nextCard = sortedCardsIterator.next();
                                areEveryCardPresent = areEveryCardPresent && nextCard.equals(new Card(cardColor, cardValue));
                            }
                        }
                    }
                }
            }
            assertTrue(areEveryCardPresent);
        }
    }

    @Test
    void isThereAnyCardsLeftTest() {
        for (int i = 0; i < NUMBER_OF_CARDS_IN_ONE_DECK * MAX_NUMBER_OF_DECKS; i++) {
            assertDoesNotThrow(() -> decks.takeNextCard());
        }
        assertThrows(EmptyStackException.class, () -> decks.takeNextCard());
    }
}