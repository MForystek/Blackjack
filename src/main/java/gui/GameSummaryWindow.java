package gui;

import gameLogic.cards.Card;
import gameLogic.cards.CardColors;
import gameLogic.cards.CardValues;
import gameLogic.players.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
        for (Player player : players) {
            if (player.isWinner()) {
                winners.append(player.getNick() + ": " + player.getTotalPoints());
            } else {
                losers.append(player.getNick() + ": " + player.getTotalPoints());
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

        new GameSummaryWindow(List.of(p1, p2));
    }

}
