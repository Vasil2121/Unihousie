package unihousie.entity;

import unihousie.mock.DataStore;
import java.time.LocalDateTime;
import java.util.Optional;

public class MutualMatch {

    private String id;
    private String studentAId;
    private String studentBId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MutualMatch createNew(String likerId, String targetId, String status) {
        MutualMatch match = new MutualMatch();

        if ("PENDING".equals(status)) {
            match.setStudentAId(likerId);
            match.setStudentBId(targetId);
        } else {
            if (likerId.compareTo(targetId) < 0) {
                match.setStudentAId(likerId);
                match.setStudentBId(targetId);
            } else {
                match.setStudentAId(targetId);
                match.setStudentBId(likerId);
            }
        }

        match.setStatus(status != null ? status : "PENDING");
        match.setCreatedAt(LocalDateTime.now());
        match.setUpdatedAt(LocalDateTime.now());

        DataStore.save(match);
        return match;
    }

    public static Optional<MutualMatch> findPending(String userId, String targetUserId) {
        return DataStore.findAll(MutualMatch.class).stream()
                .filter(m -> "PENDING".equals(m.getStatus()))
                .filter(m -> (m.getStudentAId().equals(userId) && m.getStudentBId().equals(targetUserId)) ||
                        (m.getStudentAId().equals(targetUserId) && m.getStudentBId().equals(userId)))
                .findFirst();
    }

    public static boolean validateActiveMatch(String matchId, String userId) {
        return DataStore.findById(MutualMatch.class, matchId)
                .map(m -> "ACTIVE".equals(m.getStatus()) &&
                        (m.getStudentAId().equals(userId) || m.getStudentBId().equals(userId)))
                .orElse(false);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMatchId() { return id; }
    public void setMatchId(String matchId) { this.id = matchId; }

    public String getStudentAId() { return studentAId; }
    public void setStudentAId(String studentAId) { this.studentAId = studentAId; }

    public String getStudentBId() { return studentBId; }
    public void setStudentBId(String studentBId) { this.studentBId = studentBId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}