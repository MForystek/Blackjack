package gui;

import gameLogic.GameConfig;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class DefinePlayersWindow extends JFrame{

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private int numOfPanels;
    private int currentPanelIndex = 1;
    private GameConfig gameConfig;

    public DefinePlayersWindow(GameConfig gameConfig) {

        this.gameConfig = gameConfig;

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        addPanelsToMainPanel();

        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    private void addPanelsToMainPanel() {

        for (int i = 0; i < gameConfig.getNumOfHumans(); i++) {
            JButton button = new JButton("Login");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showNextPanel();
                }
            });
            mainPanel.add(new PlayerSignInPanel(
                            gameConfig,
                            button,
                            numOfPanels + 1),
                            String.valueOf(numOfPanels)
            );
            numOfPanels ++;
        }

        for (int i = 0; i < gameConfig.getNumOfAis(); i++) {
            JButton button = new JButton("Next");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showNextPanel();
                }
            });
            mainPanel.add(new CreateAiPanel(
                            gameConfig,
                            button,
                            numOfPanels + 1 - gameConfig.getNumOfHumans()
                    ),
                    String.valueOf(numOfPanels)
            );
            numOfPanels ++;
        }

    }

    public void showNextPanel() {
        if (currentPanelIndex < numOfPanels) {
            cardLayout.show(mainPanel, String.valueOf(currentPanelIndex));
            currentPanelIndex++;
        } else {
            dispose();
            new HalfCasinoGameWindow(gameConfig);
        }
    }
    
    private class PlayerSignInPanel extends JPanel {
        private JLabel labelUsername = new JLabel("Enter username: ");
        private JLabel labelPassword = new JLabel("Enter password: ");
        private JTextField textUsername = new JTextField(20);
        private JPasswordField fieldPassword = new JPasswordField(20);
        private JButton buttonLogin = new JButton("Login");
        private JButton nextButton;

        private GameConfig gameConfig;
        private int playerId;

        public PlayerSignInPanel(GameConfig gameConfig, JButton button, int playerId) {
            super(new GridBagLayout());
            setBackground(Color.WHITE);

            this.gameConfig = gameConfig;
            nextButton = button;
            this.playerId = playerId;

            buttonLogin.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    validateUserAndSet();
                }
            });

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);

            constraints.gridx = 0;
            constraints.gridy = 0;
            add(labelUsername, constraints);

            constraints.gridx = 1;
            add(textUsername, constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            add(labelPassword, constraints);

            constraints.gridx = 1;
            add(fieldPassword, constraints);

            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 2;
            constraints.anchor = GridBagConstraints.CENTER;
            add(buttonLogin, constraints);

            setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(), "Player " + playerId));
        }

        public boolean validateUserAndSet() {
            if (getUsername().length() > 0 && getPassword().length() > 0) {
                String errorMessage = gameConfig.validatePlayerAndAdd(getUsername(), getPassword());
                if (errorMessage == null) {
                    nextButton.doClick();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, errorMessage);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Empty username or password");
            }
            return false;
        }

        public String getUsername() {
            return textUsername.getText();
        }

        public String getPassword() {
            return new String(fieldPassword.getPassword());
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(450, 350);
        }
    }

    private class CreateAiPanel extends JPanel {
        private JLabel labelUsername = new JLabel("Choose AI level");
        private JComboBox aiLevelComboBox;
        private JButton confirmButton = new JButton("Confirm");
        private JButton nextButton;

        private GameConfig gameConfig;
        private int playerId;

        public CreateAiPanel(GameConfig gameConfig,JButton button, int playerId) {
            super(new GridBagLayout());
            setBackground(Color.WHITE);

            this.gameConfig = gameConfig;
            nextButton = button;
            this.playerId = playerId;

            String aiLevels[] = { "Easy", "Medium", "Hard" };
            aiLevelComboBox = new JComboBox<>(aiLevels);

            confirmButton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    createAI();
                    nextButton.doClick();
                }
            });

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.insets = new Insets(10, 10, 10, 10);

            constraints.gridx = 0;
            constraints.gridy = 0;
            add(labelUsername, constraints);

            constraints.gridy = 1;
            add(aiLevelComboBox, constraints);

            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 2;
            add(confirmButton, constraints);

            setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(), "AI " + this.playerId));
        }

        public void createAI() {
            gameConfig.createAI(aiLevelComboBox.getSelectedItem().toString(), playerId);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(450, 350);
        }
    }
}