package unihousie.entity;

public class Landlord extends User {
    public Landlord(String userId, String email, String fullName, String phone) {
        super(userId, email, fullName, phone, "LANDLORD");
    }
}
