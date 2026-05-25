package unihousie.entity;

public class Student extends User {
    public static final String UNVERIFIED = "UNVERIFIED";
    public static final String VERIFIED = "VERIFIED";

    private String department;
    private String verificationStatus = UNVERIFIED;
    private int warningCount = 0;
    private LifestyleProfile lifestyleProfile;

    public Student(String userId, String email, String fullName, String phone, String department) {
        super(userId, email, fullName, phone, "STUDENT");
        this.department = department;
    }

    public String getStudentId() { return getUserId(); }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getVerificationStatus() { return verificationStatus; }

    @Override
    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    @Override
    public boolean isVerified() { return VERIFIED.equals(verificationStatus); }

    public int getWarningCount() { return warningCount; }

    public void incrementWarningCount() { this.warningCount++; }

    public LifestyleProfile getLifestyleProfile() { return lifestyleProfile; }
    public void setLifestyleProfile(LifestyleProfile lifestyleProfile) { this.lifestyleProfile = lifestyleProfile; }

    public void markProfileAsCompleted() {
        if (lifestyleProfile != null) lifestyleProfile.setCompleted(true);
    }

    public LifestyleProfile findOwnProfile() { return lifestyleProfile; }
}
