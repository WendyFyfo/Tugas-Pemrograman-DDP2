package assignments.assignment3;

import assignments.assignmentmodel.systemCLI.AdminSystemCLI;
import assignments.assignmentmodel.systemCLI.CustomerSystemCLI;
import assignments.assignmentmodel.systemCLI.UserSystemCLI;
import assignments.assignmentmodel.restomodel.User;

public class LoginManager {
    private final UserSystemCLI userSystem;

    public LoginManager(User userLoggedin) {
        this.userSystem = getSystem(userLoggedin);
    }

    //cek role user dan menjalankan menu program sesuai role
    public UserSystemCLI getSystem(User userLoggedIn) {
        if (userLoggedIn.getRole().equals("Customer")) {
            return new CustomerSystemCLI(userLoggedIn);
        } else {
            return new AdminSystemCLI();
        }
    }
}