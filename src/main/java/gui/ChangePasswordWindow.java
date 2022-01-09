package gui;

import applicationLogic.ApplicationData;
import database.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class ChangePasswordWindow extends JFrame{
    private Database database;

    private JTextField nick;
    private JPasswordField password;
    private JPasswordField newPassword;
    private JPasswordField repeatNewPassword;
    private JButton backToMenuButton;
    private JButton confirmButton;
    private JPanel mainPanel;
    private JRadioButton showPasswordsRadioButton;

    public ChangePasswordWindow() {
        super("Change password");
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        database = ApplicationData.getInstance().getDatabase();

        password.setEchoChar('\u25CF');
        newPassword.setEchoChar('\u25CF');
        repeatNewPassword.setEchoChar('\u25CF');

        showPasswordsRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                password.setEchoChar(showPasswordsRadioButton.isSelected() ? (char) 0 : '\u25CF');
                newPassword.setEchoChar(showPasswordsRadioButton.isSelected() ? (char) 0 : '\u25CF');
                repeatNewPassword.setEchoChar(showPasswordsRadioButton.isSelected() ? (char) 0 : '\u25CF');
            }
        });
        confirmButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                confirmButtonClicked();
            }
        });
        backToMenuButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new OptionsWindow();
            }
        });
    }

    private void confirmButtonClicked() {
        if (Objects.equals(nick.getText(), "")) {
            JOptionPane.showMessageDialog(mainPanel, "You must enter a nick.");
            return;
        }
        try {
            database.openConnection();
            if (!database.login(nick.getText(), String.valueOf(password.getPassword()))) {
                database.closeConnection();
                JOptionPane.showMessageDialog(mainPanel, "Wrong nick or password.");
                return;
            }
            database.closeConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainPanel, e.getMessage());
        }

        if (!isPasswordMatch()) {
            JOptionPane.showMessageDialog(mainPanel, "Passwords don't match.");
            return;
        }

        if (newPassword.getPassword().length == 0) {
            JOptionPane.showMessageDialog(mainPanel, "You must enter a password.");
            return;
        }

        try {
            database.openConnection();
            database.changePassword(nick.getText(), String.valueOf(newPassword.getPassword()));
            database.closeConnection();
            JOptionPane.showMessageDialog(mainPanel, "Password changed successfully.");
            dispose();
            new OptionsWindow();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainPanel, e.getMessage());
        }

    }

    private boolean isPasswordMatch() {
        return Arrays.equals(newPassword.getPassword(), repeatNewPassword.getPassword());
    }
}
