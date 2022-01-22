package gameLogic;

import applicationLogic.ApplicationData;
import applicationLogic.Statistics;
import database.Database;
import gameLogic.cards.Card;
import gameLogic.cards.CardValues;
import gameLogic.cards.Decks;
import gameLogic.players.AI;
import gameLogic.players.Dealer;
import gameLogic.players.Player;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static gameLogic.players.Player.MAX_ALLOWED_POINTS_THRESHOLD;

public class HalfCasinoGame implements Game, TurnChoice {
    private boolean isDebug = false;
    private boolean isDealerTurn = false;
    private boolean isHumanTurn = false;

    private GameConfig gameConfig;

    private Database database;
    private long gameDurationInMilliseconds;
    private GameModes gameMode;

    private Dealer dealer;
    private List<Player> players;
    private int indexOfCurrentPlayer;
    private Player currentPlayer;
    private int numberOfPlayers;
    private Runnable countdownAndAdjusterRun;
    private Runnable botCountdownRun;
    private Thread countdownAndAdjuster;
    private Decks decks;
    private volatile boolean isReadyToBePlayed;
    private boolean isEnded;
    private volatile AtomicInteger countdown;

    public static HalfCasinoGame createGame() {
        GameConfig gameConfig = ApplicationData.getInstance().getGameConfig();

        if (!gameConfig.isValid()) {
            throw new IllegalArgumentException();
        }
        return new HalfCasinoGame();
    }

    private HalfCasinoGame() {
        gameConfig = ApplicationData.getInstance().getGameConfig();
        isReadyToBePlayed = false;
        this.database = gameConfig.getDatabase();
        this.gameMode = gameConfig.getGameMode();
        isEnded = false;
        countdown = new AtomicInteger(0);
        decks = gameConfig.getDecks();
        dealer = gameConfig.getDealer();

        //order of operations matter
        initializePlayers();
        initializeCountdown();
        initializeCountdownForBots();
        isReadyToBePlayed = true;
    }

    private void initializePlayers() {
        numberOfPlayers = gameConfig.getNumberOfPlayers();
        indexOfCurrentPlayer = 0;
        players = gameConfig.getPlayers();
    }

    private void initializeCountdown() {
        countdownAndAdjusterRun = () -> {
            try {
                countdown.set(gameMode.getTimeForMove());
                while (countdown.intValue() >= 0) {
                    Thread.sleep(1000);
                    countdown.decrementAndGet();
                }
                adjustIndexOfTheCurrentPlayer();
                if (isDebug) {
                    System.out.println("Countdown stop");
                }
            } catch (InterruptedException e) {
                adjustIndexOfTheCurrentPlayer();
            }
        };
    }

    private void initializeCountdownForBots() {
        botCountdownRun = () -> {
            try {
                countdown.set(gameMode.getTimeForMove());
                while (countdown.intValue() >= gameMode.getTimeForMove() - 1) {
                    Thread.sleep(1000);
                    countdown.decrementAndGet();
                }
                if (isDebug) {
                    System.out.println("Bot countdown stop");
                }
            } catch (InterruptedException e) {
                adjustIndexOfTheCurrentPlayer();
            }
        };
    }

    public void startPlaying() {
        gameDurationInMilliseconds = System.currentTimeMillis();

        players.forEach(player -> {
            player.addCard(decks.takeNextCard());
            player.addCard(decks.takeNextCard());
        });
    }

    public void makeTurn() {
        if (!players.stream().allMatch(Player::isEnded)) {
            players.stream().distinct().forEach(player -> makePlayerTurn());
        } else {
            isDealerTurn = true;
            makeDealerMoves();
            isEnded = true;
        }
    }

    private void makePlayerTurn() {
        currentPlayer = players.get(indexOfCurrentPlayer);

        if (!currentPlayer.isEnded() && currentPlayer.getTotalPoints() >= MAX_ALLOWED_POINTS_THRESHOLD) {
            currentPlayer.setEnded();
        }
        if (currentPlayer.isEnded()) {
            if (isDebug) {
                System.out.println("Player " + currentPlayer.getNick() + " ended");
            }
            adjustIndexOfTheCurrentPlayer();
            return;
        }

        try {
            if (isDebug) {
                System.out.println("Countdown start");
            }

            if(currentPlayer instanceof AI) {
                countdownAndAdjuster = new Thread(botCountdownRun);
                countdownAndAdjuster.start();
                countdownAndAdjuster.join();

                if (currentPlayer.wantToDrawCard()) {
                    try {
                        Card nextCard = decks.takeNextCard();
                        currentPlayer.addCard(nextCard);
                    } catch (EmptyStackException e) {
                        currentPlayer.setEnded();
                    }
                } else {
                    currentPlayer.setEnded();
                }

                adjustIndexOfTheCurrentPlayer();
            } else {
                isHumanTurn = true;
                countdownAndAdjuster = new Thread(countdownAndAdjusterRun);
                countdownAndAdjuster.start();
                countdownAndAdjuster.join();
                isHumanTurn = false;
            }

            if (isDebug) {
                System.out.println("End of turn");
            }

        } catch (InterruptedException ignored) {}
    }

    @Override
    public void draw() {
        if (isNoRunningTurn()) {
            return;
        }

        try {
            if (isDebug) {
                System.out.println("Player " + currentPlayer.getNick() + " draw card");
            }
            Card nextCard = decks.takeNextCard();
            currentPlayer.addCard(nextCard);
            countdownAndAdjuster.interrupt();
        } catch (EmptyStackException e) {
            currentPlayer.setEnded();
        }
    }

    private boolean isNoRunningTurn() {
        return countdownAndAdjuster == null || !countdownAndAdjuster.isAlive();
    }

    @Override
    public void pass() {
        if (isNoRunningTurn()) {
            return;
        }
        if (isDebug) {
            System.out.println("Player " + currentPlayer.getNick() + " passed");
        }
        currentPlayer.setEnded();
        countdownAndAdjuster.interrupt();
    }

    private void adjustIndexOfTheCurrentPlayer() {
        dropAceValueIfToMuchPoints();
        if (indexOfCurrentPlayer < numberOfPlayers - 1) {
            indexOfCurrentPlayer++;
        } else {
            indexOfCurrentPlayer = 0;
        }
    }

    public void dropAceValueIfToMuchPoints() {
        if (currentPlayer.getTotalPoints() > MAX_ALLOWED_POINTS_THRESHOLD) {
            for (Card card: currentPlayer.getCards()) {
                if (card.getCardValue().equals(CardValues.ACE11)) {
                    card.changeAceValue();
                    break;
                }
            }
        }
    }

    private void makeDealerMoves() {
        dealer.setDealerTurns(true);
        while (!dealer.isEnded()) {
            try {
                countdownAndAdjuster = new Thread(botCountdownRun);
                countdownAndAdjuster.start();
                countdownAndAdjuster.join();

                if (dealer.wantToDrawCard()) {
                    dealer.addCard(decks.takeNextCard());
                } else {
                    dealer.setEnded();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (EmptyStackException e) {
                dealer.setEnded();
            }
        }
    }

    public void endGame() {
        setWinners();
        for (Player player : players) {
            try {
                database.openConnection();
                Statistics tmp = database.getStatistics(player.getNick());
                tmp.updateStatistics(player.isWinner(), player.getCards(), gameDurationInMilliseconds/1000);
                database.setStatistics(player.getNick(), tmp);
                database.closeConnection();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        try {
            database.openConnection();
            Statistics tmp = database.getStatistics(dealer.getNick());
            dealer.addCard(dealer.getVisibleCard());
            dealer.addCard(dealer.getHiddenCard());
            tmp.updateStatistics(dealer.isWinner(), dealer.getCards(), gameDurationInMilliseconds/1000);
            database.setStatistics(dealer.getNick(), tmp);
            database.closeConnection();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void setWinners() {
        if (!dealer.haveBlackjack()) {
            makePlayersWithBlackjackWinners();
        }
        if (dealer.getTotalPoints() > MAX_ALLOWED_POINTS_THRESHOLD) {
            makePlayersWithAllowedAmountOfPointsWinners();
        }
        makePlayersWithAllowedAmountAndMorePointsThanDealerWinners();
        gameDurationInMilliseconds = System.currentTimeMillis() - gameDurationInMilliseconds;
    }

    private void makePlayersWithBlackjackWinners() {
        players.stream()
                .filter(Player::haveBlackjack)
                .forEach(Player::setWinner);
    }

    private void makePlayersWithAllowedAmountOfPointsWinners() {
        players.stream()
                .filter(hasAllowedAmountOfPoints())
                .forEach(Player::setWinner);
    }

    private Predicate<Player> hasAllowedAmountOfPoints() {
        return player -> player.getTotalPoints() <= MAX_ALLOWED_POINTS_THRESHOLD;
    }

    private void makePlayersWithAllowedAmountAndMorePointsThanDealerWinners() {
        players.stream()
                .filter(hasAllowedAmountOfPoints().and(hasMorePointsThanDealer()))
                .forEach(Player::setWinner);
    }

    private Predicate<Player> hasMorePointsThanDealer() {
        return player -> player.getTotalPoints() > dealer.getTotalPoints();
    }

    @Override
    public AtomicInteger getCountdown() {
        return countdown;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public boolean isReadyToBePlayed() {
        return isReadyToBePlayed;
    }

    public void setDebug() {
        isDebug = !isDebug;
    }

    public long getGameDurationInMilliseconds() {
        if (isEnded) {
            return gameDurationInMilliseconds;
        }
        return 0;
    }

    public int getIndexOfCurrentPlayer() {
        return indexOfCurrentPlayer;
    }

    public boolean isDealerTurn() {
        return isDealerTurn;
    }

    public boolean isHumanTurn() {
        return isHumanTurn;
    }
}
