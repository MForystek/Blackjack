package gui;

import applicationLogic.ApplicationData;
import gameLogic.cards.CardDisplayer;

import javax.swing.*;
import java.awt.*;
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
    private JRadioButton skinV3RadioButton;
    private JLabel skinV3Label;

    private ApplicationData appData;

    public OptionsWindow (){
        super("Options");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        pack();
        setVisible(true);

        appData = ApplicationData.getInstance();

        ImageIcon skinV1 = new ImageIcon(appData.getCardDisplayer().getPreviewCardForV1().toString());
        ImageIcon skinV2 = new ImageIcon(appData.getCardDisplayer().getPreviewCardForV2().toString());
        ImageIcon skinV3 = new ImageIcon(appData.getCardDisplayer().getPreviewCardForV3().toString());

        skinV1Label.setIcon(skinV1);
        skinV2Label.setIcon(skinV2);
        skinV3Label.setIcon(skinV3);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(skinV1RadioButton);
        buttonGroup.add(skinV2RadioButton);
        buttonGroup.add(skinV3RadioButton);

        manageRadioButtons();

        backToMainMenuButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new MainWindow();
            }
        });
        changePasswordButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new ChangePasswordWindow();
            }
        });

    }


    private void manageRadioButtons() {
        switch (appData.getCardDisplayer().getCardSkinDirectory()) {
            case CardDisplayer.CARD_SKIN_V_1: skinV1RadioButton.setSelected(true);
                break;
            case CardDisplayer.CARD_SKIN_V_2: skinV2RadioButton.setSelected(true);
                break;
            case CardDisplayer.CARD_SKIN_V_3: skinV3RadioButton.setSelected(true);
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

        skinV3RadioButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                appData.getCardDisplayer().setCardSkinDirectory(CardDisplayer.CARD_SKIN_V_3);
            }
        });
    }
}
