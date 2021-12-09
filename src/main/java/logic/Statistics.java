package logic;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {
    private float winRate = 0f;
    private int numberOfGames = 0;
    private HashMap <CardValues, Integer> cardHistory = new HashMap<>();
    private long gameTime = 0;

    public Statistics(){
        initCardHistory();
    }

    public void updateStatistics(boolean is_won, ArrayList<Card> cards, long gameTime) {
        if (is_won) {
            this.winRate = (this.winRate * this.numberOfGames + 1) / (numberOfGames + 1);
        } else {
            this.winRate = (this.winRate * this.numberOfGames) / (numberOfGames + 1);
        }
        this.numberOfGames += 1;
        this.gameTime += gameTime;
        for (Card card:cards) {
            this.cardHistoryAdd(card);
        }
    }

    public void cardHistoryAdd(Card card) {
        CardValues cardValue = card.getCardValue();
        if(this.cardHistory.containsKey(cardValue)) {
            this.cardHistory.replace(cardValue, this.cardHistory.get(cardValue) + 1);
        } else {
            this.cardHistory.put(cardValue, 1);
        }
    }

    public int getCardOccurrence(Card card) {
        CardValues cardValue = card.getCardValue();
        if (this.cardHistory.containsKey(cardValue)) {
            return this.cardHistory.get(cardValue);
        } else {
            return 0;
        }
    }

    public int getCardOccurrence(CardValues cardValues) {
        return this.cardHistory.get(cardValues);
    }

    public void initCardHistory(){
        this.cardHistory.put(CardValues.TWO, 0);
        this.cardHistory.put(CardValues.THREE, 0);
        this.cardHistory.put(CardValues.FOUR, 0);
        this.cardHistory.put(CardValues.FIVE, 0);
        this.cardHistory.put(CardValues.SIX, 0);
        this.cardHistory.put(CardValues.SEVEN, 0);
        this.cardHistory.put(CardValues.EIGHT, 0);
        this.cardHistory.put(CardValues.NINE, 0);
        this.cardHistory.put(CardValues.TEN, 0);
        this.cardHistory.put(CardValues.JACK, 0);
        this.cardHistory.put(CardValues.QUEEN, 0);
        this.cardHistory.put(CardValues.KING, 0);
        this.cardHistory.put(CardValues.ACE, 0);
    }

    public void setCardHistory(int two, int three, int four, int five, int six, int seven,
                               int eight, int nine, int ten, int jack, int queen, int king, int ace) {
        this.cardHistory.replace(CardValues.TWO, two);
        this.cardHistory.replace(CardValues.THREE, three);
        this.cardHistory.replace(CardValues.FOUR, four);
        this.cardHistory.replace(CardValues.FIVE, five);
        this.cardHistory.replace(CardValues.SIX, six);
        this.cardHistory.replace(CardValues.SEVEN, seven);
        this.cardHistory.replace(CardValues.EIGHT, eight);
        this.cardHistory.replace(CardValues.NINE, nine);
        this.cardHistory.replace(CardValues.TEN, ten);
        this.cardHistory.replace(CardValues.JACK, jack);
        this.cardHistory.replace(CardValues.QUEEN, queen);
        this.cardHistory.replace(CardValues.KING, king);
        this.cardHistory.replace(CardValues.ACE, ace);
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
