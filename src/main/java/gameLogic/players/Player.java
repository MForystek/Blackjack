package gameLogic.players;

import applicationLogic.Statistics;
import gameLogic.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nick;
    private Statistics statistics;
    private List<Card> cards = new ArrayList<>();
    private boolean isEnded = false;

    public Player(String nick) {
        this.nick = nick;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void clearGameData() {
        isEnded = false;
        cards.clear();
    }


    // ----------------------------------


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

    public void setIsEnded(boolean ended) {
        isEnded = ended;
    }
}
