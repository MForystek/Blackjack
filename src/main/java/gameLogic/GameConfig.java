package gameLogic;

import database.Database;
import database.SqliteDB;
import applicationLogic.Statistics;
import applicationLogic.User;
import gameLogic.cards.Decks;
import gameLogic.players.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameConfig {
    private static final int MAX_NUM_OF_PLAYERS = 4;
    private static final int MAX_NUM_OF_DECKS = 3;

    private Database database;

    private List<Player> players;
    private Dealer dealer;
    private Decks decks;

    private boolean isValid;
    private int numOfHumans;
    private int numOfAis;
    private int numOfDecks;
    private int timePerTurn;

    public GameConfig(Database database) {
        setDatabase(database);
        players = new ArrayList<>();
        isValid = false;
    }

    /**
     * @return if successful return null and set appropriate class attributes, else return error message (as String)
     */
    public String validateAndSet(String gameType, int numOfHumans, int numOfAis, int numOfDecks, int timePerTurn) {
        this.isValid = false;

        this.numOfHumans = -1;
        this.numOfAis = -1;
        this.numOfDecks = -1;

        if (numOfAis < 0 || numOfAis > MAX_NUM_OF_PLAYERS) {
            return "Amount of Ais must be integer value between 0 and 4";
        }

        if (numOfHumans < 0 || numOfHumans > MAX_NUM_OF_PLAYERS) {
            return "Amount of Humans must be integer value between 0 and 4";
        }

        if (numOfAis + numOfHumans > MAX_NUM_OF_PLAYERS) {
            return "Maximum numbers of players exceed " + MAX_NUM_OF_PLAYERS;
        }

        if (numOfDecks < 1 || numOfDecks > MAX_NUM_OF_DECKS) {
            return "Amount of Decks must be integer value between 1 and " + MAX_NUM_OF_DECKS;
        }

        switch (gameType) {
            case "Humans": {
                if (numOfHumans < 2) {
                    return "You must select at least two players";
                }
                if (numOfAis > 0) {
                    return "AIs can't play in selected game mode";
                }
            }
            break;
            case "Humans & AIs": {
                if (numOfAis + numOfHumans == 0) {
                    return "You must select at least one player";
                }
                if (numOfAis == 0 || numOfHumans == 0) {
                    return "You must select at least one player and at least one AI";
                }
            }
            break;
            case "AIs": {
                if (numOfAis == 0) {
                    return "You must select at least one player";
                }
                if (numOfHumans > 0) {
                    return "Humans can't play in selected game mode";
                }
            }
            break;
            default: return "Invalid game mode";

        }

        isValid = true;
        this.numOfAis = numOfAis;
        this.numOfHumans = numOfHumans;
        this.numOfDecks = numOfDecks;
        this.timePerTurn = timePerTurn;

        return null;
    }

    /**
     * @return if successful return null and add player to list, else return error message (as String)
     */
    public String validatePlayerAndAdd(String username, String password) {
        if (!isUserAdded(username)) {
            try {
                database.openConnection();
                if (database.login(username, password)) {
                    User user = new User(username, password);
                    addPlayer(user);
                    database.closeConnection();
                    return null;
                } else {
                    database.closeConnection();
                    return "Invalid username or password";
                }
            } catch (SQLException e) {
                database.closeConnection();
                return "Invalid username or password";
            }
        } else {
            return "User was added previously";
        }
    }

    public boolean isUserAdded(String username) {
        for (Player user : players) {
            if (user.getNick().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void addPlayer(Player player) {
        if (decks == null) {
            createDeck(numOfDecks);
        }
        if (dealer == null) {
            createDealer();
        }
        players.add(player);
    }

    public void createDeck(int numberOfDecks) {
        decks = new Decks(numberOfDecks);
    }

    public void createDealer() {
        dealer = new Dealer(decks.takeNextCard(), decks.takeNextCard());
        try {
            database.openConnection();
            Statistics statistics = database.getStatistics(dealer.getNick());
            database.closeConnection();

            dealer.setStatistics(statistics);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
    * @param  level one of the strings: "Easy", "Medium", "Hard"
     *@param nameSuffix int value which will be added at the end of name
    */
    public void createAI(String level, int nameSuffix) {
        if (decks == null) {
            createDeck(numOfDecks);
        }
        if (dealer == null) {
            createDealer();
        }

       Player ai = switch (level) {
            case "Easy" -> new EasyBot("Easy Bot " + nameSuffix);
            case "Medium" -> new MediumBot("Medium Bot " + nameSuffix, decks, getDealer().getVisibleCard());
            case "Hard" -> new HardBot("Hard Bot " + nameSuffix, decks);
            default -> throw new IllegalArgumentException("Level argument is invalid");
        };

        try {
            database.openConnection();
            Statistics statistics = database.getStatistics(ai.getNick());
            database.closeConnection();

            ai.setStatistics(statistics);
            players.add(ai);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- getters and setters ------------------

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public Decks getDecks() {
        return decks;
    }

    public void setDecks(Decks decks) {
        this.decks = decks;
    }

    public int getTimePerTurn() {
        return timePerTurn;
    }

    public void setTimePerTurn(int timePerTurn) {
        this.timePerTurn = timePerTurn;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int getNumOfHumans() {
        return numOfHumans;
    }

    public void setNumOfHumans(int numOfHumans) {
        this.numOfHumans = numOfHumans;
    }

    public int getNumOfAis() {
        return numOfAis;
    }

    public void setNumOfAis(int numOfAis) {
        this.numOfAis = numOfAis;
    }

    public int getNumOfDecks() {
        return numOfDecks;
    }

    public void setNumOfDecks(int numOfDecks) {
        this.numOfDecks = numOfDecks;
    }

    public SqliteDB getDatabase() {
        return (SqliteDB) database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public GameModes getGameMode() {
        return switch (getTimePerTurn()) {
            case 10 -> GameModes.MEDIUM;
            case 15 -> GameModes.HARD;
            default -> GameModes.EASY;
        };
    }

    public int getNumberOfPlayers() {
        return getNumOfHumans() + getNumOfAis();
    }
}
