package gameLogic;

import gameLogic.cards.Card;

import java.util.Optional;

public interface Drawator {
    Optional<Card> draw();
}
