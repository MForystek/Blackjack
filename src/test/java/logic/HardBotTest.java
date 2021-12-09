package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HardBotTest {
    private HardBot ai;
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

        ai = new HardBot("nick", deck);
    }

    @Test
    void pickCard(){
        assertEquals(true, ai.pickCard());
        assertEquals(true, ai.pickCard());
        assertEquals(true, ai.pickCard());
        assertEquals(true, ai.pickCard());
        assertEquals(true, ai.pickCard());
        assertEquals(false, ai.getIsEnded());
        assertEquals(false, ai.pickCard());
        assertEquals(true, ai.getIsEnded());
        assertEquals(false, ai.pickCard());

        ai.clearGameData();

        // 7, 8, 9...
        assertEquals(true, ai.pickCard());
        assertEquals(true, ai.pickCard());
        assertEquals(true, ai.pickCard());
        assertEquals(false, ai.pickCard());
        assertEquals(true, ai.getIsEnded());

        ai.clearGameData();
        assertEquals(true, ai.pickCard());
        assertEquals(true, ai.pickCard());
        assertEquals(false, ai.pickCard());

    }
}