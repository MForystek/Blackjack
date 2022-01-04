package gameLogic.players;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import gameLogic.cards.Card;
import gameLogic.cards.CardValues;
import gameLogic.cards.Decks;

import java.util.Random;

public class MediumBot extends Player implements AI {
    private Decks deck;
    private Random random;
    private Card dealerCard;
    private MainStrategy mainStrategy;
    private static final int HIT_THRESHOLD = 17;
    private String gameNick;

    public MediumBot(String nick, Decks deck, Card dealerCard) {
        super("MediumBot");
        this.deck = deck;
        this.dealerCard = dealerCard;
        random = new Random();
        mainStrategy = new MainStrategy();
        gameNick = nick;
    }

    public MediumBot(Decks deck, Card dealerCard) {
        super("MediumBot");
        this.deck = deck;
        this.dealerCard = dealerCard;
        random = new Random();
        mainStrategy = new MainStrategy();
    }

    public boolean wantToDrawCard() {
        if (deck.isEmpty() || isEnded()) {
            setEnded();
            return false;
        }

        if(haveBlackjack()){
            setEnded();
            return false;
        }

        if (cards.size() == 2) {
            return useMainStrategy();
        } else {
            return randomDecision();
        }
    }

    private boolean useMainStrategy() {
        TwoCards twoCards = new TwoCards(cards.get(0), cards.get(1));
        Action action = mainStrategy.get(twoCards.text(), dealerCard.getCardValue().toString());

        if (action.equals(Action.STAND)) {
            setEnded();
            return false;
        } else if (action.equals(Action.HIT)) {
            return true;
        } else {
            return randomDecision();
        }
    }

    private boolean randomDecision() {
        if (getTotalPoints() >= HIT_THRESHOLD ) {
            setEnded();
            return false;
        }
        
        if (random.nextFloat() < 0.5f) {
            setEnded();
            return false;
        } else {
            return true;
        }
    }

    private enum Action implements Comparable<Action> {
        HIT,
        STAND,
        OTHER,
    }

    private enum TwoCardsType implements Comparable<TwoCardsType> {
        HARDHAND,
        SOFTHAND,
        PAIR
    }

    public String getGameNick() {
        return gameNick;
    }

    public void setGameNick(String gameNick) {
        this.gameNick = gameNick;
    }

    private class TwoCards {
        private CardValues cardValue1;
        private CardValues cardValue2;
        private TwoCardsType type;

        public TwoCards(Card card1, Card card2) {
            this.cardValue1 = card1.getCardValue();
            this.cardValue2 = card2.getCardValue();
            if (isPair()) {
                type = TwoCardsType.PAIR;
            } else if (isSoftHand()){
                type = TwoCardsType.SOFTHAND;
            } else {
                type = TwoCardsType.HARDHAND;
            }
        }

        public String text(){
            if (type.equals(TwoCardsType.PAIR)) {
                return cardValue2.toString() + cardValue1.toString();
            } else if (type.equals(TwoCardsType.SOFTHAND)) {
                if (cardValue1.isAce()) {
                    return cardValue1.toString() + cardValue2.toString();
                } else {
                    return cardValue2.toString() + cardValue1.toString();
                }
            } else {
                return Integer.toString(cardValue1.getValue() + cardValue2.getValue());
            }
        }

        public boolean isPair(){
            if (cardValue1.getValue() == cardValue2.getValue()) {
                return true;
            } else if (cardValue1.isAce() && cardValue2.isAce()) {
                return true;
            }
            return false;
        }

        public boolean isSoftHand(){
            if ((cardValue1.isAce() || cardValue2.isAce()) && !isPair()) {
                return true;
            }
            return false;
        }

    }

    public void setDealerCard(Card card){
        dealerCard = card;
    }

    private class MainStrategy {
        Table<String, String, Action> table;

        public MainStrategy(){
            table = HashBasedTable.create();

            table.put("20", "2", Action.STAND);
            table.put("20", "3", Action.STAND);
            table.put("20", "4", Action.STAND);
            table.put("20", "5", Action.STAND);
            table.put("20", "6", Action.STAND);
            table.put("20", "7", Action.STAND);
            table.put("20", "8", Action.STAND);
            table.put("20", "9", Action.STAND);
            table.put("20", "10", Action.STAND);
            table.put("20", "A", Action.STAND);
            table.put("19", "2", Action.STAND);
            table.put("19", "3", Action.STAND);
            table.put("19", "4", Action.STAND);
            table.put("19", "5", Action.STAND);
            table.put("19", "6", Action.STAND);
            table.put("19", "7", Action.STAND);
            table.put("19", "8", Action.STAND);
            table.put("19", "9", Action.STAND);
            table.put("19", "10", Action.STAND);
            table.put("19", "A", Action.STAND);
            table.put("18", "2", Action.STAND);
            table.put("18", "3", Action.STAND);
            table.put("18", "4", Action.STAND);
            table.put("18", "5", Action.STAND);
            table.put("18", "6", Action.STAND);
            table.put("18", "7", Action.STAND);
            table.put("18", "8", Action.STAND);
            table.put("18", "9", Action.STAND);
            table.put("18", "10", Action.STAND);
            table.put("18", "A", Action.STAND);
            table.put("17", "2", Action.STAND);
            table.put("17", "3", Action.STAND);
            table.put("17", "4", Action.STAND);
            table.put("17", "5", Action.STAND);
            table.put("17", "6", Action.STAND);
            table.put("17", "7", Action.STAND);
            table.put("17", "8", Action.STAND);
            table.put("17", "9", Action.STAND);
            table.put("17", "10", Action.STAND);
            table.put("17", "A", Action.STAND);
            table.put("16", "2", Action.STAND);
            table.put("16", "3", Action.STAND);
            table.put("16", "4", Action.STAND);
            table.put("16", "5", Action.STAND);
            table.put("16", "6", Action.STAND);
            table.put("16", "7", Action.HIT);
            table.put("16", "8", Action.HIT);
            table.put("16", "9", Action.HIT);
            table.put("16", "10", Action.HIT);
            table.put("16", "A", Action.HIT);
            table.put("15", "2", Action.STAND);
            table.put("15", "3", Action.STAND);
            table.put("15", "4", Action.STAND);
            table.put("15", "5", Action.STAND);
            table.put("15", "6", Action.STAND);
            table.put("15", "7", Action.HIT);
            table.put("15", "8", Action.HIT);
            table.put("15", "9", Action.HIT);
            table.put("15", "10", Action.HIT);
            table.put("15", "A", Action.HIT);
            table.put("14", "2", Action.STAND);
            table.put("14", "3", Action.STAND);
            table.put("14", "4", Action.STAND);
            table.put("14", "5", Action.STAND);
            table.put("14", "6", Action.STAND);
            table.put("14", "7", Action.HIT);
            table.put("14", "8", Action.HIT);
            table.put("14", "9", Action.HIT);
            table.put("14", "10", Action.HIT);
            table.put("14", "A", Action.HIT);
            table.put("13", "2", Action.STAND);
            table.put("13", "3", Action.STAND);
            table.put("13", "4", Action.STAND);
            table.put("13", "5", Action.STAND);
            table.put("13", "6", Action.STAND);
            table.put("13", "7", Action.HIT);
            table.put("13", "8", Action.HIT);
            table.put("13", "9", Action.HIT);
            table.put("13", "10", Action.HIT);
            table.put("13", "A", Action.HIT);
            table.put("12", "2", Action.HIT);
            table.put("12", "3", Action.HIT);
            table.put("12", "4", Action.STAND);
            table.put("12", "5", Action.STAND);
            table.put("12", "6", Action.STAND);
            table.put("12", "7", Action.HIT);
            table.put("12", "8", Action.HIT);
            table.put("12", "9", Action.HIT);
            table.put("12", "10", Action.HIT);
            table.put("12", "A", Action.HIT);
            table.put("11", "2", Action.HIT);
            table.put("11", "3", Action.HIT);
            table.put("11", "4", Action.HIT);
            table.put("11", "5", Action.HIT);
            table.put("11", "6", Action.HIT);
            table.put("11", "7", Action.HIT);
            table.put("11", "8", Action.HIT);
            table.put("11", "9", Action.HIT);
            table.put("11", "10", Action.HIT);
            table.put("11", "A", Action.HIT);
            table.put("10", "2", Action.HIT);
            table.put("10", "3", Action.HIT);
            table.put("10", "4", Action.HIT);
            table.put("10", "5", Action.HIT);
            table.put("10", "6", Action.HIT);
            table.put("10", "7", Action.HIT);
            table.put("10", "8", Action.HIT);
            table.put("10", "9", Action.HIT);
            table.put("10", "10", Action.HIT);
            table.put("10", "A", Action.HIT);
            table.put("9", "2", Action.HIT);
            table.put("9", "3", Action.HIT);
            table.put("9", "4", Action.HIT);
            table.put("9", "5", Action.HIT);
            table.put("9", "6", Action.HIT);
            table.put("9", "7", Action.HIT);
            table.put("9", "8", Action.HIT);
            table.put("9", "9", Action.HIT);
            table.put("9", "10", Action.HIT);
            table.put("9", "A", Action.HIT);
            table.put("8", "2", Action.HIT);
            table.put("8", "3", Action.HIT);
            table.put("8", "4", Action.HIT);
            table.put("8", "5", Action.HIT);
            table.put("8", "6", Action.HIT);
            table.put("8", "7", Action.HIT);
            table.put("8", "8", Action.HIT);
            table.put("8", "9", Action.HIT);
            table.put("8", "10", Action.HIT);
            table.put("8", "A", Action.HIT);
            table.put("7", "2", Action.HIT);
            table.put("7", "3", Action.HIT);
            table.put("7", "4", Action.HIT);
            table.put("7", "5", Action.HIT);
            table.put("7", "6", Action.HIT);
            table.put("7", "7", Action.HIT);
            table.put("7", "8", Action.HIT);
            table.put("7", "9", Action.HIT);
            table.put("7", "10", Action.HIT);
            table.put("7", "A", Action.HIT);
            table.put("6", "2", Action.HIT);
            table.put("6", "3", Action.HIT);
            table.put("6", "4", Action.HIT);
            table.put("6", "5", Action.HIT);
            table.put("6", "6", Action.HIT);
            table.put("6", "7", Action.HIT);
            table.put("6", "8", Action.HIT);
            table.put("6", "9", Action.HIT);
            table.put("6", "10", Action.HIT);
            table.put("6", "A", Action.HIT);
            table.put("5", "2", Action.HIT);
            table.put("5", "3", Action.HIT);
            table.put("5", "4", Action.HIT);
            table.put("5", "5", Action.HIT);
            table.put("5", "6", Action.HIT);
            table.put("5", "7", Action.HIT);
            table.put("5", "8", Action.HIT);
            table.put("5", "9", Action.HIT);
            table.put("5", "10", Action.HIT);
            table.put("5", "A", Action.HIT);
            table.put("A9", "2", Action.STAND);
            table.put("A9", "3", Action.STAND);
            table.put("A9", "4", Action.STAND);
            table.put("A9", "5", Action.STAND);
            table.put("A9", "6", Action.STAND);
            table.put("A9", "7", Action.STAND);
            table.put("A9", "8", Action.STAND);
            table.put("A9", "9", Action.STAND);
            table.put("A9", "10", Action.STAND);
            table.put("A9", "A", Action.STAND);
            table.put("A8", "2", Action.STAND);
            table.put("A8", "3", Action.STAND);
            table.put("A8", "4", Action.STAND);
            table.put("A8", "5", Action.STAND);
            table.put("A8", "6", Action.STAND);
            table.put("A8", "7", Action.STAND);
            table.put("A8", "8", Action.STAND);
            table.put("A8", "9", Action.STAND);
            table.put("A8", "10", Action.STAND);
            table.put("A8", "A", Action.STAND);
            table.put("A7", "2", Action.STAND);
            table.put("A7", "3", Action.STAND);
            table.put("A7", "4", Action.STAND);
            table.put("A7", "5", Action.STAND);
            table.put("A7", "6", Action.STAND);
            table.put("A7", "7", Action.STAND);
            table.put("A7", "8", Action.STAND);
            table.put("A7", "9", Action.HIT);
            table.put("A7", "10", Action.HIT);
            table.put("A7", "A", Action.HIT);
            table.put("A6", "2", Action.HIT);
            table.put("A6", "3", Action.HIT);
            table.put("A6", "4", Action.HIT);
            table.put("A6", "5", Action.HIT);
            table.put("A6", "6", Action.HIT);
            table.put("A6", "7", Action.HIT);
            table.put("A6", "8", Action.HIT);
            table.put("A6", "9", Action.HIT);
            table.put("A6", "10", Action.HIT);
            table.put("A6", "A", Action.HIT);
            table.put("A5", "2", Action.HIT);
            table.put("A5", "3", Action.HIT);
            table.put("A5", "4", Action.HIT);
            table.put("A5", "5", Action.HIT);
            table.put("A5", "6", Action.HIT);
            table.put("A5", "7", Action.HIT);
            table.put("A5", "8", Action.HIT);
            table.put("A5", "9", Action.HIT);
            table.put("A5", "10", Action.HIT);
            table.put("A5", "A", Action.HIT);
            table.put("A4", "2", Action.HIT);
            table.put("A4", "3", Action.HIT);
            table.put("A4", "4", Action.HIT);
            table.put("A4", "5", Action.HIT);
            table.put("A4", "6", Action.HIT);
            table.put("A4", "7", Action.HIT);
            table.put("A4", "8", Action.HIT);
            table.put("A4", "9", Action.HIT);
            table.put("A4", "10", Action.HIT);
            table.put("A4", "A", Action.HIT);
            table.put("A3", "2", Action.HIT);
            table.put("A3", "3", Action.HIT);
            table.put("A3", "4", Action.HIT);
            table.put("A3", "5", Action.HIT);
            table.put("A3", "6", Action.HIT);
            table.put("A3", "7", Action.HIT);
            table.put("A3", "8", Action.HIT);
            table.put("A3", "9", Action.HIT);
            table.put("A3", "10", Action.HIT);
            table.put("A3", "A", Action.HIT);
            table.put("A2", "2", Action.HIT);
            table.put("A2", "3", Action.HIT);
            table.put("A2", "4", Action.HIT);
            table.put("A2", "5", Action.HIT);
            table.put("A2", "6", Action.HIT);
            table.put("A2", "7", Action.HIT);
            table.put("A2", "8", Action.HIT);
            table.put("A2", "9", Action.HIT);
            table.put("A2", "10", Action.HIT);
            table.put("A2", "A", Action.HIT);
            table.put("1010", "2", Action.STAND);
            table.put("1010", "3", Action.STAND);
            table.put("1010", "4", Action.STAND);
            table.put("1010", "5", Action.STAND);
            table.put("1010", "6", Action.STAND);
            table.put("1010", "7", Action.STAND);
            table.put("1010", "8", Action.STAND);
            table.put("1010", "9", Action.STAND);
            table.put("1010", "10", Action.STAND);
            table.put("1010", "A", Action.STAND);
            table.put("99", "2", Action.OTHER);
            table.put("99", "3", Action.OTHER);
            table.put("99", "4", Action.OTHER);
            table.put("99", "5", Action.OTHER);
            table.put("99", "6", Action.OTHER);
            table.put("99", "7", Action.STAND);
            table.put("99", "8", Action.OTHER);
            table.put("99", "9", Action.OTHER);
            table.put("99", "10", Action.STAND);
            table.put("99", "A", Action.STAND);
            table.put("88", "2", Action.OTHER);
            table.put("88", "3", Action.OTHER);
            table.put("88", "4", Action.OTHER);
            table.put("88", "5", Action.OTHER);
            table.put("88", "6", Action.OTHER);
            table.put("88", "7", Action.OTHER);
            table.put("88", "8", Action.OTHER);
            table.put("88", "9", Action.OTHER);
            table.put("88", "10", Action.OTHER);
            table.put("88", "A", Action.OTHER);
            table.put("77", "2", Action.OTHER);
            table.put("77", "3", Action.OTHER);
            table.put("77", "4", Action.OTHER);
            table.put("77", "5", Action.OTHER);
            table.put("77", "6", Action.OTHER);
            table.put("77", "7", Action.OTHER);
            table.put("77", "8", Action.HIT);
            table.put("77", "9", Action.HIT);
            table.put("77", "10", Action.HIT);
            table.put("77", "A", Action.HIT);
            table.put("66", "2", Action.OTHER);
            table.put("66", "3", Action.OTHER);
            table.put("66", "4", Action.OTHER);
            table.put("66", "5", Action.OTHER);
            table.put("66", "6", Action.OTHER);
            table.put("66", "7", Action.HIT);
            table.put("66", "8", Action.HIT);
            table.put("66", "9", Action.HIT);
            table.put("66", "10", Action.HIT);
            table.put("66", "A", Action.HIT);
            table.put("55", "2", Action.HIT);
            table.put("55", "3", Action.HIT);
            table.put("55", "4", Action.HIT);
            table.put("55", "5", Action.HIT);
            table.put("55", "6", Action.HIT);
            table.put("55", "7", Action.HIT);
            table.put("55", "8", Action.HIT);
            table.put("55", "9", Action.HIT);
            table.put("55", "10", Action.HIT);
            table.put("55", "A", Action.HIT);
            table.put("44", "2", Action.HIT);
            table.put("44", "3", Action.HIT);
            table.put("44", "4", Action.HIT);
            table.put("44", "5", Action.OTHER);
            table.put("44", "6", Action.OTHER);
            table.put("44", "7", Action.HIT);
            table.put("44", "8", Action.HIT);
            table.put("44", "9", Action.HIT);
            table.put("44", "10", Action.HIT);
            table.put("44", "A", Action.HIT);
            table.put("33", "2", Action.OTHER);
            table.put("33", "3", Action.OTHER);
            table.put("33", "4", Action.OTHER);
            table.put("33", "5", Action.OTHER);
            table.put("33", "6", Action.OTHER);
            table.put("33", "7", Action.OTHER);
            table.put("33", "8", Action.HIT);
            table.put("33", "9", Action.HIT);
            table.put("33", "10", Action.HIT);
            table.put("33", "A", Action.HIT);
            table.put("22", "2", Action.OTHER);
            table.put("22", "3", Action.OTHER);
            table.put("22", "4", Action.OTHER);
            table.put("22", "5", Action.OTHER);
            table.put("22", "6", Action.OTHER);
            table.put("22", "7", Action.OTHER);
            table.put("22", "8", Action.HIT);
            table.put("22", "9", Action.HIT);
            table.put("22", "10", Action.HIT);
            table.put("22", "A", Action.HIT);
            table.put("AA", "2", Action.OTHER);
            table.put("AA", "3", Action.OTHER);
            table.put("AA", "4", Action.OTHER);
            table.put("AA", "5", Action.OTHER);
            table.put("AA", "6", Action.OTHER);
            table.put("AA", "7", Action.OTHER);
            table.put("AA", "8", Action.OTHER);
            table.put("AA", "9", Action.OTHER);
            table.put("AA", "10", Action.OTHER);
            table.put("AA", "A", Action.OTHER);

        }

        public Action get(String cards, String dealerCard){
            return table.get(cards,dealerCard);
        }
    }

}
