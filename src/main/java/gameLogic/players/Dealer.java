package gameLogic.players;

import gameLogic.cards.Card;
import java.util.ArrayList;
import java.util.List;

public class Dealer extends Player implements AI {
    public static final int THRESHOLD_FOR_DEALER_TO_DRAW_CARDS = 16;
    private Card hiddenCard;
    private Card visibleCard;
    private boolean isEnded;

    // TODO : delete constructor
    public Dealer(){
        super("Dealer");
        cards = new ArrayList<>();
        isEnded = false;
    }

    public Dealer(Card visibleCard, Card hiddenCard) {
        super("Dealer");
        setVisibleCard(visibleCard);
        setHiddenCard(hiddenCard);
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

    @Override
    public int getTotalPoints() {
        if (getCards() == null) {
            return 0;
        }

        int value = 0;
        for (Card card : getCards()) {
            value = value + card.getValue();
        }
        return value + hiddenCard.getValue() + visibleCard.getValue();
    }

    @Override
    public void clearGameData() {
        isEnded = false;
        setIsWinner(false);
        cards.clear();
        hiddenCard = null;
        visibleCard = null;
    }

    @Override
    public boolean haveBlackjack() {
        if (visibleCard == null || hiddenCard == null){
            return false;
        }

        boolean hasAce = false;
        boolean hasTen = false;
        for (Card card : new Card[] {hiddenCard, visibleCard}) {
            switch (card.getCardValue()){
                case ACE11, ACE1: hasAce = true;
                    break;
                case KING, QUEEN, JACK, TEN: hasTen = true;
                    break;
            }
            if(hasAce && hasTen) return true;
        }
        return false;
    }

    public void setHiddenCard(Card card) {
        hiddenCard = card;
    }

    public void setVisibleCard(Card card) {
        visibleCard = card;
    }

    public Card getHiddenCard() {
        return hiddenCard;
    }

    public Card getVisibleCard() {
        return visibleCard;
    }

    public String getGameNick() {
        return this.getNick();
    }
}
