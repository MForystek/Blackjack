package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.CardColors;
import gameLogic.cards.CardValues;
import gameLogic.cards.Decks;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class MediumBotTest {
    private MediumBot ai;
    private Decks deck;
    private Card dealerCard;

    @Test
    void wantToDrawCard(){
        generateAI(generateDeck());


        // HARDHAND -------------------------------------------------
        ai.setDealerCard(generateDealerCard(CardValues.ACE11));

        addCardsToDeckAndPick(CardValues.TWO, CardValues.THREE);
        assertTrue(pickCard());
        ai.clearGameData();

        addCardsToDeckAndPick(CardValues.KING, CardValues.FIVE);
        assertTrue(pickCard());
        ai.clearGameData();

        addCardsToDeckAndPick(CardValues.QUEEN, CardValues.SEVEN);
        assertFalse(pickCard());
        ai.clearGameData();

        ai.setDealerCard(generateDealerCard(CardValues.ACE11));

        addCardsToDeckAndPick(CardValues.QUEEN, CardValues.NINE);
        assertFalse(pickCard());
        ai.clearGameData();

        addCardsToDeckAndPick(CardValues.QUEEN, CardValues.SIX);
        assertTrue(pickCard());
        ai.clearGameData();

        addCardsToDeckAndPick(CardValues.TWO, CardValues.SEVEN);
        assertTrue(pickCard());
        ai.clearGameData();

        ai.setDealerCard(generateDealerCard(CardValues.TWO));

        addCardsToDeckAndPick(CardValues.QUEEN, CardValues.NINE);
        assertFalse(pickCard());
        ai.clearGameData();

        addCardsToDeckAndPick(CardValues.TEN, CardValues.THREE);
        assertFalse(pickCard());
        ai.clearGameData();

        addCardsToDeckAndPick(CardValues.TWO, CardValues.FOUR);
        assertTrue(pickCard());
        ai.clearGameData();

        // SOFTHAND ----------------------------------------------
        ai.setDealerCard(generateDealerCard(CardValues.EIGHT));

        addCardsToDeckAndPick(CardValues.ACE11, CardValues.SEVEN);
        assertFalse(pickCard());
        ai.clearGameData();

        addCardsToDeckAndPick(CardValues.ACE11, CardValues.FIVE);
        assertTrue(pickCard());
        ai.clearGameData();

        ai.setDealerCard(generateDealerCard(CardValues.SIX));

        addCardsToDeckAndPick(CardValues.ACE11, CardValues.SIX);
        assertTrue(pickCard());
        ai.clearGameData();

        addCardsToDeckAndPick(CardValues.ACE11, CardValues.EIGHT);
        assertFalse(pickCard());
        ai.clearGameData();

        // PAIR --------------------------------------------------

        ai.setDealerCard(generateDealerCard(CardValues.SIX));

        addCardsToDeckAndPick(CardValues.FIVE, CardValues.FIVE);
        assertTrue(pickCard());
        ai.clearGameData();

        addCardsToDeckAndPick(CardValues.TEN, CardValues.KING);
        assertFalse(pickCard());
        ai.clearGameData();

        ai.setDealerCard(generateDealerCard(CardValues.FOUR));

        addCardsToDeckAndPick(CardValues.FOUR, CardValues.FOUR);
        assertTrue(pickCard());
        ai.clearGameData();

        ai.setDealerCard(generateDealerCard(CardValues.ACE11));

        addCardsToDeckAndPick(CardValues.NINE, CardValues.NINE);
        assertFalse(pickCard());
        ai.clearGameData();

    }

    private boolean pickCard(){
        boolean decision = ai.wantToDrawCard();
        if(decision){
            ai.addCard(deck.takeNextCard());
        }
        return decision;
    }

    private void pick2Cards(){
        ai.addCard(deck.takeNextCard());
        ai.addCard(deck.takeNextCard());
    }

    private void generateAI(Stack <Card> cards){
        deck = new Decks(1);
        deck.setDecks(cards);
        dealerCard = new Card(CardColors.DIAMONDS, CardValues.TWO);
        ai = new MediumBot(deck, dealerCard);
    }

    private Stack<Card> generateDeck() {
        Stack<Card> cards = new Stack<>();
        cards.add(new Card(CardColors.DIAMONDS, CardValues.KING));
        return cards;
    }

    private Card generateDealerCard(CardValues cardValues){
        return new Card(CardColors.DIAMONDS, cardValues);
    }

    private void addCardsToDeckAndPick(CardValues cardValues1, CardValues cardValues2) {
        Stack<Card> cards = new Stack<>();

        cards.add(new Card(CardColors.DIAMONDS, CardValues.TWO));
        cards.add(new Card(CardColors.SPADES, cardValues1));
        cards.add(new Card(CardColors.HEARTS, cardValues2));

        deck.setDecks(cards);

        pick2Cards();
    }
}