package gui;

import applicationLogic.ApplicationData;
import gameLogic.GameManager;
import gameLogic.cards.Card;
import gameLogic.players.Dealer;
import gameLogic.players.Player;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HalfCasinoGameWindow extends JFrame implements GameWindow {
    private JPanel mainPanel;
    private JPanel player1Panel;
    private JPanel player4Panel;
    private JPanel player3Panel;
    private JPanel player2Panel;
    private JPanel dealersCardsPanel;
    private JLabel hiddenCardLabel;
    private JLabel visibleCardLabel;
    private JLabel player1TurnMarkerLabel;
    private JLabel player4TurnMarkerLabel;
    private JLabel player3TurnMarkerLabel;
    private JLabel player2TurnMarkerLabel;
    private JList<Icon> player1CardsJList;
    private JList<Icon> player2CardsJList;
    private JList<Icon> player3CardsJList;
    private JList<Icon> player4CardsJList;
    private JList<Icon> dealersCardsJList;
    private JLabel dealersPointsLabel;
    private JPanel otherPanel;
    private JPanel dealerPanel;
    private volatile JLabel countdownLabel;
    private JPanel playersPanel;
    private JPanel gameButtonsPanel;
    private JButton backToMainMenuButton;
    private JButton drawButton;
    private JButton passButton;
    private JPanel backToMainMenuPanel;
    private JLabel player2PointsLabel;
    private JLabel player3PointsLabel;
    private JLabel player4PointsLabel;
    private JLabel player1PointsLabel;
    private JPanel dealersPointsPanel;
    private JPanel countdownPanel;

    private DefaultListModel<Icon> dealersListModel;
    private DefaultListModel<Icon> player1ListModel;
    private DefaultListModel<Icon> player2ListModel;
    private DefaultListModel<Icon> player3ListModel;
    private DefaultListModel<Icon> player4ListModel;
    private ApplicationData applicationData;
    private GameManager gameManager;
    private Dealer dealer;
    private List<Player> players;
    private volatile AtomicInteger countdown;
    private Thread countdownGuiThread;

    public HalfCasinoGameWindow() {
        super("Blackjack");
        setContentPane(this.mainPanel);
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

        configureJLists();

        this.applicationData = ApplicationData.getInstance();

        gameManager = new GameManager(this);
        initializeCountdownGuiThread();

        gameManager.start();
        countdownGuiThread.start();
    }

    private void initializeCountdownGuiThread() {
        countdownGuiThread = new Thread(() -> {
            while(true) {
                try {
                    if (countdown != null) {
                        countdownLabel.setText(String.valueOf(countdown));
                    } else {
                        countdownLabel.setText("");
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }

    private void configureJLists() {
        dealersCardsJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        player1CardsJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        player2CardsJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        player3CardsJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        player4CardsJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        dealersCardsJList.setVisibleRowCount(1);
        player1CardsJList.setVisibleRowCount(1);
        player2CardsJList.setVisibleRowCount(1);
        player3CardsJList.setVisibleRowCount(1);
        player4CardsJList.setVisibleRowCount(1);

        dealersListModel = new DefaultListModel<>();
        player1ListModel = new DefaultListModel<>();
        player2ListModel = new DefaultListModel<>();
        player3ListModel = new DefaultListModel<>();
        player4ListModel = new DefaultListModel<>();

        dealersCardsJList.setModel(dealersListModel);
        player1CardsJList.setModel(player1ListModel);
        player2CardsJList.setModel(player2ListModel);
        player3CardsJList.setModel(player3ListModel);
        player4CardsJList.setModel(player4ListModel);
    }

    public void showBeginningResults() {
        //TODO show beginning results after game started in backend
        takeReferences();
        showBeginningDealer();
        showPlayers();
    }

    private void takeReferences() {
        this.dealer = applicationData.getGameConfig().getDealer();
        this.players = applicationData.getGameConfig().getPlayers();
        this.countdown = gameManager.getCountdown();
    }

    private void showBeginningDealer() {
        showDealersHiddenCard();
        showDealersVisibleCard();
        dealersPointsLabel.setText("Dealer's points: " + dealer.getVisibleCard().getValue());
    }

    private void showDealersHiddenCard() {
        String path = applicationData.getCardDisplayer().getBaseDirectory() + "\\reverse.jpg";
        hiddenCardLabel.setIcon(new ImageIcon(path));
        hiddenCardLabel.setText("Hidden Card");
    }

    private void showDealersVisibleCard() {
        String path = String.valueOf(applicationData.getCardDisplayer().getFilePath(dealer.getVisibleCard()));
        visibleCardLabel.setIcon(new ImageIcon(path));
        visibleCardLabel.setText("Visible Card");
    }

    private void showPlayers() {
        adjustAmountOfPlayerPanels();
        showBeginningPlayersCards();
    }

    private void adjustAmountOfPlayerPanels() {
        player4Panel.setVisible(players.size() >= 4);
        player3Panel.setVisible(players.size() >= 3);
        player2Panel.setVisible(players.size() >= 2);
    }

    private void showBeginningPlayersCards() {
        player1Panel.setBorder(BorderFactory.createTitledBorder(player1Panel.getBorder(), players.get(0).getNick()));
        player1PointsLabel.setText("Points: " + players.get(0).getTotalPoints());
        player1TurnMarkerLabel.setIcon(new ImageIcon(applicationData.getCardDisplayer().getBaseDirectory() + "\\marker.jpg"));
        addCardsToJList(players.get(0),  player1ListModel);
        if (player2Panel.isVisible()) {
            player2Panel.setBorder(BorderFactory.createTitledBorder(player1Panel.getBorder(), players.get(1).getNick()));
            player2PointsLabel.setText("Points: " + players.get(1).getTotalPoints());
            addCardsToJList(players.get(1),  player2ListModel);
        }
        if (player3Panel.isVisible()) {
            player3Panel.setBorder(BorderFactory.createTitledBorder(player1Panel.getBorder(), players.get(2).getNick()));
            player3PointsLabel.setText("Points: " + players.get(2).getTotalPoints());
            addCardsToJList(players.get(2),  player3ListModel);
        }
        if (player4Panel.isVisible()) {
            player4Panel.setBorder(BorderFactory.createTitledBorder(player1Panel.getBorder(), players.get(3).getNick()));
            player4PointsLabel.setText("Points: " + players.get(3).getTotalPoints());
            addCardsToJList(players.get(3),  player4ListModel);
        }
    }

    private void addCardsToJList(Player player, DefaultListModel<Icon> listModel) {
        listModel.clear();
        for (int i = 0; i < player.getCards().size(); i++) {
            Card card = player.getCards().get(i);
            String path = String.valueOf(applicationData.getCardDisplayer().getFilePath(card));
            Icon cardIcon = new ImageIcon(path);
            listModel.addElement(cardIcon);
        }
    }

    public void showTurnResults() {
        //TODO show turn results after turn ends
        //TODO bots should move and be displayed automatically
        dealersPointsLabel.setText(String.valueOf(dealer.getTotalPoints()));
        addCardsToJList(dealer, dealersListModel);
        player1PointsLabel.setText("Points: " + players.get(0).getTotalPoints());
        addCardsToJList(players.get(0), player1ListModel);
        if (player2Panel.isVisible()) {
            player2PointsLabel.setText("Points: " + players.get(1).getTotalPoints());
            addCardsToJList(players.get(1), player2ListModel);
        }
        if (player3Panel.isVisible()) {
            player3PointsLabel.setText("Points: " + players.get(2).getTotalPoints());
            addCardsToJList(players.get(2), player3ListModel);
        }
        if (player4Panel.isVisible()) {
            player4PointsLabel.setText("Points: " + players.get(3).getTotalPoints());
            addCardsToJList(players.get(3), player4ListModel);
        }
    }

    public void showGameResults() {
        //TODO show game results after game ends
        //TODO show Dealers moves
        //TODO show next window with game summary
        //TODO close this window
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
