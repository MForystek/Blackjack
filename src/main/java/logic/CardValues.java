package logic;

public enum CardValues {
    NULL(0),
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
    ACE(11);

    private int value;

    CardValues(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void changeAceValue() {
        if (this.value == 11) {
            value = 1;
        } else if (this.value == 1) {
            value = 11;
        }
    }
}
