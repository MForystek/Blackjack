package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.CardValues;
import gameLogic.cards.Decks;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class HardBot extends Player implements AI {
    private static final int MAX_SCORE = 21;
    private static final int ACE_VALUE_DROP_THRESHOLD = 18;
    private int bestScore;

    private Decks deck;

    public HardBot(String nick, Decks deck) {
        super(nick);
        this.deck = deck;
    }

    public boolean wantToDrawCard(){
        if (deck.isEmpty() || isEnded()) {
            setEnded();
            return false;
        }

        if(haveBlackjack()){
            isEnded();
            return false;
        }

        bestScore = getTotalPoints();
        Card nextCard;
        try {
            nextCard = deck.getNextCard();
        } catch (EmptyStackException e) {
            return false;
        }

        if (isScoreBetter(nextCard)) {
            return true;
        } else if (nextCard.getCardValue().equals(CardValues.ACE11)) {
            nextCard.changeAceValue();
           if (isScoreBetter(nextCard)){
               return true;
           }
        } else if (bestScore <= ACE_VALUE_DROP_THRESHOLD &&
                canTakeCardViaDropAcesValues(nextCard)){
            return true;
        } else {
            setEnded();
            return false;
        }
        setEnded();
        return false;
    }

    private boolean isScoreBetter(Card nextCard){
        if (bestScore + nextCard.getCardValue().getValue() > MAX_SCORE){
            return false;
        }

        if (Math.abs(MAX_SCORE - (bestScore + nextCard.getCardValue().getValue()))
                < Math.abs(MAX_SCORE - bestScore)){
            return true;
        } else {
            return false;
        }
    }

    private boolean canTakeCardViaDropAcesValues(Card nextCard){
        for(int i = 0; i < cards.size(); i++){
            if (cards.get(i).getCardValue().equals(CardValues.ACE11)){
                if(cards.get(i).getCardValue().equals(CardValues.ACE11)) {
                    cards.get(i).changeAceValue();
                    bestScore = getTotalPoints();
                    if (isScoreBetter(nextCard)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
