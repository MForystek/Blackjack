package gameLogic.players;

import applicationLogic.Statistics;
import gameLogic.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public static final int MAX_ALLOWED_POINTS_THRESHOLD = 21;

    private String nick;
    private Statistics statistics;
    private List<Card> cards = new ArrayList<>();
    private boolean isWinner = false;
    private boolean isEnded = false;

    public Player(String nick) {
        this.nick = nick;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void clearGameData() {
        isEnded = false;
        isWinner = false;
        cards.clear();
    }

    public boolean wantToDrawCard() {
        //TODO some way to check what player chose
        return false;
    }

    public boolean haveBlackjack() {
        //TODO check if player have Blackjack
        return false;
    }

    public int getTotalPoints() {
        //TODO getTotalAmountOfPoints
        return 0;
    }


    // ----------------------------------


    public void setWinner() {
        isWinner = true;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded() {
        isEnded = true;
    }
}
