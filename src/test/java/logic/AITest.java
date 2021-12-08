package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AITest {
    private AI ai;
    private Decks deck;

    @BeforeEach
    void beforeEach() {
        Stack<Card> cards = new Stack<>();

        cards.add(new Card(CardColors.DIAMONDS, CardValues.ACE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.KING));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.QUEEN));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.JACK));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.TEN));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.NINE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.EIGHT));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.SEVEN));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.SIX));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.FIVE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.FOUR));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.THREE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.TWO));

        deck = new Decks(1);
        deck.setDecks(cards);

        ai = new AI("nick", deck);
    }

    @Test
    void pickCard2(){
        assertEquals(true, ai.pickCard2());
        assertEquals(true, ai.pickCard2());
        assertEquals(true, ai.pickCard2());
        assertEquals(true, ai.pickCard2());
        assertEquals(true, ai.pickCard2());
        assertEquals(false, ai.is_ended);
        assertEquals(false, ai.pickCard2());
        assertEquals(true, ai.is_ended);
        assertEquals(false, ai.pickCard2());

        ai.clearGameData();

        // 7, 8, 9...
        assertEquals(true, ai.pickCard2());
        assertEquals(true, ai.pickCard2());
        assertEquals(true, ai.pickCard2());
        assertEquals(false, ai.pickCard2());
        assertEquals(true, ai.is_ended);

        ai.clearGameData();
        assertEquals(true, ai.pickCard2());
        assertEquals(true, ai.pickCard2());
        assertEquals(false, ai.pickCard2());

    }
}