package gameLogic.players;

import static gameLogic.players.Player.MAX_ALLOWED_POINTS_THRESHOLD;

import gameLogic.cards.Card;

import java.util.ArrayList;
import java.util.List;

//TODO prawdopodobnie dałoby się powiązać Dealera i Playera poprzez jakąś wspólną klasę rodzic bo mają dużo wspólnego
public class Dealer {
    public static final int THRESHOLD_FOR_DEALER_TO_DRAW_CARDS = 17;

    private List<Card> cards;
    private Card hiddenCard;
    private boolean isEnded;

    public Dealer() {
        cards = new ArrayList<>();
        isEnded = false;
    }

    public void setHiddenCard(Card card) {
        hiddenCard = card;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void clearGameData() {
        hiddenCard = null;
        cards.clear();
    }

    public boolean wantToDrawCard() {
        return haveBlackjack() || getTotalPoints() >= THRESHOLD_FOR_DEALER_TO_DRAW_CARDS;
    }

    public boolean haveBlackjack() {
        //TODO the same as for Player
        return false;
    }

    public int getTotalPoints() {
        //TODO the same as for Player
        return 0;
    }

    public boolean haveToManyPoints() {
        return getTotalPoints() > MAX_ALLOWED_POINTS_THRESHOLD;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded() {
        isEnded = true;
    }
}
