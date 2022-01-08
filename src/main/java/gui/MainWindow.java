package gui;

import database.Database;
import database.SqliteDB;

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

    private Database database;

    public MainWindow() {

        super("Blackjack");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        initDatabase();

        newGameLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new NewGameWindow(database);
                dispose();
            }
        });
        statisticsLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new StatisticsWindow(database);
                dispose();
            }
        });


        exitLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

    }

    public void initDatabase() {
        database = new SqliteDB();
    }

    public static void main(String [] args) {
        new MainWindow();
    }
}
