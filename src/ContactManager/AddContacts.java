package ContactManager;

import javax.swing.*;
import java.awt.*;

public class AddContacts {
    private JFrame frame;
    private JLabel imgTitle, firstNameLabel, lastNameLabel, emailLabel, phoneNoLabel, addressLabel, altPhoneLabel, confirmPasswordLabel;
    private JTextField firstName, lastName, email, phone, address, altPhone;
    private JButton sendButton, skipButton;

    public AddContacts() {
        createUIComponents();
    }

    private void createUIComponents() {
        // Frame settings
        frame = new JFrame("User Registration");
        frame.setSize(980, 720);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // Left Panel - Image and Title
        JPanel imageContainer = new JPanel();
        imageContainer.setBackground(Color.WHITE);
        imageContainer.setLayout(new BorderLayout());
        imageContainer.setPreferredSize(new Dimension(300, 500));

        // Image
        ImageIcon icon = new ImageIcon("Image/register-theme-icon.png");
        imgTitle = new JLabel(icon, JLabel.CENTER);
        imageContainer.add(imgTitle, BorderLayout.CENTER);

        // Right Panel - Form
        JPanel formContainer = new JPanel();
        formContainer.setBackground(Color.white);
        formContainer.setPreferredSize(new Dimension(600, 720));
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
        altPhoneLabel = new JLabel("Alternative Phone (if any)");
        altPhoneLabel.setFont(new Font("verdana", Font.PLAIN, 16));
        formContainer.add(altPhoneLabel, gbc);
        gbc.gridx = 1;
        altPhone = new JTextField();
        altPhone.setPreferredSize(new Dimension(250, 50));
        altPhone.setForeground(Color.LIGHT_GRAY);
        altPhone.setText("xxx");
        formContainer.add(altPhone, gbc);

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


        sendButton = new JButton(" Add ");
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
        String altPhoneInpt = altPhone.getText();

        // check for empty fields
        if (fnameInpt.isEmpty() || lnameInpt.isEmpty() || phoneInpt.isEmpty() || addressInpt.isEmpty() || emailInpt.isEmpty() || altPhoneInpt.isEmpty())
            JOptionPane.showMessageDialog(frame, "Please fill all the fields");
        else {
            Boolean isContactAdded = DatabaseManager.addNewContact(fnameInpt, lnameInpt, phoneInpt, addressInpt, emailInpt, altPhoneInpt);

            if (isContactAdded) {
                JOptionPane.showMessageDialog(frame, "You have successfully added new contact");
                frame.dispose();
            } else JOptionPane.showMessageDialog(frame, "Something went wrong");
        }
    }
}
