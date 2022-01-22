package gameLogic.players;

import applicationLogic.Statistics;
import gameLogic.cards.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player implements Comparable<Object> {
    public static final int MAX_ALLOWED_POINTS_THRESHOLD = 21;

    private String nick;
    private Statistics statistics;
    protected List<Card> cards = new ArrayList<>();
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
        return false;
    }

    public boolean haveBlackjack() {
        if (cards == null || cards.size() != 2){
            return false;
        }

        boolean hasAce = false;
        boolean hasTen = false;
        for (Card card : cards) {
            switch (card.getCardValue().toString()){
                case "A": hasAce = true;
                    break;
                case "10": hasTen = true;
                    break;
            }
            if(hasAce && hasTen) return true;
        }
        return false;
    }

    public int getTotalPoints() {
        if (getCards() == null) {
            return 0;
        }

        int value = 0;
        for (Card card : getCards()) {
            value = value + card.getValue();
        }
        return value;
    }

    @Override
    public int compareTo(Object anotherPlayer) throws ClassCastException {
        if (!(anotherPlayer instanceof Player)) {
            throw new ClassCastException("A Player object expected.");
        }
        return ((Player)anotherPlayer).getTotalPoints() - this.getTotalPoints();
    }


    // ----------------------------------


    public void setWinner() {
        isWinner = true;
    }

    public void setIsWinner(boolean status) {
        isWinner = status;
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

    public String getGameNick() {
        return nick;
    }
}
