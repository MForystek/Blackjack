package gui;

import applicationLogic.Statistics;
import database.Database;
import database.SqliteDB;
import gameLogic.cards.CardValues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StatisticsWindow extends JFrame{
    private JPanel mainPanel;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton backButton;

    public  StatisticsWindow (Database db){
        super("Statistics");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(1500,1000);
        setVisible(true);
        textArea.setEditable(false);
        Scrollbar scrollbar = new Scrollbar();
        textArea.add(scrollbar);
        loadStatisticsFromDatabase(db);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainWindow();
            }
        });
    }

    public static void main(String[] args) {
        new StatisticsWindow(new SqliteDB());
    }

    private void loadStatisticsFromDatabase(Database db){
        textArea.append(
                "Nick" + "\t" + "Win Rate" + "\t" + "Games played"+ "\t" + "Game Time" + "\t" +
                        "2's pulled" + "\t" +
                        "3's pulled" + "\t" +
                        "4's pulled" + "\t" +
                        "5's pulled" + "\t" +
                        "6's pulled" + "\t" +
                        "7's pulled" + "\t" +
                        "8's pulled" + "\t" +
                        "9's pulled" + "\t" +
                        "10's pulled" + "\t" +
                        "Jacks pulled" + "\t" +
                        "Queens pulled" + "\t" +
                        "Kings pulled" + "\t" +
                        "Aces pulled" + "\n"
                );
        db.openConnection();
        ArrayList<String> nicks = db.getAllNicks();
        db.closeConnection();
        for (String nick : nicks) {
            try {
                db.openConnection();
                Statistics statistics = db.getStatistics(nick);
                db.closeConnection();
                textArea.append(
                        nick + "\t" +
                                statistics.getWinRate() + "%\t" +
                                statistics.getNumberOfGames() + "\t" +
                                statistics.getGameTime() + "\t" +
                                statistics.getCardOccurrence(CardValues.TWO) + "\t" +
                                statistics.getCardOccurrence(CardValues.THREE) + "\t" +
                                statistics.getCardOccurrence(CardValues.FOUR) + "\t" +
                                statistics.getCardOccurrence(CardValues.FIVE) + "\t" +
                                statistics.getCardOccurrence(CardValues.SIX) + "\t" +
                                statistics.getCardOccurrence(CardValues.SEVEN) + "\t" +
                                statistics.getCardOccurrence(CardValues.EIGHT) + "\t" +
                                statistics.getCardOccurrence(CardValues.NINE) + "\t" +
                                statistics.getCardOccurrence(CardValues.TEN) + "\t" +
                                statistics.getCardOccurrence(CardValues.JACK) + "\t" +
                                statistics.getCardOccurrence(CardValues.QUEEN) + "\t" +
                                statistics.getCardOccurrence(CardValues.KING) + "\t" +
                                (statistics.getCardOccurrence(CardValues.ACE1) + statistics.getCardOccurrence(CardValues.ACE11)) + "\n"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
