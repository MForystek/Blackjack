package logic;

import java.util.ArrayList;

public class Player {
    public String nick;
    public Statistics statistics;
    public ArrayList <Card> cards = new ArrayList<>();
    public boolean is_ended = false;

    public Player(String nick) {
        this.nick = nick;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void clearGameData(){
        this.is_ended = false;
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

}
