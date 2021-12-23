package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.CardColors;
import gameLogic.cards.CardValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PlayerTest {
    private Player player;

    @BeforeEach
    void beforeEach() {
        player = new Player("nick");
    }

    @Test
    void haveBlackjack() {
        player.cards.add(new Card(CardColors.DIAMONDS, CardValues.ACE11));
        assertFalse(player.haveBlackjack());

        player.cards.add(new Card(CardColors.DIAMONDS, CardValues.KING));
        assertTrue(player.haveBlackjack());

        player.cards.add(new Card(CardColors.DIAMONDS, CardValues.EIGHT));
        assertFalse(player.haveBlackjack());

    }
}