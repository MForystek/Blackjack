package gui;

import database.Database;
import database.SqliteDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class RegisterWindow extends JFrame{
    private JButton button1;
    private JPanel mainPanel;
    private Database db;

    private JLabel nickLabel;
    private JLabel passwordLabel;
    private JLabel repeatPasswordLabel;
    private JTextField nickText;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JButton backButton;


    public  RegisterWindow (Database database){
        super("Register");
        this.db = database;
        setContentPane(this.mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(500,500);
        setVisible(true);
        passwordField1.setEchoChar('\u25CF');
        passwordField2.setEchoChar('\u25CF');


        //passwordField1.setEchoChar((char) 0);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClicked();
            }
        });
        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField1.setEchoChar(radioButton1.isSelected() ? (char) 0 : '\u25CF');
            }
        });
        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField2.setEchoChar(radioButton2.isSelected() ? (char) 0 : '\u25CF');
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainWindow();
            }
        });

    }
    public static void main(String[] args) {
        new RegisterWindow(new SqliteDB());
    }

    private void buttonClicked() {
        if (Objects.equals(nickText.getText(), "")) {
            JOptionPane.showMessageDialog(mainPanel, "You must enter a nick.");
            return;
        }
        if (!isPasswordMatch()) {
            JOptionPane.showMessageDialog(mainPanel, "Passwords don't match.");
            return;
        }
        if (passwordField1.getPassword().length == 0) {
            JOptionPane.showMessageDialog(mainPanel, "You must enter a password.");
            return;
        }
        try {
            db.openConnection();
            db.register(nickText.getText(), String.valueOf(passwordField1.getPassword()));
            db.closeConnection();
            JOptionPane.showMessageDialog(mainPanel, "Successfully registered");
            dispose();
            new MainWindow();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainPanel, e.getMessage());
        }
    }

    private boolean isPasswordMatch() {
        return Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword());
    }
}
