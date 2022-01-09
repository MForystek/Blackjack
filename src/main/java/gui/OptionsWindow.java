package gui;

import applicationLogic.ApplicationData;
import database.Database;
import gameLogic.cards.CardDisplayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OptionsWindow extends JFrame{
    private JPanel mainPanel;
    private JRadioButton skinV1RadioButton;
    private JRadioButton skinV2RadioButton;
    private JLabel skinV1Label;
    private JLabel skinV2Label;
    private JButton changePasswordButton;
    private JButton backToMainMenuButton;

    private ApplicationData appData;

    public OptionsWindow (){
        super("Options");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        appData = ApplicationData.getInstance();

        //ImageIcon skinV1 = createImageIcon(appData.getCardDisplayer().getPreviewCardForV1().toString());

        ImageIcon skinV2 = new ImageIcon(appData.getCardDisplayer().getPreviewCardForV2().toString());

        //skinV1Label = new JLabel(skinV1);
        skinV2Label = new JLabel(skinV2);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(skinV1RadioButton);
        buttonGroup.add(skinV2RadioButton);

        manageRadioButtons();

        backToMainMenuButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new MainWindow();
            }
        });

    }

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void manageRadioButtons() {
        switch (appData.getCardDisplayer().getCardSkinDirectory()) {
            case CardDisplayer.CARD_SKIN_V_1: skinV1RadioButton.setSelected(true);
                break;
            case CardDisplayer.CARD_SKIN_V_2: skinV2RadioButton.setSelected(true);
                break;
        }

        skinV1RadioButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                appData.getCardDisplayer().setCardSkinDirectory(CardDisplayer.CARD_SKIN_V_1);
            }
        });

        skinV2RadioButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                appData.getCardDisplayer().setCardSkinDirectory(CardDisplayer.CARD_SKIN_V_2);
            }
        });
    }


//    public static void main(String[] args) {
//        new OptionsWindow();
//    }
}
