package logic;

import java.util.ArrayList;

public class Player {
    private String nick;
    private Statistics statistics;
    private ArrayList <Card> cards = new ArrayList<>();
    private boolean isEnded = false;

    public Player(String nick) {
        this.nick = nick;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void clearGameData(){
        this.isEnded = false;
        this.cards.clear();
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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public boolean getIsEnded() {
        return isEnded;
    }

    public void setIsEnded(boolean ended) {
        isEnded = ended;
    }
}