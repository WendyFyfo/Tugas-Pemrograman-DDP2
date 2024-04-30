package assignments.assignment3;

import assignments.assignmentmodel.systemCLI.AdminSystemCLI;
import assignments.assignmentmodel.systemCLI.CustomerSystemCLI;
import assignments.assignmentmodel.systemCLI.UserSystemCLI;

public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;

    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    //TODO: Solve the error :) (It's actually easy if you have done the other TODOs)
    public UserSystemCLI getSystem(String role){
        if(role == "Customer"){
            return adminSystem;
        }else{
            return customerSystem;
        }
    }
}
