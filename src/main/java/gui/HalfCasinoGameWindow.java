package gui;

import gameLogic.GameConfig;

import javax.swing.*;

public class HalfCasinoGameWindow extends JFrame implements GameWindow {
    private GameConfig gameConfig;
    private JPanel mainPanel;

    public HalfCasinoGameWindow(GameConfig gameConfig) {
        super("Blackjack");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        this.gameConfig = gameConfig;
    }

    public void showBeginningResults() {
        //TODO show beginning results after game started in backend
    }

    public void showTurnResults() {
        //TODO show turn results after turn ends
    }

    public void showGameResults() {
        //TODO show game results after game ends
    }
}
