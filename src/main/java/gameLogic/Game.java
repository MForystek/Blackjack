package gameLogic;

import Database.Database;
import gameLogic.cards.Decks;
import gameLogic.players.Dealer;
import gameLogic.players.Player;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.function.Predicate;

import static gameLogic.players.Player.MAX_ALLOWED_POINTS_THRESHOLD;

public class Game {
    public static final int MAX_NUMBER_OF_PLAYERS = 4;

    private Database database;
    private long gameDurationInMilliseconds;
    private GameModes gameMode;

    private Dealer dealer;
    private List<Player> players;
    private int indexOfCurrentPlayer;
    private int numberOfPlayers;
    private Decks decks;
    private boolean isEnded;

    public Game(Database database, int numberOfDecks, int numberOfPlayers, GameModes gameMode) {
        this.database = database;
        this.gameMode = gameMode;
        isEnded = false;
        decks = new Decks(numberOfDecks);
        dealer = new Dealer();
        initializePlayers(numberOfPlayers);
    }

    private void initializePlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        indexOfCurrentPlayer = 0;
        players = new ArrayList<>();
        correctNumberOfPlayers();

        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("Player" + i));
            players.get(i).clearGameData();
        }
    }

    private void correctNumberOfPlayers() {
        if (numberOfPlayers > MAX_NUMBER_OF_PLAYERS) {
            numberOfPlayers = MAX_NUMBER_OF_PLAYERS;
        }
    }

    public void startGame() {
        gameDurationInMilliseconds = System.currentTimeMillis();

        dealer.setHiddenCard(decks.takeNextCard());
        dealer.addCard(decks.takeNextCard());

        players.forEach(player -> {
            player.addCard(decks.takeNextCard());
            player.addCard(decks.takeNextCard());
        });
    }

    public void makeTurn() {
        if (players.stream().allMatch(Player::isEnded)) {
            makeDealerMoves();
            isEnded = true;
        }
        players.forEach(player -> {
            makePlayerMove();
        });
    }

    private void makePlayerMove() {
        Player currentPlayer = players.get(indexOfCurrentPlayer);
        if (currentPlayer.getTotalPoints() >= MAX_ALLOWED_POINTS_THRESHOLD) {
            currentPlayer.setEnded();
        }
        if (currentPlayer.isEnded()) {
            return;
        }
        //TODO wymyślić jak rozwiązać problem czy gracz chce dobrać kartę i jak to połączyć z GUI
        if (currentPlayer.wantToDrawCard()) {
            try {
                currentPlayer.addCard(decks.takeNextCard());
            } catch (EmptyStackException e) {
                currentPlayer.setEnded();
            }
        }
        adjustIndexOfTheCurrentPlayer();
    }

    private void adjustIndexOfTheCurrentPlayer() {
        if (indexOfCurrentPlayer >= numberOfPlayers) {
            indexOfCurrentPlayer = 0;
        } else {
            indexOfCurrentPlayer++;
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

    public void setWinners() {
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

    public boolean isEnded() {
        return isEnded;
    }
}
