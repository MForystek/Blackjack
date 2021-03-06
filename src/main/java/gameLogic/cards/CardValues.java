package gameLogic.cards;

public enum CardValues implements Comparable<CardValues> {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE11(11),
    ACE1(1);

    private int value;

    CardValues(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (this.isAce()) {
            return "A";
        } else {
            return Integer.toString(value);
        }
    }

    public boolean isAce() {
        if (value == 11 || value == 1) {
            return true;
        } else {
            return false;
        }
    }
}
