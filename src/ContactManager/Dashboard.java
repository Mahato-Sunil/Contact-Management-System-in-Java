package ContactManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    private static String key;
    JPanel mainPanel, searchPanel,contactListPanel;
    JTextField searchField;
    JTable contactsTable;
    JScrollPane contactsScrollPane;

    String[] contactHeading = {"SN", "Contact Name", "Number", "Email", "Address"};
    static DefaultTableModel contactTableModel;
    Object[][] contactData;

    // function to create the gui comoponent
    private void createUIComponents() {

        // menu content
        MenuUtils.setRefKey(key);
        MenuUtils.setMenu();
        MenuUtils.setMenuLogic();

        // ======= SEARCH PANEL =======
        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(500, 40));

        searchPanel.add(searchField);

        // ======= CONTACT LIST PANEL =======
        contactListPanel = new JPanel(new BorderLayout());
        contactListPanel.setBorder(BorderFactory.createTitledBorder("All Contact List"));

        contactTableModel = new DefaultTableModel(contactHeading, 0);

        contactsTable = new JTable(contactTableModel);
        contactsTable.setFillsViewportHeight(true);
        contactsTable.setRowHeight(40);

        refreshTableData();

        contactsScrollPane = new JScrollPane(contactsTable);
        contactsScrollPane.setPreferredSize(new Dimension(720, 200));

        contactListPanel.add(contactsScrollPane, BorderLayout.CENTER);

        // Add components to the main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(contactListPanel, BorderLayout.CENTER);

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
        contactsTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Number
        contactsTable.getColumnModel().getColumn(3).setPreferredWidth(280); // Email
        contactsTable.getColumnModel().getColumn(4).setPreferredWidth(280); // Address
        contactsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // for center
        contactsTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {{
            setHorizontalAlignment(CENTER);
        }});
        contactsTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(CENTER);
            }
        });

    }

    public static void refreshTableData() {
        ArrayList<Object[]> data = DatabaseManager.getAllContacts(key);

        // Clear existing rows
        contactTableModel.setRowCount(0);

        // Add new rows dynamically
        for (Object[] row : data) {
            contactTableModel.addRow(row);
        }

        // Notify table about data change
        contactTableModel.fireTableDataChanged();
    }


    public Dashboard(String key) {
        this.key = key;
        createUIComponents();
        setColumnDimension();
    }
}
