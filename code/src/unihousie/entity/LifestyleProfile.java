package unihousie.entity;

public class LifestyleProfile {
    private String profileId;
    private String habits;
    private String schedule;
    private double budget;
    private String notes;
    private boolean isCompleted;

    public LifestyleProfile(String profileId, String habits, String schedule, double budget, String notes) {
        this.profileId = profileId;
        this.habits = habits;
        this.schedule = schedule;
        this.budget = budget;
        this.notes = notes;
        this.isCompleted = false;
    }

    public String getProfileId() { return profileId; }

    public String getHabits() { return habits; }
    public void setHabits(String habits) { this.habits = habits; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public void updateDetails(String habits, String schedule, double budget, String notes) {
        this.habits = habits;
        this.schedule = schedule;
        this.budget = budget;
        this.notes = notes;
    }

    public void save() {  }
}
