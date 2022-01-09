package gameLogic;

import gameLogic.cards.Card;

import java.util.Optional;

public interface TurnChoice {
    Optional<Card> draw();
    void pass();
}
