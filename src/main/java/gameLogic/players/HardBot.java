package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.CardValues;
import gameLogic.cards.Decks;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class HardBot extends Player implements AI {
    private static final int MAX_SCORE = 21;
    private static final int ACE_VALUE_DROP_THRESHOLD = 17;
    private int bestScore;
    private String gameNick;

    private Decks deck;

    public HardBot(String nick, Decks deck) {
        super("HardBot");
        this.deck = deck;
        gameNick = nick;
    }

    public HardBot(Decks deck) {
        super("HardBot");
        this.deck = deck;
        gameNick = "HardBot";
    }

    public boolean wantToDrawCard(){
        if (deck.isEmpty() || isEnded()) {
            setEnded();
            return false;
        }

        if(haveBlackjack()){
            setEnded();
            return false;
        }

        bestScore = getTotalPoints();
        if (bestScore >= 21) {
            setEnded();
            return false;
        }

        Card nextCard;
        try {
            nextCard = deck.getNextCard();
        } catch (EmptyStackException e) {
            setEnded();
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
        } else {
            return true;
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

    public String getGameNick() {
        return gameNick;
    }

    public void setGameNick(String gameNick) {
        this.gameNick = gameNick;
    }
}
