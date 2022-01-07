package gameLogic;

import database.Database;
import database.SqliteDB;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    @Test
    void drawingCardDuringThePlayerTurnTest() {
        /*
        Card drawing logic:
        Method makePlayerTurn() starts thread countdownAndAdjust
        When the thread isAlive() and draw() is invoked from GUI then the card is drawn
        and the thread is interrupted and turn of the next player is started
         */

        Database database = new SqliteDB();
        GameManager gameManager = new GameManager(database, 2, 2, GameModes.MEDIUM);
        Drawator drawator = gameManager.getDrawator();

        gameManager.setDebug();
        gameManager.start();
        for (int i = 0; i < 10; i++) {
            try {
                if (drawator != null) {
                    drawator.draw();
                }
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}