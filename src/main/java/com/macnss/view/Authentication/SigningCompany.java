package com.macnss.view.Authentication;

import com.macnss.app.Models.Company;
import com.macnss.app.Models.user.Employee;
import com.macnss.app.Services.Authentication;
import com.macnss.dao.CompanyDao;
import com.macnss.view.Company.index;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Optional;

public class SigningCompany extends JFrame implements ActionListener {
    Authentication auth;

    private JTextField username;
    private JPasswordField password;
    private JButton loginButton, resetButton, forgetPasswordButton, signUpButton;
    private JLabel usernameLabel, passwordLabel;
    private ImageIcon userIcon, passwordIcon;
    private Image logo;

    private String enteredEmail = null, enteredPassword = null;

    public SigningCompany() {
        auth = new Authentication();

        setTitle("Signing company");
        setSize(560, 700);
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
        loginButton = new JButton("Login");
        forgetPasswordButton = new JButton("Forget Password ?");
        resetButton = new JButton("R");
        usernameLabel = new JLabel("Username :");
        passwordLabel = new JLabel("Password :");
        signUpButton = new JButton("Signup");


        JLabel logoLabel = new JLabel(new ImageIcon(logo));
        logoLabel.setBounds(130, 40,300 , 282);

        usernameLabel.setBounds(40, 340, 470, 30);
        usernameLabel.setIcon(userIcon);
        username.setBounds(40, 375, 460, 35);

        passwordLabel.setBounds(40, 430, 470, 30);
        passwordLabel.setIcon(passwordIcon);
        password.setBounds(40, 475, 460, 35);

        loginButton.setBounds(45, 560, 170, 30);
        loginButton.setForeground(new Color(29, 170, 172));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        signUpButton.setBounds(45, 600, 170, 30);
        signUpButton.setForeground(new Color(29, 170, 172));
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 16));
        signUpButton.setOpaque(true);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));



        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        forgetPasswordButton.addActionListener(this);
        signUpButton.addActionListener(this);

        add(logoLabel);
        add(usernameLabel);
        add(username);
        add(passwordLabel);
        add(password);
        add(loginButton);
        add(forgetPasswordButton);
        add(signUpButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            enteredEmail = username.getText();
            enteredPassword = new String(password.getPassword());

            if (auth.authenticateCompany(enteredEmail, enteredPassword)) {
                Company company = new Company.Builder()
                                            .email(enteredEmail)
                                            .password(enteredPassword)
                                            .build();
                index Indexview = null;
                CompanyDao companyDao = new CompanyDao(company);

                Optional<Company> activeCompanyData = companyDao.get(company.getEmail());

                if(activeCompanyData.isPresent()){
                    Company activeCompany = new Company.Builder()
                            .companyId(activeCompanyData.get().getCompany_id())
                            .email(activeCompanyData.get().getEmail())
                            .password(activeCompanyData.get().getPassword())
                            .build();
                    try {
                        Indexview = new index(activeCompany);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                assert Indexview != null;
                Indexview.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "Username or Password Incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else if(e.getSource() == signUpButton) {
            setVisible(false);
            new SignUpCompany().setVisible(true);
        }else if (e.getSource() == forgetPasswordButton) {
            JOptionPane.showMessageDialog(this, "Contact the administrator", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (e.getSource() == resetButton) {
            username.setText("");
            password.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SigningCompany().setVisible(true);
        });
    }
}
