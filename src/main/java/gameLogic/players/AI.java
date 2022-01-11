package gameLogic.players;

public interface AI {
    boolean wantToDrawCard();
    //TODO in bots there is no method to actually perform card adding, bots are supposed to call draw() from TurnChoice interface to correctly draw the card
    //TODO the same problem is with dealer
}
