package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.CardValues;

import java.util.ArrayList;

public class Dealer extends Player implements AI {
    public static final int THRESHOLD_FOR_DEALER_TO_DRAW_CARDS = 16;
    private Card hiddenCard;
    private Card visibleCard;
    private boolean isEnded;
    private boolean isDealerTurns;

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

    private boolean canDrawCard () {
        int score = getTotalPoints();
        if (score <= THRESHOLD_FOR_DEALER_TO_DRAW_CARDS && !isEnded) {
            return true;
        } else if (score > 21) {
            return canDropAceValue();
        }
        return false;
    }

    private boolean canDropAceValue(){
        if (visibleCard.getCardValue().equals(CardValues.ACE11)) {
            visibleCard.changeAceValue();
            if (getTotalPoints() <= THRESHOLD_FOR_DEALER_TO_DRAW_CARDS && !isEnded) {
                return true;
            }
        }

        if (hiddenCard.getCardValue().equals(CardValues.ACE11)) {
            hiddenCard.changeAceValue();
            if (getTotalPoints() <= THRESHOLD_FOR_DEALER_TO_DRAW_CARDS && !isEnded) {
                return true;
            }
        }

        for (Card card : cards) {
            if (card.getCardValue().equals(CardValues.ACE11)) {
                card.changeAceValue();
                if (getTotalPoints() <= THRESHOLD_FOR_DEALER_TO_DRAW_CARDS && !isEnded) {
                    return true;
                }
            }
        }
        return false;
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
        if(isDealerTurns) {
            return value + hiddenCard.getValue() + visibleCard.getValue();
        }
        return value + visibleCard.getValue();
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

    public boolean isDealerTurns() {
        return isDealerTurns;
    }

    public void setDealerTurns(boolean dealerTurns) {
        isDealerTurns = dealerTurns;
    }
}
