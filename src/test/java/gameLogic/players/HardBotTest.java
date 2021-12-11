package gameLogic.players;

import gameLogic.cards.Decks;
import gameLogic.cards.Card;
import gameLogic.cards.CardColors;
import gameLogic.cards.CardValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(ai.pickCard());
        assertTrue(ai.pickCard());
        assertTrue(ai.pickCard());
        assertTrue(ai.pickCard());
        assertTrue(ai.pickCard());
        assertFalse(ai.isEnded());
        assertFalse(ai.pickCard());
        assertTrue(ai.isEnded());
        assertFalse(ai.pickCard());

        ai.clearGameData();

        // 7, 8, 9...
        assertTrue(ai.pickCard());
        assertTrue(ai.pickCard());
        assertTrue(ai.pickCard());
        assertFalse(ai.pickCard());
        assertTrue(ai.isEnded());

        ai.clearGameData();
        assertTrue(ai.pickCard());
        assertTrue(ai.pickCard());
        assertFalse(ai.pickCard());
    }
}