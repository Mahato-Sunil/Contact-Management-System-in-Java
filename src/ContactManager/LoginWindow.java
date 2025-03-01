package ContactManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginWindow implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel userModeLabel, userIdLabel, userPwdLabel, message;
    private JComboBox<String> userMode;
    private JTextField userIdInp;
    private JPasswordField userPwdInp;
    private JButton submit;
    private String[] mode = {"Admin", "User"}; // Sample roles

    public LoginWindow() {
        frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 250);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Use GridBagLayout for professional UI management
        panel = new JPanel();
        panel.setBackground(Color.white); // Light blue background
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label
        userIdLabel = new JLabel("Username or Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(userIdLabel, gbc);

        // Username Input
        userIdInp = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(userIdInp, gbc);

        // Password Label
        userPwdLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(userPwdLabel, gbc);

        // Password Input
        userPwdInp = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(userPwdInp, gbc);

        // Login Button
        submit = new JButton("Login");
        submit.setFocusable(false);
        submit.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        panel.add(submit, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // login button
        if (e.getSource() == submit) {
            String id = userIdInp.getText();
            String password = String.valueOf(userPwdInp.getPassword());

            if (id.isEmpty() || password.isEmpty())
                JOptionPane.showMessageDialog(frame, "Please Enter User Credentials !");
            else {
                // creationg of validate class
                Validate valid = new Validate(id, password);
                if (valid.checkValidity()) {
                    frame.dispose();
                    //retreive the admin phone number as a unique key
                    ArrayList<String> key = DatabaseManager.getAdminInfo(id);
                    SwingUtilities.invokeLater(() -> new Dashboard(key.get(3)));

                } else JOptionPane.showMessageDialog(frame, "Invalid Username or Password !");
            }
        }
    }
}
