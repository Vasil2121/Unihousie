package unihousie.entity;

public class UserSummary {
    private String userId;
    private String fullName;
    private String department;
    private String habits;
    private double budget;
    private String contactStub;

    public UserSummary(String userId, String fullName, String department,
                       String habits, double budget, String contactStub) {
        this.userId = userId;
        this.fullName = fullName;
        this.department = department;
        this.habits = habits;
        this.budget = budget;
        this.contactStub = contactStub;
    }

    public String getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getDepartment() { return department; }
    public String getHabits() { return habits; }
    public double getBudget() { return budget; }
    public String getContactStub() { return contactStub; }
}
