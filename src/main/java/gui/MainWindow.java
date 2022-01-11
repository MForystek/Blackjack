package gui;

import applicationLogic.ApplicationData;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame{
    private JLabel newGameLabel;
    private JLabel createAccountLabel;
    private JPanel mainPanel;
    private JLabel statisticsLabel;
    private JLabel optionsLabel;
    private JLabel exitLabel;

    private ApplicationData appData;

    public MainWindow() {
        super("Blackjack");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        initApplicationData();
        addListeners();
    }

    public void initApplicationData () {
        appData = ApplicationData.getInstance();
    }


    public void addListeners(){
        newGameLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new NewGameWindow();
                dispose();
            }
        });

        optionsLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new OptionsWindow();
                dispose();
            }
        });

        statisticsLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new StatisticsWindow(appData.getDatabase());
                dispose();
            }
        });

        createAccountLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new RegisterWindow(appData.getDatabase());
                dispose();
            }
        });

        exitLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String [] args) {
        new MainWindow();
    }
}
