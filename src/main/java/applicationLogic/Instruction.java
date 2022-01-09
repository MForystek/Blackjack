package applicationLogic;

public class Instruction {
    private static String instruction = """
            Deck has 52 cards.
            Values of cards:
                - 2-9 -> 2-9,
                - 10/Jack/Queen/King -> 10,
                - Ace -> 11/1 (which is better for the player).
            Available amount of players 1-4.
            Available amount of decks 1-8.
            
            Preparations:
            Dealer draws 2 cards. First is hidden the second one is visible to everyone.
            Each player draws 2 cards. Both cards are visible to everyone.
            
            Players' turns:
            In each turn player can either draw next card or pass.
            If player pass they he/she can't continue the game.
            Game ends when every player passes or have more than 21 points.
            
            Dealer turn:
            Dealers turn begins after all player passed or exceeded 21 points.
            Dealer draws cards until he has 17 or more points.
            
            Results:
            If someone has Ace and King/Queen/Jack/10 we call it Blackjack.
            If dealer have Blackjack all players lose.
            If player have Blackjack and dealer hasn't then that player wins.
            If dealer have more than 21 points then everyone who has less then 21 points wins;
            If dealer have less then 21 points then everyone who has less than 21 points and more points than dealer wins.
            
            """;

    public static String getInstruction() {
        return instruction;
    }
}
