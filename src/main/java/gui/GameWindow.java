package gui;

import gameLogic.GameConfig;

import javax.swing.*;

public class GameWindow extends JFrame{
    private GameConfig gameConfig;
    private JPanel mainPanel;

    public GameWindow(GameConfig gameConfig) {
        super("Blackjack");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        this.gameConfig = gameConfig;
    }
}
