package logic;

import java.util.EmptyStackException;

public class HardBot extends Player implements AI {
    private Decks deck;

    public HardBot(String nick, Decks deck){
        super(nick);
        this.deck = deck;
    }

    public boolean pickCard() {
        if(this.deck.isEmpty() || this.getIsEnded()){
            this.setIsEnded(true);
            return false;
        }

        int score = this.getCardsValue();
        try {
            int cardValue = this.deck.lookAtNextCardFromTheTop().getCardValue().getValue();

            if (Math.abs(21 - (score + cardValue)) < Math.abs(21 - score)) {
                this.addCard(this.deck.takeCardFromTheTop());
                return true;
            } else {
                this.setIsEnded(true);
                return false;
            }
        } catch(EmptyStackException e) {
            e.getMessage();
            return false;
        }
    }

    public int getCardsValue(){
        if(this.getCards() == null){
            return 0;
        }

        int value = 0;
        for (Card card:this.getCards()) {
            value = value + card.getCardValue().getValue();
        }
        return value;
    }

}
