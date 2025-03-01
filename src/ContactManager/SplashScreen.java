package ContactManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SplashScreen {
    // Initialization of objects
    JFrame frame;
    JLabel image;
    JProgressBar progressbar;
    JLabel message;
    protected int progress = 0;

    private static final String CONTACT_DB = "dr_contact";

    // Constructor
    SplashScreen() {
        CreateGui();
        SwingUtilities.invokeLater(this::loadDatabaseWithProgress);
    }

    // Function to create GUI
    void CreateGui() {
        frame = new JFrame("Dr. Contact");
        frame.setUndecorated(true);
        frame.setSize(950, 640);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // === ContactManager.Main Panel with BorderLayout ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // === Image Label ===
        image = new JLabel(new ImageIcon("Image/splash-screen.png"));
        image.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(image, BorderLayout.CENTER);

        // === Bottom Panel with BoxLayout (Stacks Progress Bar & Message) ===
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.BLACK);

        // === Loading Message ===
        message = new JLabel("Loading ... 0%", JLabel.CENTER);
        message.setForeground(Color.WHITE);
        message.setFont(new Font("Arial", Font.PLAIN, 18));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === Progress Bar ===
        progressbar = new JProgressBar();
        progressbar.setBorderPainted(false);
        progressbar.setStringPainted(true);
        progressbar.setBackground(Color.DARK_GRAY);
        progressbar.setForeground(Color.BLUE);
        progressbar.setMinimum(0);
        progressbar.setMaximum(100);
        progressbar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressbar.setPreferredSize(new Dimension(680, 20));

        JPanel prograss_wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prograss_wrapper.setBackground(Color.BLACK);

        prograss_wrapper.add(progressbar);

        // Add components to bottom panel
        bottomPanel.add(Box.createVerticalStrut(10)); // Adds spacing
        bottomPanel.add(message);
        bottomPanel.add(Box.createVerticalStrut(10)); // Adds spacing
        bottomPanel.add(prograss_wrapper);
        bottomPanel.add(Box.createVerticalStrut(20)); // Adds spacing

        // Add bottom panel to the frame
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // start the initial configuration for the database
    private void loadDatabaseWithProgress() {
        try {
            Connection conn = DatabaseManager.DatabaseConnection(DatabaseManager.database, DatabaseManager.username, DatabaseManager.password);
            Statement statement = conn.createStatement();

            // Step 1: Check if database exists
            updateProgress("Checking database...", 10);
            String checkDbQuery = "CREATE DATABASE IF NOT EXISTS " + CONTACT_DB;
            statement.executeUpdate(checkDbQuery);
            Thread.sleep(500);

            statement.close();
            conn.close(); // Close initial connection

            // Step 2: Connect to the created/existing database
            Connection dbConn = DatabaseManager.DatabaseConnection(DatabaseManager.contactUrl, DatabaseManager.username, DatabaseManager.password);
            Statement dbStmt = dbConn.createStatement();

            // Step 3: Check if tables exist
            updateProgress("Checking tables...", 30);
            String createAdminTable = "CREATE TABLE IF NOT EXISTS admin (" + "id INT PRIMARY KEY AUTO_INCREMENT, " + "Name VARCHAR(100) NOT NULL," + "Phone VARCHAR(20)  UNIQUE NOT NULL," + "Address VARCHAR(255) NOT NULL," + "Email VARCHAR(255) NOT NULL,"  + "Username VARCHAR(100) UNIQUE NOT NULL," + "Salt VARCHAR(255) UNIQUE NOT NULL, " + "Hash VARCHAR(255) UNIQUE NOT NULL" + ")";
            dbStmt.executeUpdate(createAdminTable);

            String contactTable = "CREATE TABLE IF NOT EXISTS contact_info (" + "id INT PRIMARY KEY AUTO_INCREMENT, " + "Name VARCHAR(100) NOT NULL, " + "Phone VARCHAR(20) UNIQUE NOT NULL, " + "Email VARCHAR(255) UNIQUE NOT NULL, " + "Address VARCHAR(255) NOT NULL, " + "Phone2 VARCHAR(20) DEFAULT NULL," + "RefId VARCHAR(20) NOT NULL, " + " FOREIGN KEY (RefId) REFERENCES admin(Phone)" + ")";
            dbStmt.executeUpdate(contactTable);

            updateProgress("Finalizing setup...", 80);
            Thread.sleep(500);

            dbStmt.close();
            dbConn.close();

            updateProgress("Database Ready!", 100);
            Thread.sleep(1000);

            frame.dispose();
            SwingUtilities.invokeLater(InitWindow::new); // Switch to home screen

        } catch (SQLException e) {
            updateProgress("Database Initialization Failed!", progress);
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            updateProgress("Initialization Interrupted!", progress);
            e.printStackTrace();
        }
    }

    private void updateProgress(String text, int value) {
        SwingUtilities.invokeLater(() -> {
            message.setText(text);
            progressbar.setValue(value);
        });
    }
}