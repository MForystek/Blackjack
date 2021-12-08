package logic;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {
    public float winRate = 0f;
    public int numberOfGames = 0;
    HashMap <CardValues, Integer> cardHistory = new HashMap<>();
    long gameTime = 0;

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

    private void cardHistoryAdd(Card card) {
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
