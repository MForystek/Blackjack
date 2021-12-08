package logic;

import java.util.EmptyStackException;
import java.util.Random;

public class AI extends Player{
    public Decks deck;
    public Random random = new Random();


    public AI(String nick, Decks deck){
        super(nick);
        this.deck = deck;
    }

    public boolean pickCard() {
        if(this.deck.isEmpty() || this.is_ended){
            this.is_ended = true;
            return false;
        }

        if (this.random.nextFloat() < 0.5f) {
            this.is_ended = true;
            return false;
        } else {
            return true;
        }
    }

    public boolean pickCard2() {
        if(this.deck.isEmpty() || this.is_ended){
            this.is_ended = true;
            return false;
        }

        int score = this.getCardsValue();
        try {
            int cardValue = this.deck.lookAtNextCardFromTheTop().getCardValue().getValue();

            if (Math.abs(21 - (score + cardValue)) < Math.abs(21 - score)) {
                this.addCard(this.deck.takeCardFromTheTop());
                return true;
            } else {
                this.is_ended = true;
                return false;
            }
        } catch(EmptyStackException e) {
            e.getMessage();
            return false;
        }
    }

    public int getCardsValue(){
        if(this.cards == null){
            return 0;
        }

        int value = 0;
        for (Card card:this.cards) {
            value = value + card.getCardValue().getValue();
        }
        return value;
    }

}
