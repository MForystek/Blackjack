package applicationLogic;

import gameLogic.cards.Card;
import gameLogic.cards.CardValues;

import java.util.HashMap;
import java.util.List;

public class Statistics {
    private float winRate = 0f;
    private int numberOfGames = 0;
    private HashMap <CardValues, Integer> cardHistory = new HashMap<>();
    private long gameTime = 0;

    public Statistics() {
        initCardHistory();
    }

    public void initCardHistory() {
        for (CardValues cardValue : CardValues.values()) {
            cardHistory.put(cardValue, 0);
        }
    }

    public void updateStatistics(boolean isWon, List<Card> cards, long gameTime) {
        if (isWon) {
            winRate = (winRate * numberOfGames + 1) / (numberOfGames + 1);
        } else {
            winRate = (winRate * numberOfGames) / (numberOfGames + 1);
        }
        numberOfGames += 1;
        this.gameTime += gameTime;
        for (Card card : cards) {
            cardHistoryAdd(card);
        }
    }

    public void cardHistoryAdd(Card card) {
        CardValues cardValue = card.getCardValue();
        if(cardHistory.containsKey(cardValue)) {
            cardHistory.replace(cardValue, cardHistory.get(cardValue) + 1);
        } else {
            cardHistory.put(cardValue, 1);
        }
    }

    public int getCardOccurrence(Card card) {
        CardValues cardValue = card.getCardValue();
        return cardHistory.getOrDefault(cardValue, 0);
    }

    public int getCardOccurrence(CardValues cardValues) {
        return cardHistory.get(cardValues);
    }

    public void setCardHistory(int two, int three, int four, int five, int six, int seven,
                               int eight, int nine, int ten, int jack, int queen, int king, int ace) {
        cardHistory.replace(CardValues.TWO, two);
        cardHistory.replace(CardValues.THREE, three);
        cardHistory.replace(CardValues.FOUR, four);
        cardHistory.replace(CardValues.FIVE, five);
        cardHistory.replace(CardValues.SIX, six);
        cardHistory.replace(CardValues.SEVEN, seven);
        cardHistory.replace(CardValues.EIGHT, eight);
        cardHistory.replace(CardValues.NINE, nine);
        cardHistory.replace(CardValues.TEN, ten);
        cardHistory.replace(CardValues.JACK, jack);
        cardHistory.replace(CardValues.QUEEN, queen);
        cardHistory.replace(CardValues.KING, king);
        cardHistory.replace(CardValues.ACE11, ace);
    }


    // --------------------------


    public float getWinRate() {
        return winRate;
    }

    public void setWinRate(float winRate) {
        this.winRate = winRate;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public HashMap<CardValues, Integer> getCardHistory() {
        return cardHistory;
    }

    public void setCardHistory(HashMap<CardValues, Integer> cardHistory) {
        this.cardHistory = cardHistory;
    }

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }
}
