package gameLogic.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


class CardDisplayerTest {
    private CardDisplayer cardDisplayer;
    private Path baseDir = Path.of("resources","cardSkins");

    @BeforeEach
    void before() {
        cardDisplayer = new CardDisplayer(baseDir);
    }


    @Test
    void getFilePath() {
        assertEquals(genPath(CardDisplayer.CARD_SKIN_V_1,"SA"),
                cardDisplayer.getFilePath(new Card(CardColors.SPADES, CardValues.ACE1)));

        cardDisplayer.changeCardsSkins();

        assertEquals(genPath(CardDisplayer.CARD_SKIN_V_2,"H8"),
                cardDisplayer.getFilePath(new Card(CardColors.HEARTS, CardValues.EIGHT)));
        assertEquals(genPath(CardDisplayer.CARD_SKIN_V_2,"DK"),
                cardDisplayer.getFilePath(new Card(CardColors.DIAMONDS, CardValues.KING)));

    }

    private Path genPath(String dirName, String fileName){
        return (Path.of(baseDir.toString(),dirName,
                fileName + "." + cardDisplayer.getExtension()));
    }


    @Test
    void changeCardsSkins() {
        assertEquals(CardDisplayer.CARD_SKIN_V_1,cardDisplayer.getCardSkinDirectory());

        cardDisplayer.changeCardsSkins();
        assertEquals(CardDisplayer.CARD_SKIN_V_2,cardDisplayer.getCardSkinDirectory());

        cardDisplayer.changeCardsSkins();
        assertEquals(CardDisplayer.CARD_SKIN_V_1,cardDisplayer.getCardSkinDirectory());

    }

    @Test
    void setCardSkinDirectory() {
        assertEquals(CardDisplayer.CARD_SKIN_V_1, cardDisplayer.getCardSkinDirectory());

        cardDisplayer.setCardSkinDirectory(CardDisplayer.CARD_SKIN_V_2);
        assertEquals(CardDisplayer.CARD_SKIN_V_2, cardDisplayer.getCardSkinDirectory());

        cardDisplayer.setCardSkinDirectory(CardDisplayer.CARD_SKIN_V_1);
        assertEquals(CardDisplayer.CARD_SKIN_V_1, cardDisplayer.getCardSkinDirectory());

        assertThrows(IllegalArgumentException.class, () -> {
            cardDisplayer.setCardSkinDirectory("s");
        });

    }

    @Test
    void printAllFilePaths() {
        Stack<Card> decks = new Stack<>();


        for (CardColors cardColor : CardColors.values()) {
            for (CardValues cardValue : CardValues.values()) {
                if (!cardValue.equals(CardValues.ACE1)) {
                    decks.push(new Card(cardColor, cardValue));
                }
            }
        }


        for(Card card: decks) {
            System.out.println(cardDisplayer.getFilePath(card));
        }
    }
}