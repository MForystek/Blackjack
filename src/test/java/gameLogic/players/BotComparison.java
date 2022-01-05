package gameLogic.players;

import gameLogic.cards.Card;
import gameLogic.cards.Decks;

import java.util.Stack;

public class BotComparison {
    private EasyBot easyBot;
    private MediumBot mediumBot;
    private HardBot hardBot;
    private Dealer dealer;
    private Decks decks;
    private int numberOfDecks;
    private int easyBotWins;
    private int mediumBotWins;
    private int hardBotWins;
    private int dealerWins;
    private int numOfGames;

    public BotComparison(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
        setNewDeck();
    }

    public void playGame(Player player) {
        numOfGames++;

        while (!player.isEnded()) {
            makeTurn(player);
        }

        while (!dealer.isEnded()) {
            makeTurn(dealer);
        }
        updateStats(player, isBotWinner(player));
        player.clearGameData();
        dealer.clearGameData();
    }

    public void playWithEasyBot(){
        Decks decks = cloneDeck();
        Card dealerCard = decks.takeNextCard();

        dealer = new Dealer(dealerCard, decks.takeNextCard());

        easyBot = new EasyBot();
        easyBot.addCard(decks.takeNextCard());
        easyBot.addCard(decks.takeNextCard());

        playGame(easyBot);
    }

    public void playWithMediumBot(){
        Decks decks = cloneDeck();
        Card dealerCard = decks.takeNextCard();

        dealer = new Dealer(dealerCard, decks.takeNextCard());

        mediumBot = new MediumBot(decks, dealerCard);
        mediumBot.addCard(decks.takeNextCard());
        mediumBot.addCard(decks.takeNextCard());

        playGame(mediumBot);
    }

    public void playWithHardBot(){
        Decks decks = cloneDeck();
        Card dealerCard = decks.takeNextCard();

        dealer = new Dealer(dealerCard, decks.takeNextCard());

        hardBot = new HardBot(decks);
        hardBot.addCard(decks.takeNextCard());
        hardBot.addCard(decks.takeNextCard());

        playGame(hardBot);
    }

    // +1 bot wins, -1 bot loses, 0 draw
    public int isBotWinner(Player bot) {
        if (dealer.haveBlackjack() && !bot.haveBlackjack()) {
            return -1;
        }
        if (bot.haveBlackjack() && !dealer.haveBlackjack()) {
            return 1;
        }
        if (bot.haveBlackjack() && dealer.haveBlackjack()) {
            return 0;
        }

        int dealerPoints = dealer.getTotalPoints();
        int botPoints = bot.getTotalPoints();

        if (botPoints > 21 && dealerPoints <= 21) {
            return -1;
        }

        if (botPoints <= 21 && dealerPoints > 21) {
            return 1;
        }

        if (botPoints == dealerPoints) {
            return 0;
        }

        if (botPoints > dealerPoints) {
            return 1;
        } else {
            return -1;
        }
    }

    public void makeTurn(Player player){
        if (player.wantToDrawCard()) {
            player.addCard(decks.takeNextCard());
        }
    }

    public void updateStats(Player player, int gameResultCode) {
        if (player instanceof EasyBot) {
            if (gameResultCode == 1) {
                easyBotWins++;
            } else if (gameResultCode == -1) {
                dealerWins++;
            } else {
                easyBotWins++;
                dealerWins++;
            }
        }

        if (player instanceof MediumBot) {
            if (gameResultCode == 1) {
                mediumBotWins++;
            } else if (gameResultCode == -1) {
                dealerWins++;
            } else {
                mediumBotWins++;
                dealerWins++;
            }
        }

        if (player instanceof HardBot) {
            if (gameResultCode == 1) {
                hardBotWins++;
            } else if (gameResultCode == -1) {
                dealerWins++;
            } else {
                hardBotWins++;
                dealerWins++;
            }
        }
    }

    public void setNewDeck(){
        decks = new Decks(numberOfDecks);
    }

    public Decks cloneDeck(){
        Decks decksCopy = new Decks(numberOfDecks);
        decksCopy.setDecks((Stack<Card>) decks.getDecks().clone());
        return decksCopy;
    }

    public void displayStats() {
        System.out.println("number of games: " + numOfGames);
        System.out.println("EasyBot wins: " + easyBotWins);
        System.out.println("MediumBot wins: " + mediumBotWins);
        System.out.println("HardBot wins: " + hardBotWins);
        System.out.println("Dealer wins: " + dealerWins);
        System.out.println("Dealer/3 wins: " + dealerWins / 3);
    }

    public static void main(String [] args) {
        BotComparison botComparison = new BotComparison(1);
        for (int i = 0; i < 3000; i++) {
            botComparison.playWithEasyBot();
            botComparison.playWithMediumBot();
            botComparison.playWithHardBot();
            botComparison.setNewDeck();
        }

        botComparison.displayStats();
    }
}
