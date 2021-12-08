package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DecksTest {
    private Decks decks;

    @BeforeEach
    void before() {
        decks = new Decks(4);
    }

    @Test
    void areCardsShuffled() {
        int cardsShuffled = 0;

        for (int i = 0; i < decks.getNumberOfDecks(); i++) {
            for (CardColors cardColor : CardColors.values()) {
                for (CardValues cardValue : CardValues.values()) {
                    Card nextCard = decks.takeCardFromTheTop();
                    if (!nextCard.equals(new Card(cardColor, cardValue))) {
                        cardsShuffled++;
                    }
                }
            }
        }

        assertTrue(cardsShuffled >= decks.getDecksMaxSize()*0.75);
    }

    @Test
    void areAllCardsPresentTest() {
        //TODO mandatory refactor this
        Decks tempDecks;
        List<Card> sortedCards;
        int maxAllowedAmountOfDecks = 4;

        for (int i = 1; i <= maxAllowedAmountOfDecks; i++) {
            tempDecks = new Decks(i);
            sortedCards = new ArrayList<>();
            for (int j = 0; j < tempDecks.getDecksMaxSize(); j++) {
                sortedCards.add(tempDecks.takeCardFromTheTop());
            }
            Collections.sort(sortedCards);


            boolean areEveryCardPresent = true;
            Iterator<Card> sortedCardsIterator = sortedCards.listIterator();
            Card nextCard;

            for (CardColors cardColor : CardColors.values()) {
                for (CardValues cardValue : CardValues.values()) {
                    for (int j = 0; j < tempDecks.getNumberOfDecks(); j++) {
                        if (sortedCardsIterator.hasNext()) {
                            nextCard = sortedCardsIterator.next();
                            areEveryCardPresent = areEveryCardPresent && nextCard.equals(new Card(cardColor, cardValue));
                        }
                    }
                }
            }
            assertTrue(areEveryCardPresent);
        }
    }
}