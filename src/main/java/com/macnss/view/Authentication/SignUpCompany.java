package com.macnss.view.Authentication;

import com.macnss.app.Services.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpCompany extends JFrame implements ActionListener {

    Authentication auth;

    private JTextField companyname, email;
    private JPasswordField password, confirmPassword;
    private JButton resetButton, signUpButton;
    private JLabel companyNameLabel, passwordLabel, confirmPasswordLabel, emailLabel;
    private ImageIcon userIcon, passwordIcon;
    private Image logo;

    private String enteredCompanyName = null, enteredPassword = null, enteredConfirmPassword = null,enteredEmail = null;

    public SignUpCompany() {
        auth = new Authentication();

        setTitle("Signup company");
        setSize(560, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        userIcon = new ImageIcon("user.png");
        passwordIcon = new ImageIcon("password.png");

        ImageIcon logoIcon = new ImageIcon("assets/images/app/cnss.png");
        Image logo = logoIcon.getImage();

        setIconImage(logo);

        companyname = new JTextField();
        email = new JTextField();
        emailLabel = new JLabel("Company email : ");
        password = new JPasswordField();
        confirmPassword = new JPasswordField();
        resetButton = new JButton("R");
        companyNameLabel = new JLabel("Company name :");
        passwordLabel = new JLabel("Password :");
        confirmPasswordLabel = new JLabel("Confirm password :");
        signUpButton = new JButton("Signup");


        JLabel logoLabel = new JLabel(new ImageIcon(logo));
        logoLabel.setBounds(130, 40,300 , 282);

        companyNameLabel.setBounds(40, 330, 470, 30);
        companyNameLabel.setIcon(userIcon);
        companyname.setBounds(40, 375, 460, 35);

        emailLabel.setBounds(40, 430, 470, 30);
        emailLabel.setIcon(userIcon);
        email.setBounds(40, 475, 460, 35);

        passwordLabel.setBounds(40, 530, 470, 30);
        passwordLabel.setIcon(passwordIcon);
        password.setBounds(40, 575, 460, 35);

        confirmPasswordLabel.setBounds(40,630,470,30);
        passwordLabel.setIcon(passwordIcon);
        confirmPassword.setBounds(40,675,460,35);

        signUpButton.setBounds(45, 720, 170, 30);
        signUpButton.setForeground(new Color(29, 170, 172));
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 16));
        signUpButton.setOpaque(true);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));



        resetButton.addActionListener(this);
        signUpButton.addActionListener(this);

        add(logoLabel);
        add(companyNameLabel);
        add(companyname);
        add(passwordLabel);
        add(password);
        add(confirmPasswordLabel);
        add(confirmPassword);
        add(signUpButton);
        add(email);
        add(emailLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == signUpButton) {

            enteredCompanyName = companyname.getText();
            enteredEmail = email.getText();
            enteredPassword = new String(password.getPassword());
            enteredConfirmPassword = new String(confirmPassword.getPassword());

            try{
                if(auth.registerCompany(enteredCompanyName,enteredEmail,enteredPassword,enteredConfirmPassword)){
                    setVisible(false);
                    new SigningCompany().setVisible(true);
                }

            }catch(Exception exception){
                JOptionPane.showMessageDialog(this,"Invalid credentials","Error",JOptionPane.ERROR_MESSAGE);
                setVisible(true);
            }

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SigningCompany().setVisible(true);
        });
    }
}
