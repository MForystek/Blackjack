package gameLogic;

import database.Database;
import database.SqliteDB;
import gui.GameWindow;
import gui.HalfCasinoGameWindow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HalfCasinoGameTest {
    @Test
    void drawingCardDuringThePlayerTurnTest() {
        /*
        Card drawing logic:
        Method makePlayerTurn() starts thread countdownAndAdjust
        When the thread isAlive() and draw() is invoked from GUI then the card is drawn
        and the thread is interrupted and turn of the next player is started
         */

        //TODO Problem with GameManager constructor after changes related to ApplicationData and GameConfig

        /*
        Database database = new SqliteDB();
        GameManager gameManager = new GameManager(database, 3, 2, GameModes.MEDIUM, new HalfCasinoGameWindow());
        TurnChoice turnChoice = gameManager.getTurnChoice();

        gameManager.setDebug();
        gameManager.start();
        for (int i = 0; i < 10; i++) {
            try {
                if (turnChoice != null) {
                    if (i == 4) {
                        turnChoice.pass();
                    } else {
                        turnChoice.draw();
                    }
                }
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
    }
}