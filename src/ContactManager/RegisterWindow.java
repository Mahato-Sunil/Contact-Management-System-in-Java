package ContactManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class RegisterWindow {
    private JFrame frame;
    private JLabel imgTitle, firstNameLabel, lastNameLabel, emailLabel, phoneNoLabel, addressLabel, passwordLabel, confirmPasswordLabel;
    private JTextField firstName, lastName, email, phone, address;
    private JButton sendButton, skipButton;
    private JPasswordField password, confirmPassword;

    public RegisterWindow() {
        createUIComponents();
    }

    private void createUIComponents() {
        // Frame settings
        frame = new JFrame("User Registration");
        frame.setSize(1080, 720);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // Left Panel - Image and Title
        JPanel imageContainer = new JPanel();
        imageContainer.setBackground(Color.WHITE);
        imageContainer.setLayout(new BorderLayout());
        imageContainer.setPreferredSize(new Dimension(400, 500));

        // Image
        ImageIcon icon = new ImageIcon("Image/register-theme-icon.png");
        imgTitle = new JLabel(icon, JLabel.CENTER);
        imageContainer.add(imgTitle, BorderLayout.CENTER);

        // Right Panel - Form
        JPanel formContainer = new JPanel();
        formContainer.setBackground(Color.white);
        formContainer.setPreferredSize(new Dimension(700, 720));
        formContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels & Inputs
        gbc.gridx = 0;
        gbc.gridy = 0;
        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(new Font("verdana", Font.PLAIN, 16));
        formContainer.add(firstNameLabel, gbc);

        firstName = new JTextField();
        firstName.setPreferredSize(new Dimension(250, 50));
        gbc.gridx = 1;
        formContainer.add(firstName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(new Font("verdana", Font.PLAIN, 16));
        formContainer.add(lastNameLabel, gbc);

        lastName = new JTextField(15);
        lastName.setPreferredSize(new Dimension(250, 50));
        gbc.gridx = 1;
        formContainer.add(lastName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("verdana", Font.PLAIN, 16));

        formContainer.add(emailLabel, gbc);
        email = new JTextField();
        email.setPreferredSize(new Dimension(250, 50));
        gbc.gridx = 1;
        formContainer.add(email, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("verdana", Font.PLAIN, 16));
        formContainer.add(addressLabel, gbc);
        gbc.gridx = 1;
        address = new JTextField();
        address.setPreferredSize(new Dimension(250, 50));
        formContainer.add(address, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        phoneNoLabel = new JLabel("Phone");
        phoneNoLabel.setFont(new Font("verdana", Font.PLAIN, 16));

        formContainer.add(phoneNoLabel, gbc);
        phone = new JTextField();
        phone.setPreferredSize(new Dimension(250, 50));
        gbc.gridx = 1;
        formContainer.add(phone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("verdana", Font.PLAIN, 16));
        formContainer.add(passwordLabel, gbc);

        password = new JPasswordField();
        password.setPreferredSize(new Dimension(250, 50));
        gbc.gridx = 1;
        formContainer.add(password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        confirmPasswordLabel = new JLabel("Retype Password");
        confirmPasswordLabel.setFont(new Font("verdana", Font.PLAIN, 16));

        formContainer.add(confirmPasswordLabel, gbc);
        confirmPassword = new JPasswordField();
        confirmPassword.setPreferredSize(new Dimension(250, 50));
        gbc.gridx = 1;
        formContainer.add(confirmPassword, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        skipButton = new JButton("Cancel");
        skipButton.setFont(new Font("verdana", Font.PLAIN, 16));
        skipButton.setBackground(new Color(255, 151, 151));
        skipButton.setPreferredSize(new Dimension(120, 35));
        skipButton.setFocusable(false);
        skipButton.addActionListener(e -> {
            frame.dispose();
        });


        sendButton = new JButton("Register");
        sendButton.setFont(new Font("verdana", Font.PLAIN, 16));
        sendButton.setBackground(new Color(148, 255, 156));
        sendButton.setPreferredSize(new Dimension(120, 35));
        sendButton.setFocusable(false);
        sendButton.addActionListener(e -> {
            init_db_operation();
        });

        buttonPanel.add(skipButton);
        buttonPanel.add(sendButton);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        formContainer.add(buttonPanel, gbc);

        // Adding components to frame
        frame.add(imageContainer, BorderLayout.WEST);
        frame.add(formContainer, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void init_db_operation() {
        // retrieve the data
        String fnameInpt = firstName.getText();
        String lnameInpt = lastName.getText();
        String phoneInpt = phone.getText();
        String addressInpt = address.getText();
        String emailInpt = email.getText();
        String pwdInpt = password.getText();
        String confirmPwdInpt = confirmPassword.getText();

        // check for empty fields
        if (fnameInpt.isEmpty() || lnameInpt.isEmpty() || phoneInpt.isEmpty() || addressInpt.isEmpty() || emailInpt.isEmpty() || pwdInpt.isEmpty() || confirmPwdInpt.isEmpty())
            JOptionPane.showMessageDialog(frame, "Please fill all the fields");
        else {

            // check for the password similarities
            if (!pwdInpt.equals(confirmPwdInpt)) JOptionPane.showMessageDialog(frame, "Passwords do not match");
            else {
                // hash the password
                int hashFunction = (int) (Math.random() * 100000000);
                String salt = String.valueOf(hashFunction);
                String hashCode = salt + confirmPwdInpt;

                Boolean isRegistered = DatabaseManager.registerAdmin(fnameInpt, lnameInpt, phoneInpt, addressInpt, emailInpt, salt, hashCode);

                if (isRegistered) {
                    JOptionPane.showMessageDialog(frame, "You have successfully registered your contact");
                    frame.dispose();
                    SwingUtilities.invokeLater(LoginWindow::new);
                }
                else JOptionPane.showMessageDialog(frame, "Something went wrong");
            }
        }
    }
}
