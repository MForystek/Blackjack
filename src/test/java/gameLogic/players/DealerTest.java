package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.CardColors;
import gameLogic.cards.CardValues;
import gameLogic.cards.Decks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


class DealerTest {
    private Dealer ai;
    private Decks deck;

    @BeforeEach
    void beforeEach() {
        deck = new Decks(1);
        deck.setDecks(generateDeck());
        ai = new Dealer(deck.takeNextCard(),deck.takeNextCard());
    }

    @Test
    void wantToDrawCard() {

        assertTrue(pickCard());
        assertTrue(pickCard());
        assertTrue(pickCard());
        assertFalse(pickCard());
        assertEquals(17, ai.getTotalPoints());

        ai.clearGameData();
        createDealer();

        assertFalse(pickCard());
        assertEquals(21, ai.getTotalPoints());
    }

    private Stack<Card> generateDeck() {
        Stack<Card> cards = new Stack<>();

        cards.add(new Card(CardColors.DIAMONDS, CardValues.ACE11));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.KING));

        cards.add(new Card(CardColors.DIAMONDS, CardValues.THREE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.FIVE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.FOUR));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.THREE));
        cards.add(new Card(CardColors.DIAMONDS, CardValues.TWO));

        return cards;
    }

    private void createDealer(){
        ai = new Dealer(deck.takeNextCard(),deck.takeNextCard());
    }

    private boolean pickCard(){
        boolean decision = ai.wantToDrawCard();
        if(decision){
            ai.addCard(deck.takeNextCard());
        }
        return decision;
    }
}