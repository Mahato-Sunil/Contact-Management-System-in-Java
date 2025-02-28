package ContactManager;

import java.util.ArrayList;

public class Validate extends DatabaseManager {

    String uid, upassword;
    String orgUserName;

    Validate(String uid, String upassword) {
        this.uid = uid;
        this.upassword = upassword;
    }

    public Boolean checkValidity() {
        boolean isValid = false;

        // retrieve the admin details
        ArrayList<String> adminInfo = DatabaseManager.getAdminInfo(uid);
        System.out.println(adminInfo);
        if (adminInfo.isEmpty()) {  // ✅ Check if the list is empty before accessing
            System.out.println("Error: No admin found with UID: " + uid);
            return false; // Return false if no data is found
        }

        String org_username = adminInfo.get(0); // username
        String org_salt = adminInfo.get(1);     //salt
        String org_hash = adminInfo.get(2);     // hash

        String retrievedKey = org_salt + upassword;
        System.out.println(retrievedKey);
        isValid = (uid.equals(org_username) && org_hash.equals(retrievedKey));
        return isValid;
    }
}