package gameLogic.cards;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CardDisplayer {
    private Path baseDirectory;
    private String cardSkinDirectory;
    private String extension = "jpg";
    public static final String CARD_SKIN_V_1 = "v1";
    public static final String CARD_SKIN_V_2 = "v2";

    public CardDisplayer(Path directory) {
        this.baseDirectory = directory;
        setDefaultCardSkin();
        createDirectories();
    }

    public CardDisplayer(){
        this(Path.of("resources", "cardSkins"));
    }

    private void createDirectories() {
        try {
            Files.createDirectories(Path.of(baseDirectory.toString(), CARD_SKIN_V_1));
            Files.createDirectories(Path.of(baseDirectory.toString(), CARD_SKIN_V_2));
        } catch (IOException e) {
            System.err.println("Cannot create directories " + e.getMessage());
        }
    }

    public void setDefaultCardSkin(){
        cardSkinDirectory = CARD_SKIN_V_1;
    }

    public void changeCardsSkins(){
        cardSkinDirectory = switch (cardSkinDirectory){
            case CARD_SKIN_V_1 -> CARD_SKIN_V_2;
            default -> CARD_SKIN_V_1;
        };
    }

    public Path getFilePath(Card card) {
        return Path.of(baseDirectory.toString(),
                cardSkinDirectory, cardToFilename(card) + "." + extension);
    }

    private String cardToFilename(Card card) {
        return cardColorsToSymbol(card.getCardColor())
                + cardValuesToSymbol(card.getCardValue());
    }

    private String cardValuesToSymbol(CardValues cardValues){
        if (cardValues.getValue() != 10) {
            return cardValues.toString();
        } else {
            return switch (cardValues) {
                case TEN -> "10";
                case JACK -> "J";
                case QUEEN -> "Q";
                case KING -> "K";
                default -> throw new IllegalStateException("Unexpected value: " + cardValues);
            };
        }
    }

    private String cardColorsToSymbol(CardColors cardColors) {
        return switch (cardColors){
            case CLUBS -> "C";
            case HEARTS -> "H";
            case SPADES -> "S";
            case DIAMONDS -> "D";
            default -> throw new IllegalStateException("Unexpected value: " + cardColors);
        };
    }


    // getters and setters ------------------------------------------

    /**
     *
     * @param cardSkinDirectory must be one of the CardDisplayer static constants
     */
    public void setCardSkinDirectory(String cardSkinDirectory) throws IllegalArgumentException{
        if (cardSkinDirectory.equals(CARD_SKIN_V_1) || cardSkinDirectory.equals(CARD_SKIN_V_2)) {
            this.cardSkinDirectory = cardSkinDirectory;
        } else {
            throw new IllegalArgumentException(cardSkinDirectory + " is invalid");
        }
    }

    public String getCardSkinDirectory() {
        return cardSkinDirectory;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
