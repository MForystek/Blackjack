package gameLogic.players;

import gameLogic.cards.Card;
import java.util.ArrayList;
import java.util.List;

public class Dealer extends Player implements AI{
    public static final int THRESHOLD_FOR_DEALER_TO_DRAW_CARDS = 16;
    private Card hiddenCard;
    private boolean isEnded;

    public Dealer(){
        super("Dealer");
        cards = new ArrayList<>();
        isEnded = false;
    }
    public Dealer(String nick) {
        super(nick);
        cards = new ArrayList<>();
        isEnded = false;
    }

    public void setHiddenCard(Card card) {
        hiddenCard = card;
    }

    public boolean wantToDrawCard() {
        if (haveBlackjack() || !canDrawCard()) {
            setEnded();
            return false;
        }
        return true;
    }

    public boolean haveToManyPoints() {
        return getTotalPoints() > MAX_ALLOWED_POINTS_THRESHOLD;
    }

    private boolean canDrawCard () {
        int score = getTotalPoints();
        return score <= MAX_ALLOWED_POINTS_THRESHOLD &&
                score <= THRESHOLD_FOR_DEALER_TO_DRAW_CARDS && !isEnded;
    }
}
