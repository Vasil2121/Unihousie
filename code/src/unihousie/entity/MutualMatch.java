package unihousie.entity;

import java.util.Date;

public class MutualMatch {
    public static final String PENDING = "PENDING";
    public static final String MUTUAL = "MUTUAL";

    private String matchId;
    private String studentAId;
    private String studentBId;
    private String status;
    private Date createdAt;

    public MutualMatch(String matchId, String studentAId, String studentBId, String status) {
        this.matchId = matchId;
        this.studentAId = studentAId;
        this.studentBId = studentBId;
        this.status = status;
        this.createdAt = new Date();
    }

    public String getMatchId() { return matchId; }
    public String getStudentAId() { return studentAId; }
    public String getStudentBId() { return studentBId; }
    public Date getCreatedAt() { return createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isMutual() { return MUTUAL.equals(status); }

    public boolean validateActiveMatch(String matchId, String senderId) {
        return this.matchId.equals(matchId)
                && isMutual()
                && (studentAId.equals(senderId) || studentBId.equals(senderId));
    }

    public boolean involves(String studentId) {
        return studentAId.equals(studentId) || studentBId.equals(studentId);
    }

    public String otherParticipant(String studentId) {
        if (studentAId.equals(studentId)) return studentBId;
        if (studentBId.equals(studentId)) return studentAId;
        return null;
    }

    public void save() {  }
}
