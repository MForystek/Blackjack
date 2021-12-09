package logic;

import java.util.Random;

public class EasyBot extends Player implements AI {
    private Decks deck;
    private Random random = new Random();


    public EasyBot(String nick, Decks deck){
        super(nick);
        this.deck = deck;
    }

    public boolean pickCard() {
        if(this.deck.isEmpty() || this.getIsEnded()){
            this.setIsEnded(true);
            return false;
        }

        if (this.random.nextFloat() < 0.5f) {
            this.setIsEnded(true);
            return false;
        } else {
            return true;
        }
    }

    public int getCardsValue(){
        if(this.getCards()== null){
            return 0;
        }

        int value = 0;
        for (Card card:this.getCards()) {
            value = value + card.getCardValue().getValue();
        }
        return value;
    }

}
