package unihousie.entity;

public class Admin extends User {
    public Admin(String userId, String email, String fullName, String phone) {
        super(userId, email, fullName, phone, "ADMIN");
    }

    public String getAdminId() { return getUserId(); }
}
