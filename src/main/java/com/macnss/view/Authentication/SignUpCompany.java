package com.macnss.view.Authentication;

import com.macnss.app.Services.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpCompany extends JFrame implements ActionListener {

    Authentication auth;

    private JTextField username;
    private JPasswordField password, confirmPassword;
    private JButton resetButton, signUpButton;
    private JLabel usernameLabel, passwordLabel, confirmPasswordLabel;
    private ImageIcon userIcon, passwordIcon;
    private Image logo;

    private String enteredUsername = null, enteredPassword = null;

    public SignUpCompany() {
        auth = new Authentication();

        setTitle("Signup company");
        setSize(560, 730);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        userIcon = new ImageIcon("user.png");
        passwordIcon = new ImageIcon("password.png");

        ImageIcon logoIcon = new ImageIcon("assets/images/app/cnss.png");
        Image logo = logoIcon.getImage();

        setIconImage(logo);

        username = new JTextField();
        password = new JPasswordField();
        confirmPassword = new JPasswordField();
        resetButton = new JButton("R");
        usernameLabel = new JLabel("Username :");
        passwordLabel = new JLabel("Password :");
        confirmPasswordLabel = new JLabel("Confirm password :");
        signUpButton = new JButton("Signup");


        JLabel logoLabel = new JLabel(new ImageIcon(logo));
        logoLabel.setBounds(130, 40,300 , 282);

        usernameLabel.setBounds(40, 340, 470, 30);
        usernameLabel.setIcon(userIcon);
        username.setBounds(40, 375, 460, 35);

        passwordLabel.setBounds(40, 430, 470, 30);
        passwordLabel.setIcon(passwordIcon);
        password.setBounds(40, 475, 460, 35);

        confirmPasswordLabel.setBounds(40,530,470,30);
        passwordLabel.setIcon(passwordIcon);
        confirmPassword.setBounds(40,575,460,35);

        signUpButton.setBounds(45, 620, 170, 30);
        signUpButton.setForeground(new Color(29, 170, 172));
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 16));
        signUpButton.setOpaque(true);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));



        resetButton.addActionListener(this);
        signUpButton.addActionListener(this);

        add(logoLabel);
        add(usernameLabel);
        add(username);
        add(passwordLabel);
        add(password);
        add(confirmPasswordLabel);
        add(confirmPassword);
        add(signUpButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SigningCompany().setVisible(true);
        });
    }
}
