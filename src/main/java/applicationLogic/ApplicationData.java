package applicationLogic;

import database.Database;
import database.SqliteDB;
import gameLogic.GameConfig;
import gameLogic.cards.CardDisplayer;

public class ApplicationData {
    private static ApplicationData appData;
    private GameConfig gameConfig;
    private CardDisplayer cardDisplayer;

    public static ApplicationData getInstance(){
        return appData == null ? appData = new ApplicationData(): appData;
    }

    private ApplicationData(){
        initApplication();
    }

    private void initApplication() {
        Database database = new SqliteDB();
        gameConfig = new GameConfig(database);
        cardDisplayer = new CardDisplayer();
    }


    public Database getDatabase() {
        return gameConfig.getDatabase();
    }

    public void setDatabase(Database database) {
        this.gameConfig.setDatabase(database);
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public CardDisplayer getCardDisplayer() {
        return cardDisplayer;
    }

    public void setCardDisplayer(CardDisplayer cardDisplayer) {
        this.cardDisplayer = cardDisplayer;
    }
}
