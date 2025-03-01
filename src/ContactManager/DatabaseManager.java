package ContactManager;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    // database credentials
    static String username = "root";
    static String password = "";

    static String database = "jdbc:mysql://localhost:3306/";
    static String contactUrl = "jdbc:mysql://localhost:3306/dr_contact";

    // try connection
    public static Connection DatabaseConnection(String dbUrl, String dbUserName, String dbPassword) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        } catch (ClassNotFoundException e) {
            System.err.println(" MySQL JDBC Driver not found!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Database connection failed! Check your credentials and database URL.");
            e.printStackTrace();
            return null;
        }
    }

    // function to store to database
    public static Boolean addNewContact(String... params) {
        // create the connection
        try {
            Connection conn = DatabaseConnection(DatabaseManager.contactUrl, DatabaseManager.username, DatabaseManager.password);
            String sql = "INSERT INTO contact_info (Name, Phone, Address, Email, Phone2, RefId ) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, params[0] + params[1]);
            statement.setString(2, params[2]);
            statement.setString(3, params[3]);
            statement.setString(4, params[4]);
            statement.setString(5, params[5]);
            statement.setString(6, params[6]);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) return true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // function to retrieve the data
    public static ArrayList<Object[]> getAllContacts(String id) {
        ArrayList<Object[]> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contact_info WHERE  RefId='" + id + "'";
        try {
            Connection conn = DatabaseConnection(DatabaseManager.contactUrl, DatabaseManager.username, DatabaseManager.password);
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String sn = resultSet.getString("Id");
                String fullName = resultSet.getString("Name");
                String phone = resultSet.getString("Phone");
                String address = resultSet.getString("Address");
                String email = resultSet.getString("Email");
                String phone2 = resultSet.getString("Phone2");
                contacts.add(new Object[]{sn, fullName, phone, address, email, phone2});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    // function to delete  the data
    public static void DeleteContactData() {

    }

    // function to edit the contatct data
    public static void UpdateContactData() {

    }

    // function to query the database for admin login information
    public static ArrayList<String> getAdminInfo(String id) {
        ArrayList<String> adminInfo = new ArrayList<>();

        String sql = "SELECT * FROM admin WHERE Username = '" + id + "'";
        try {
            Connection conn = DatabaseConnection(DatabaseManager.contactUrl, DatabaseManager.username, DatabaseManager.password);
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                adminInfo.add(resultSet.getString("Username"));
                adminInfo.add(resultSet.getString("Salt"));
                adminInfo.add(resultSet.getString("Hash"));
                adminInfo.add(resultSet.getString("Phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminInfo;
    }

    // function to register the admin
    public static Boolean registerAdmin(String... params) {
        // create the connection
        try {
            Connection conn = DatabaseConnection(DatabaseManager.contactUrl, DatabaseManager.username, DatabaseManager.password);
            String sql = "INSERT INTO admin (Name, Phone, Address, Email, Username, Salt, Hash) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, params[0] + params[1]);
            statement.setString(2, params[2]);
            statement.setString(3, params[3]);
            statement.setString(4, params[4]);
            statement.setString(5, params[4]);
            statement.setString(6, params[5]);
            statement.setString(7, params[6]);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) return true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}