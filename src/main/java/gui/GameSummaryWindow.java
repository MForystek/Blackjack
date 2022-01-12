package gui;

import gameLogic.cards.Card;
import gameLogic.cards.CardColors;
import gameLogic.cards.CardValues;
import gameLogic.players.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class GameSummaryWindow extends JFrame {

    private JPanel mainPanel;
    private JTextArea winners;
    private JTextArea losers;
    private JButton backToMainMenuButton;

    public GameSummaryWindow(List<Player> players) {
        super("Options");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(300, 500);

        printOutPlayers(players);

        backToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainWindow();
            }
        });
    }

    private void printOutPlayers(List<Player> players) {
        for (Player player : players.stream().sorted().toList()) {
            if (player.isWinner()) {
                winners.append(player.getGameNick() + ": " + player.getTotalPoints() + "pts\n");
            } else {
                losers.append(player.getGameNick() + ": " + player.getTotalPoints() + "pts\n");
            }
        }
    }


    public static void main(String[] args) {
        Player p1 = new Player("p1");
        p1.setIsWinner(true);
        p1.addCard(new Card(CardColors.DIAMONDS, CardValues.KING));

        Player p2 = new Player("p2");
        p2.setIsWinner(false);
        p2.addCard(new Card(CardColors.DIAMONDS, CardValues.TWO));

        Player p3 = new Player("p3");
        p3.setIsWinner(false);
        p3.addCard(new Card(CardColors.DIAMONDS, CardValues.FIVE));

        Player p4 = new Player("p4");
        p4.setIsWinner(false);
        p4.addCard(new Card(CardColors.DIAMONDS, CardValues.FOUR));

        new GameSummaryWindow(List.of(p1, p2, p3, p4));
    }

}
