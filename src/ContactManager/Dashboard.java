package ContactManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Dashboard extends JFrame {

    JPanel mainPanel, searchPanel, recentContactsPanel, contactListPanel, centerPanel;
    JTextField searchField;
    JTable contactsTable, recentContactsTable;
    JScrollPane contactsScrollPane, recentScrollPane;

    // function to create the gui comoponent
    private void createUIComponents() {
        // menu content
        MenuUtils.setMenu();
        MenuUtils.setMenuLogic();

        // ======= SEARCH PANEL =======
        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(500, 40));

        searchPanel.add(searchField);

        //==============RECENT CONTACTS PANEL ===============
        recentContactsPanel = new JPanel(new BorderLayout());
        recentContactsPanel.setBorder(BorderFactory.createTitledBorder("Recent Contacts"));

        // Table for Recent Contacts
        String[] recentColumns = {"SN", "Name", "Number"};
        Object[][] recentData = {{"1", "Alice", "9876543210"}, {"2", "Bob", "8765432109"}, {"3", "Charlie", "7654321098"}, {"4", "David", "6543210987"}, {"5", "Emma", "5432109876"},};

        recentContactsTable = new JTable(recentData, recentColumns);
        recentContactsTable.setFillsViewportHeight(true);
        recentContactsTable.setRowHeight(40);

        recentScrollPane = new JScrollPane(recentContactsTable);
        recentScrollPane.setPreferredSize(new Dimension(720, 200));

        recentContactsPanel.add(recentScrollPane, BorderLayout.CENTER);


        // ======= CONTACT LIST PANEL =======
        contactListPanel = new JPanel(new BorderLayout());
        contactListPanel.setBorder(BorderFactory.createTitledBorder("All Contact List"));

        // Table for All Contacts
        String[] columns = {"SN", "Contact Name", "Number", "Email", "Address"};
        Object[][] data = {{"1", "Alice", "9876543210", "alice@example.com", "New York"}, {"2", "Bob", "8765432109", "bob@example.com", "Los Angeles"}, {"3", "Charlie", "7654321098", "charlie@example.com", "Chicago"}, {"4", "David", "6543210987", "david@example.com", "Houston"}, {"5", "Emma", "5432109876", "emma@example.com", "San Francisco"}, {"6", "Frank", "4321098765", "frank@example.com", "Seattle"}, {"7", "Grace", "3210987654", "grace@example.com", "Boston"}, {"8", "Hank", "2109876543", "hank@example.com", "Denver"}};

        contactsTable = new JTable(data, columns);
        contactsTable.setFillsViewportHeight(true);
        contactsTable.setRowHeight(40);

        contactsScrollPane = new JScrollPane(contactsTable);
        contactsScrollPane.setPreferredSize(new Dimension(720, 200));

        contactListPanel.add(contactsScrollPane, BorderLayout.CENTER);

        // ======= CENTER PANEL (Holding Recent Contacts and Contact List) =======
        centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(recentContactsPanel, BorderLayout.NORTH);
        centerPanel.add(contactListPanel, BorderLayout.CENTER);

        // Add components to the main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
        setBackground(Color.WHITE);
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(MenuUtils.menu);
        setVisible(true);
    }

    private void setColumnDimension() {
        // Reduce table column widths (contact table)
        contactsTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // SN
        contactsTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Contact Name
        contactsTable.getColumnModel().getColumn(2).setPreferredWidth(180); // Number
        contactsTable.getColumnModel().getColumn(3).setPreferredWidth(280); // Email
        contactsTable.getColumnModel().getColumn(4).setPreferredWidth(280); // Address
        contactsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // for center
        contactsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {{
            setHorizontalAlignment(CENTER);
        }});
        contactsTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(CENTER);
            }
        });

        // for recent tables
        recentContactsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {{
            setHorizontalAlignment(CENTER);
        }});
        recentContactsTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(CENTER);
            }
        });

        recentContactsTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // SN
        recentContactsTable.getColumnModel().getColumn(1).setPreferredWidth(300); // Contact Name
        recentContactsTable.getColumnModel().getColumn(2).setPreferredWidth(280); // Number
        recentContactsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    }

    public Dashboard() {
        createUIComponents();
        setColumnDimension();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}
