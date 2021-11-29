package logic;

public class Card {
    private CardValues cardValue;
    private CardColors cardColor;

    public static Card getNullCard() {
        return new Card(CardColors.NULL, CardValues.NULL);
    }

    public Card(CardColors cardColor, CardValues cardValue) {
        this.cardValue = cardValue;
        this.cardColor = cardColor;
        adjustNullCardColorOrValue();
    }

    private void adjustNullCardColorOrValue() {
        if (cardColor == CardColors.NULL) {
            cardValue = CardValues.NULL;
        }
        if (cardValue == CardValues.NULL) {
            cardColor = CardColors.NULL;
        }
    }

    public CardColors getCardColor() {
        return cardColor;
    }

    public CardValues getCardValue() {
        return cardValue;
    }

    public boolean isNullCard() {
        return (getCardColor() == CardColors.NULL && getCardValue() == CardValues.NULL);
    }
}
