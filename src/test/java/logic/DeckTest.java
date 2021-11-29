package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    private Deck deck;

    @BeforeEach
    void beforeEach() {
        deck = new Deck();
    }

    @Test
    void getAmountOfCardsInDeckTest() {
        assertEquals(52, deck.getAmountOfCardsInDeck());

        deck.takeCardFromDeck(CardColors.HEARTS, CardValues.TWO);
        deck.takeCardFromDeck(CardColors.SPADES, CardValues.ACE);

        assertEquals(50, deck.getAmountOfCardsInDeck());
    }

    @Test
    void isCardInDeckTest() {
        for (CardColors cardColor : CardColors.values()) {
            for (CardValues cardValues : CardValues.values()) {
                if (cardColor == CardColors.NULL || cardValues == CardValues.NULL) {
                    assertFalse(deck.isCardInDeck(cardColor, cardValues));
                } else {
                    assertTrue(deck.isCardInDeck(cardColor, cardValues));
                }
            }
        }
    }

    @Test
    void takeCardTest() {
        deck.takeCardFromDeck(CardColors.HEARTS, CardValues.TWO);
        assertFalse(deck.isCardInDeck(CardColors.HEARTS, CardValues.TWO));

        assertDoesNotThrow(() -> deck.takeCardFromDeck(CardColors.NULL, CardValues.ACE));
        assertDoesNotThrow(() -> deck.takeCardFromDeck(CardColors.HEARTS, CardValues.NULL));
    }
}