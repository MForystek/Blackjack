package gameLogic.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardValuesTest {

    @Test
    void changeAceValueForAce() {
        CardValues card = CardValues.ACE;
        assertEquals(11, card.getValue());

        card.changeAceValue();
        assertEquals(1, card.getValue());

        card.changeAceValue();
        assertEquals(11, card.getValue());
    }

    @Test
    void changeAceValueForOtherCards() {
        CardValues card;
        for (CardValues currentCard : CardValues.values()) {
            if (currentCard.getValue() == CardValues.ACE.getValue()) {
                continue;
            }
            card = currentCard;
            card.changeAceValue();
            assertEquals(currentCard.getValue(), card.getValue());
        }
    }
}