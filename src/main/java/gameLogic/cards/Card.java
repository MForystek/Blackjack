package gameLogic.cards;

public class Card implements Comparable<Card> {
    private CardValues cardValue;
    private CardColors cardColor;

    public Card(CardColors cardColor, CardValues cardValue) {
        this.cardValue = cardValue;
        this.cardColor = cardColor;
    }

    public void changeAceValue(){
        if (cardValue.equals(CardValues.ACE11)) {
            cardValue = CardValues.ACE1;
        } else if (cardValue.equals(CardValues.ACE1)) {
            cardValue = CardValues.ACE11;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card c)) {
            return false;
        }
        boolean isColorTheSame = this.cardColor.equals(c.cardColor);
        boolean isValueTheSame = this.cardValue.equals(c.cardValue);
        return isColorTheSame && isValueTheSame;
    }

    public CardColors getCardColor() {
        return cardColor;
    }

    public CardValues getCardValue() {
        return cardValue;
    }

    public int getValue() {
        return cardValue.getValue();
    }

    @Override
    public String toString() {
        return "Card color: " + cardColor.toString() + " Card value: " + cardValue.toString();
    }

    @Override
    public int compareTo(Card c) {
        if (c.cardColor.compareTo(this.cardColor) < 0) {
            return 1;
        } else if (c.cardColor.compareTo(this.cardColor) > 0) {
            return -1;
        } else {
            return -c.cardValue.compareTo(this.cardValue);
        }
    }
}
