package gameLogic.players;

import gameLogic.cards.Decks;
import gameLogic.cards.Card;
import gameLogic.cards.CardColors;
import gameLogic.cards.CardValues;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class HardBotTest {
    private HardBot ai;
    private Decks deck;

    @Test
    void wantToDrawCard(){
        generateAI(generateDeck());

        assertTrue(pickCard());
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertFalse(ai.isEnded());
        assertFalse(pickCard());
        assertTrue(ai.isEnded());
        assertFalse(pickCard());

        ai.clearGameData();

        // 7, 8, 9...
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertFalse(pickCard());
        assertTrue(ai.isEnded());

        ai.clearGameData();
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertFalse(pickCard());
    }

    @Test
    void wantToDrawCardWithAces(){
        generateAI(generateDeckWithAces());

        //blackjack
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertFalse(ai.isEnded());

        ai.clearGameData();

        // Testing ace value drop for next card
        // 8, 4 , ace, 5 ,6
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertTrue(pickCard());

        assertTrue(pickCard());
        assertFalse(pickCard());
        assertEquals(18, ai.getTotalPoints());
        ai.clearGameData();

        // Testing ace value drop for ai's cards
        // 6, ace, 8, 3, (5)
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertFalse(pickCard());
        assertEquals(18, ai.getTotalPoints());

        ai.clearGameData();

        // Testing ace drop threshold = 18
        // 5, ace, 3, (6)
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertFalse(pickCard());
        assertEquals(19, ai.getTotalPoints());

    }

    private boolean pickCard(){
        boolean decision = ai.wantToDrawCard();
        if(decision){
            ai.addCard(deck.takeNextCard());
        }
        return decision;
    }

    private void generateAI(Stack <Card> cards){
        deck = new Decks(1);
        deck.setDecks(cards);
        ai = new HardBot("nick", deck);
    }

    private Stack<Card> generateDeck() {
        Stack<Card> cards = new Stack<>();

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

        return cards;
    }

    private Stack<Card> generateDeckWithAces() {
        Stack<Card> cards = new Stack<>();
        cards.add(new Card(CardColors.DIAMONDS, CardValues.SIX));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.THREE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.ACE11));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.FIVE));

        // change ace in ai's cards
        cards.add(new Card(CardColors.DIAMONDS, CardValues.THREE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.EIGHT));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.ACE11));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.SIX));

        // change value of next ace in deck
        cards.add(new Card(CardColors.DIAMONDS, CardValues.FIVE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.ACE11));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.FOUR));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.EIGHT));

        // blackjack
        cards.add(new Card(CardColors.DIAMONDS, CardValues.ACE11));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.KING));
        return cards;
    }
}