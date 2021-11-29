package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardsTest {

    @Test
    void changeAceValueForAce() {
        Cards card = Cards.ACE;
        assertEquals(11, card.getValue());

        card.changeAceValue();
        assertEquals(1, card.getValue());

        card.changeAceValue();
        assertEquals(11, card.getValue());
    }

    @Test
    void changeAceValueForOtherCards() {
        Cards card;
        for (Cards currentCard : Cards.values()) {
            if (currentCard.getValue() == Cards.ACE.getValue()) {
                continue;
            }
            card = currentCard;
            card.changeAceValue();
            assertEquals(currentCard.getValue(), card.getValue());
        }
    }
}