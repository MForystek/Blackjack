package gui;

import Database.Database;
import Database.SqliteDB;
import gameLogic.GameConfig;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NewGameWindow extends JFrame{
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTextField numOfHumansText;
    private JTextField numOfAIsText;
    private JTextField numOfDecksText;
    private JRadioButton a5SecondsRadioButton;
    private JRadioButton a10SecondsRadioButton;
    private JRadioButton a15SecondsRadioButton;
    private JComboBox gameModeComboBox;
    private JButton definePlayersButton;
    private ButtonGroup buttonGroup;

    private boolean status;
    private int numOfHumans;
    private int numOfAis;
    private int numOfDecks;
    private int timePerTurn;

    private GameConfig gameConfig;

    public NewGameWindow(Database database) {
        super("New game");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        gameConfig = new GameConfig(database);

        a15SecondsRadioButton.setSelected(true);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(a5SecondsRadioButton);
        buttonGroup.add(a10SecondsRadioButton);
        buttonGroup.add(a15SecondsRadioButton);

        definePlayersButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                //suppose everything is ok, then check some conditions and eventually set 'status' to false
                status = true;

                if (a5SecondsRadioButton.isSelected()) {
                    timePerTurn = 5;
                } else if(a10SecondsRadioButton.isSelected()) {
                    timePerTurn = 10;
                } else {
                    timePerTurn = 15;
                }

                try {
                     numOfHumans = Integer.parseInt(numOfHumansText.getText());
                } catch (NumberFormatException e) {
                    displayPopUpAndSetStatus("Amount of humans field is invalid" );
                }

                try {
                    numOfAis = Integer.parseInt(numOfAIsText.getText());
                } catch (NumberFormatException e) {
                   displayPopUpAndSetStatus("Amount of AIs field is invalid" );
                }

                try {
                    numOfDecks = Integer.parseInt(numOfDecksText.getText());
                } catch (NumberFormatException e) {
                   displayPopUpAndSetStatus( "Amount of Decks field is invalid" );
                }

                if (status) {
                    String errorMessage = gameConfig.validateAndSet(gameModeComboBox.getSelectedItem().toString(),
                            numOfHumans,
                            numOfAis,
                            numOfDecks,
                            timePerTurn
                    );

                    if (gameConfig.isValid()) {
                        new DefinePlayersWindow(gameConfig);
                        dispose();
                    } else {
                        displayPopUpAndSetStatus(errorMessage);
                    }

                }
            }
        });
    }

    public void displayPopUpAndSetStatus (String message) {
        if (status && message != null) {
            JOptionPane.showMessageDialog(mainPanel, message);
        }
        status = false;
    }
}
