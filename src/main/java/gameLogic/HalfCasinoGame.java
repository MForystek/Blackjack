package gameLogic;

import database.Database;
import gameLogic.cards.Card;
import gameLogic.cards.Decks;
import gameLogic.players.Dealer;
import gameLogic.players.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static gameLogic.players.Player.MAX_ALLOWED_POINTS_THRESHOLD;

public class HalfCasinoGame implements Game, TurnChoice {
    private boolean isDebug = false;

    private Database database;
    private long gameDurationInMilliseconds;
    private GameModes gameMode;

    private Dealer dealer;
    private List<Player> players;
    private int indexOfCurrentPlayer;
    private Player currentPlayer;
    private int numberOfPlayers;
    private Runnable countdownAndAdjusterRun;
    private Thread countdownAndAdjuster;
    private Decks decks;
    private volatile boolean isReadyToBePlayed;
    private boolean isEnded;
    private volatile AtomicInteger countdown;

    public static HalfCasinoGame createGame(Database database, int numberOfDecks, int numberOfPlayers, GameModes gameMode) {
        if (!isComplete(database, numberOfPlayers, numberOfDecks, gameMode)) {
            throw new IllegalArgumentException();
        }
        return new HalfCasinoGame(database, numberOfDecks, numberOfPlayers, gameMode);
    }

    private static boolean isComplete(Database database, int numberOfPlayers, int numberOfDecks, GameModes gameMode) {
        return checkDatabase(database)
                && checkNumberOfPlayers(numberOfPlayers)
                && checkNumberOfDecks(numberOfDecks)
                && checkGameMode(gameMode);
    }

    private static boolean checkDatabase(Database database) {
        return database != null;
    }

    private static boolean checkNumberOfPlayers(int numberOfPlayers) {
        return numberOfPlayers >= 1 && numberOfPlayers <= 4;
    }

    private static boolean checkNumberOfDecks(int numberOfDecks) {
        return numberOfDecks >= 1 && numberOfDecks <= 8;
    }

    private static boolean checkGameMode(GameModes gameMode) {
        return gameMode != null;
    }

    private HalfCasinoGame(Database database, int numberOfDecks, int numberOfPlayers, GameModes gameMode) {
        isReadyToBePlayed = false;
        this.database = database;
        this.gameMode = gameMode;
        isEnded = false;
        countdown = new AtomicInteger(0);
        decks = new Decks(numberOfDecks);
        dealer = new Dealer(decks.takeNextCard(), decks.takeNextCard());

        //order of operations matter
        initializePlayers(numberOfPlayers);
        initializeCountdown();
        isReadyToBePlayed = true;
    }

    private void initializePlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        indexOfCurrentPlayer = 0;
        players = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("Player" + i));
            players.get(i).clearGameData();
        }
    }

    private void initializeCountdown() {
        countdownAndAdjusterRun = () -> {
            try {
                countdown = new AtomicInteger(gameMode.getTimeForMove());
                while (countdown.intValue() > 0) {
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

    public void startPlaying() {
        gameDurationInMilliseconds = System.currentTimeMillis();

        dealer.setHiddenCard(decks.takeNextCard());
        dealer.addCard(decks.takeNextCard());

        players.forEach(player -> {
            player.addCard(decks.takeNextCard());
            player.addCard(decks.takeNextCard());
        });
    }

    public void makeTurn() {
        if (!players.stream().allMatch(Player::isEnded)) {
            players.stream().distinct().forEach(player -> makePlayerTurn());
        } else {
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
            countdownAndAdjuster = new Thread(countdownAndAdjusterRun);
            countdownAndAdjuster.start();
            countdownAndAdjuster.join();
            if (isDebug) {
                System.out.println("End of turn");
            }
        } catch (InterruptedException ignored) {}
    }

    @Override
    public Optional<Card> draw() {
        if (isNoRunningTurn()) {
            return Optional.empty();
        }

        try {
            if (isDebug) {
                System.out.println("Player " + currentPlayer.getNick() + " draw card");
            }

            Card nextCard = decks.takeNextCard();
            currentPlayer.addCard(nextCard);
            countdownAndAdjuster.interrupt();

            return Optional.of(nextCard);
        } catch (EmptyStackException e) {
            currentPlayer.setEnded();
            return Optional.empty();
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
        if (indexOfCurrentPlayer < numberOfPlayers - 1) {
            indexOfCurrentPlayer++;
        } else {
            indexOfCurrentPlayer = 0;
        }
    }

    private void makeDealerMoves() {
        while (!dealer.isEnded() && dealer.wantToDrawCard()) {
            try {
                dealer.addCard(decks.takeNextCard());
            } catch (EmptyStackException e) {
                dealer.setEnded();
            }
        }
    }

    public void endGame() {
        setWinners();
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
    public Map<String, Object> getBeginningResults() {
        Map<String, Object> references = new HashMap<>();
        references.put("dealersVisibleCard", dealer);
        references.put("dealerHidden", dealer);
        references.put("players", players);
        references.put("countdown", countdown);
        return references;
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
}
