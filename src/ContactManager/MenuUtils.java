package ContactManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class MenuUtils extends JFrame {
    // for menu
    private static String refKey;
    static JMenuBar menu;
    static JMenu file, edit, view, help;
    static JMenuItem newContact, exportContact, logout, exit, editContact, deleteContact, recentContact, allContacts, about, userGuide;
    static Font menuFont = new Font("Times New Roman", Font.PLAIN, 18);

   static void setRefKey(String refKey) {
       MenuUtils.refKey = refKey;
   }

    static void setMenu()      // for menu items
    {
        menu = new JMenuBar();
        menu.setFont(menuFont);
        menu.setPreferredSize(new Dimension(500, 50));
        menu.setBackground(Color.WHITE);

        file = new JMenu("File");
        edit = new JMenu("Edit");
        view = new JMenu("view");
        help = new JMenu("Help");

        newContact = new JMenuItem("New contact");
        exportContact = new JMenuItem("Export");
        logout = new JMenuItem("Logout");
        exit = new JMenuItem("Exit");

        file.add(newContact);
        file.add(exportContact);
        file.add(logout);
        file.add(exit);

        editContact = new JMenuItem("Update");
        deleteContact = new JMenuItem("Delete");
        edit.add(editContact);
        edit.add(deleteContact);

        recentContact = new JMenuItem("Recently Added");
        allContacts = new JMenuItem("All Contacts");

        view.add(recentContact);
        view.add(allContacts);

        about = new JMenuItem("About");
        userGuide = new JMenuItem("User Guide");
        help.add(userGuide);
        help.add(about);


        menu.add(file);
        menu.add(edit);
        menu.add(view);
        menu.add(help);
    }

//    static void setMenuDesign() {
//        home.setFont(menuFont);
//        homeMenu.setFont(menuFont);
//        aboutMenu.setFont(menuFont);
//        viewMenu.setFont(menuFont);
//        settingMenu.setFont(menuFont);
//        aboutMenuItem.setFont(menuFont);
//        aboutDeveloper.setFont(menuFont);
//        teacherDataMenu.setFont(menuFont);
//        studentDataMenu.setFont(menuFont);
//        exitMenu.setFont(menuFont);
//        adminPageMenu.setFont(menuFont);
//        loginPageMenu.setFont(menuFont);
//    }

    static void disposeFrame() {
        Frame[] allFrames = Frame.getFrames();  // Get all frames to an array
        for (Frame frame : allFrames) { // Iterate through the allFrames array
            if (frame.isVisible()) {            // Check if frame is visible
                frame.dispose();
            }
        }
    }

    static void setMenuLogic() {
        newContact.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new AddContacts(refKey));
        });

        about.addActionListener(e -> JOptionPane.showMessageDialog(null, " Developer Name = Sunil Mahato \n Education : BCA 6th Sem \n Contact : +977-9860650642 \n Email : sunilmaht642@gmail.com"));
    }
}

/* // extends the class and add the methods in the constructor of the given class to achieve the menu in the given class

setMenu();                  // menu dimensions
setMenuDesign();            //menu design
setMenuLogic()                // menu logic
frame.setJMenuBar(menu);    // to show the menu frame
*/

/*
for classes which cant be extended
use following code :
        publicMenu.setMenu();
        publicMenu.setMenuDesign();
        publicMenu.setMenuLogic();
        frame.setJMenuBar(publicMenu.menu);

 */
