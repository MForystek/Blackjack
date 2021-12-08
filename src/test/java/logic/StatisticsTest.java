package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {
    private Statistics statistics;

    @BeforeEach
    void beforeEach() {
        statistics = new Statistics();
    }

    @Test
    void updateStatistics() {
        ArrayList<Card> cards = new ArrayList<>();

        statistics.updateStatistics(true, cards, 100);

        assertEquals(1, statistics.getWinRate());
        assertEquals(1, statistics.getNumberOfGames());
        assertEquals(100, statistics.getGameTime());
        assertEquals(true, statistics.getCardHistory().isEmpty());

        Card eight = new Card(CardColors.HEARTS, CardValues.EIGHT);
        Card king = new Card(CardColors.SPADES, CardValues.KING);
        cards.add(eight);
        cards.add(king);
        cards.add(king);

        statistics.updateStatistics(false, cards, 50);

        assertEquals(0.5, statistics.getWinRate());
        assertEquals(2, statistics.getNumberOfGames());
        assertEquals(150, statistics.getGameTime());
        assertEquals(1, statistics.getCardOccurrence(eight));
        assertEquals(2, statistics.getCardOccurrence(king));
        assertEquals(null, statistics.getCardHistory().get(CardValues.ACE));
        assertEquals(0, statistics.getCardOccurrence(new Card(CardColors.DIAMONDS, CardValues.ACE)));

    }
}