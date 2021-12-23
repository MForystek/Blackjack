//package logic;
//
//import java.util.EmptyStackException;
//
//public class Dealer extends Player implements AI{
//    private Decks deck;
//
//    public Dealer(String nick, Decks deck){
//        super(nick);
//        this.deck = deck;
//    }
//
//    public boolean pickCard() {
//        if(this.deck.isEmpty() || this.getIsEnded()){
//            this.setIsEnded(true);
//            return false;
//        }
//        try {
//            int score = this.getCardsValue();
//            if (score <= 16) {
//                this.addCard(this.deck.takeCardFromTheTop());
//                return true;
//            } else {
//                this.setIsEnded(true);
//                return false;
//            }
//        } catch(EmptyStackException e) {
//            e.getMessage();
//            return false;
//        }
//    }
//}
