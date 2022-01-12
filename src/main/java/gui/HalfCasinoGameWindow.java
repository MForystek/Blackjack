package gui;

import applicationLogic.ApplicationData;
import gameLogic.GameManager;
import gameLogic.cards.CardDisplayer;
import gameLogic.players.Dealer;
import gameLogic.players.Player;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
public class HalfCasinoGameWindow extends JFrame implements GameWindow {
    private JPanel mainPanel;
    private JPanel otherPanel;
    private JPanel leftPanel;
    private JPanel dealerPanel;
    private DealerPanel dealerMainPanel;
    private volatile JLabel countdownLabel;
    private JPanel playersPanel;
    private PlayersMainPanel playersMainPanel;
    private JPanel gameButtonsPanel;
    private JButton backToMainMenuButton;
    private JButton drawButton;
    private JButton passButton;
    private JPanel backToMainMenuPanel;
    private JPanel countdownPanel;

    private ApplicationData applicationData;
    private GameManager gameManager;
    private Dealer dealer;
    private List<Player> players;
    private volatile AtomicInteger countdown;
    private Thread guiThread;

    public HalfCasinoGameWindow() {
        super("Blackjack");
        setContentPane(this.mainPanel);
        setPreferredSize(new Dimension(800, 660));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        backToMainMenuButton.addActionListener(e -> {
            ApplicationData.reset();
            new MainWindow();
            dispose();
            //TODO ensure that everything is closing in the right way
        });
        drawButton.addActionListener(e -> gameManager.getTurnChoice().draw());
        passButton.addActionListener(e -> gameManager.getTurnChoice().pass());


        this.applicationData = ApplicationData.getInstance();

        gameManager = new GameManager(this);
        initializeGuiThread();

        gameManager.start();
        guiThread.start();
    }

    private void initializeGuiThread() {
        guiThread = new Thread(() -> {
            while(true) {
                try {
                    if (countdown != null) {
                        countdownLabel.setText(String.valueOf(countdown));
                    } else {
                        countdownLabel.setText("");
                    }

                    if (gameManager.isHumanTurn()) {
                        drawButton.setVisible(true);
                        passButton.setVisible(true);
                    } else {
                        drawButton.setVisible(false);
                        passButton.setVisible(false);
                    }

                   // if (!gameManager.isEnded()) {
                        playersMainPanel.updatePanel(gameManager.getIndexOfCurrentPlayer());
                    //}

                    if (gameManager.isDealerTurn()) {
                        dealerMainPanel.showHiddenCard();
                        playersMainPanel.hideBuggyArrowForFirstPlayer();
                        if (dealer.isEnded()) {
                            dealerMainPanel.hideArrow();
                        } else {
                            dealerMainPanel.showArrow();
                        }
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }

    public void showBeginningResults() {
        takeReferences();
    }

    private void takeReferences() {
        this.dealer = applicationData.getGameConfig().getDealer();
        this.players = applicationData.getGameConfig().getPlayers();
        this.countdown = gameManager.getCountdown();
    }


    public void showTurnResults() {}

    public void showGameResults() {
        // TODO: check if game results is fine +
        // should be here: players.add(dealer) or GameSummaryWindow with constructor (Dealer, List<Players>)
        ApplicationData.reset();
        new GameSummaryWindow(players);
        dispose();
    }

    private void createUIComponents() {
        applicationData = ApplicationData.getInstance();
        playersMainPanel = new PlayersMainPanel(applicationData.getGameConfig().getPlayers());
        playersPanel = playersMainPanel;
        dealerMainPanel = new DealerPanel(
                applicationData.getGameConfig().getDealer(),
                applicationData.getCardDisplayer()
        );
        dealerPanel = dealerMainPanel;
    }



    // -----------------------------------------------------------
    // -----------------------------------------------------------
    // -----------------------------------------------------------

    class PlayersMainPanel extends JPanel{
        private List<Player> players;
        private GridBagConstraints constraints;
        private int displayedIndexOfPlayer = -1;
        private List <PlayerPanel> playerPanels;

        public PlayersMainPanel(List<Player> players) {
            super(new GridBagLayout());
            setBackground(Color.WHITE);
            this.players = players;

            constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.NORTHWEST;

            constraints.gridx = 0;
            constraints.gridy = 0;

            constraints.weightx = 1;
            constraints.weighty = 1;

            createPlayersPanels();
        }

        private void createPlayersPanels() {
            playerPanels = new ArrayList<>();
            for (Player player: players) {
                PlayerPanel playerPanel = new PlayerPanel(player, applicationData.getCardDisplayer());
                playerPanels.add(playerPanel);
                add(playerPanel, constraints);
                constraints.gridy++;
            }
        }

       public void updatePanel(int indexOfCurrentPlayer){
            if (indexOfCurrentPlayer != displayedIndexOfPlayer) {
                if (displayedIndexOfPlayer >= 0) {
                    playerPanels.get(displayedIndexOfPlayer).changeArrowState();
                }
                playerPanels.get(indexOfCurrentPlayer).changeArrowState();
                displayedIndexOfPlayer = indexOfCurrentPlayer;
            }
       }

       public void hideBuggyArrowForFirstPlayer() {
            playerPanels.get(0).hideArrow();
       }
    }

    private class PlayerPanel extends JPanel {
        private Player player;
        private CardPanel cardPanel;
        private InformationPanel infoPanel;

        private Thread guiRefresher;

        public PlayerPanel(Player player, CardDisplayer cardDisplayer) {
            super(new GridBagLayout());

            this.player = player;

            setBackground(Color.WHITE);
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);

            constraints.gridx = 0;
            constraints.gridy = 0;

            constraints.weightx = 1;
            constraints.weighty = 1;

            infoPanel = new InformationPanel(player);
            add(infoPanel,  constraints);
            constraints.gridy = 1;

            cardPanel = new CardPanel(player, cardDisplayer);
            add(cardPanel, constraints);

            initThreadAndStart();
        }
        private void initThreadAndStart(){
            Runnable refreshTask = () -> {
                while (!player.isEnded()) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cardPanel.update();
                    infoPanel.update();
                }
            };

            guiRefresher = new Thread(refreshTask);
            guiRefresher.start();
        }

        public void changeArrowState(){
            if (cardPanel.getArrowLabel().isVisible()) {
                cardPanel.getArrowLabel().setVisible(false);
            } else {
                cardPanel.getArrowLabel().setVisible(true);
            }
            if (player.isEnded()) {
                cardPanel.getArrowLabel().setVisible(false);
            }
        }

        public void hideArrow(){
            cardPanel.getArrowLabel().setVisible(false);
        }
    }



    private class CardPanel extends JPanel {
        private GridBagConstraints constraints;
        private Player player;
        private int numOfDisplayedCards;
        private CardDisplayer cardDisplayer;

        private JLabel arrowLabel;

        public CardPanel(Player player, CardDisplayer cardDisplayer) {
            super(new GridBagLayout());
            setBackground(Color.WHITE);

            this.player = player;
            this.cardDisplayer = cardDisplayer;
            numOfDisplayedCards = player.getCards().size();

            constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(0, 0, 0, 10);

            constraints.gridx = 0;
            constraints.gridy = 0;

            String path = Path.of("", "resources", "cardSkins", "marker2.png").toAbsolutePath().toString();
            arrowLabel = new JLabel(new ImageIcon(path));
            arrowLabel.setVisible(false);
            add(arrowLabel, constraints);

            constraints.gridx = 1;
        }


        public void update() {
            if (player.getCards().size() > numOfDisplayedCards) {
                int actualNumOfCards = player.getCards().size();

                for (int i = numOfDisplayedCards; i < actualNumOfCards;i++) {
                    addCard(cardDisplayer.getFilePath(player.getCards().get(i)).toString());
                }

                numOfDisplayedCards = actualNumOfCards;
            }
        }

        public void addCard(String path) {
            ImageIcon icon = new ImageIcon(path);
            JLabel label = new JLabel(icon);
            add(label, constraints);
            constraints.gridx++;
            updateUI();
        }

        public JLabel getArrowLabel() {
            return arrowLabel;
        }
    }

    private class InformationPanel extends JPanel {
        protected GridBagConstraints constraints;
        protected Player player;
        private int displayedTime = 0;
        private JLabel pointsLabel;
        private JLabel statusLabel;

        public InformationPanel(Player player) {
            super(new GridBagLayout());
            setBackground(Color.WHITE);

            this.player = player;

            constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(0,0,0,15);

            constraints.gridx = 0;
            constraints.gridy = 0;

            constraints.weightx = 1;
            constraints.weighty = 1;

            JLabel nick = new JLabel(player.getGameNick());
            add(nick, constraints);

            pointsLabel = new JLabel("Points: " + displayedTime);
            constraints.gridx = 1;
            add(pointsLabel, constraints);

            statusLabel = new JLabel("Status: Passed");
            statusLabel.setVisible(false);
            constraints.gridx = 2;
            add(statusLabel, constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
        }


        public void update() {
            if (player.getTotalPoints() > displayedTime) {
                displayedTime = player.getTotalPoints();
                pointsLabel.setText("Points: " + displayedTime);
            }
            if (player.isEnded()) {
                statusLabel.setVisible(true);
            }
        }
    }

    // Dealer ------------------------------------------

    private class DealerPanel extends JPanel {
        private Dealer dealer;
        private CardPanel cardPanel;
        private InformationPanel infoPanel;
        private JPanel specialCardsPanel;
        private JLabel hiddenCardLabel;
        private ImageIcon hiddenCardIcon;

        private Thread guiRefresher;

        public DealerPanel(Player player, CardDisplayer cardDisplayer) {
            super(new GridBagLayout());

            dealer = (Dealer)player;

            setBackground(Color.WHITE);
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);

            constraints.gridx = 0;
            constraints.gridy = 0;

            constraints.weightx = 1;
            constraints.weighty = 1;
            infoPanel = new InformationPanel(dealer);
            add(infoPanel,  constraints);
            constraints.gridy = 1;

            specialCardsPanel = new JPanel();
            specialCardsPanel.setBackground(Color.WHITE);

            hiddenCardIcon = new ImageIcon(
                    cardDisplayer.getFilePath(dealer.getHiddenCard()).toString());
            String path = Path.of("", "resources", "cardSkins", "reverse.jpg").toAbsolutePath().toString();
            hiddenCardLabel = new JLabel(new ImageIcon(path));
            specialCardsPanel.add(hiddenCardLabel);

            JLabel visibleCardLabel = new JLabel(new ImageIcon(
                    cardDisplayer.getFilePath(dealer.getVisibleCard()).toString()));
            specialCardsPanel.add(visibleCardLabel);

            add(specialCardsPanel, constraints);


            constraints.gridx = 0;
            constraints.gridy = 2;
            cardPanel = new CardPanel(dealer, cardDisplayer);
            add(cardPanel, constraints);

            initThreadAndStart();
        }

        private void initThreadAndStart(){
            Runnable refreshTask = () -> {
                while (!dealer.isEnded()) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cardPanel.update();
                    infoPanel.update();
                }
            };

            guiRefresher = new Thread(refreshTask);
            guiRefresher.start();
        }


        public void showArrow(){
            cardPanel.getArrowLabel().setVisible(true);
        }

        public void hideArrow(){
            cardPanel.getArrowLabel().setVisible(false);
        }

        public void showHiddenCard(){
            hiddenCardLabel.setIcon(hiddenCardIcon);
        }
    }
}
