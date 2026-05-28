package unihousie.controller;

import unihousie.boundary.ChatWindow;
import unihousie.entity.MutualMatch;
import unihousie.mock.DataStore;

import javax.swing.JOptionPane;
import java.util.Optional;

public class InteractionController {

    public void registerLike(String likerId, String targetUserId) {
        if (likerId.equals(targetUserId)) {
            return;
        }

        Optional<MutualMatch> existing = findExistingMatch(likerId, targetUserId);

        if (existing.isPresent()) {
            MutualMatch match = existing.get();

            if ("PENDING".equals(match.getStatus())) {
                match.setStatus("ACTIVE");
                match.setUpdatedAt(java.time.LocalDateTime.now());
                DataStore.save(match);

                System.out.println("🎉 MUTUAL MATCH! Μεταξύ " + likerId + " και " + targetUserId);

                JOptionPane.showMessageDialog(null,
                        "🎉 Αμοιβαίο Match!\n\nΜπορείτε τώρα να συνομιλήσετε!",
                        "Mutual Match", JOptionPane.INFORMATION_MESSAGE);

                new ChatWindow(match.getMatchId(), likerId, targetUserId).setVisible(true);

            } else if ("ACTIVE".equals(match.getStatus())) {
                JOptionPane.showMessageDialog(null,
                        "Έχετε ήδη αμοιβαίο match με αυτόν τον χρήστη!",
                        "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
            }
            return;
        }

        MutualMatch.createNew(likerId, targetUserId, "PENDING");
        System.out.println(" Like καταχωρήθηκε (PENDING) από " + likerId + " προς " + targetUserId);
    }

    private Optional<MutualMatch> findExistingMatch(String user1, String user2) {
        return DataStore.matches.stream()
                .filter(m ->
                        (m.getStudentAId().equals(user1) && m.getStudentBId().equals(user2)) ||
                                (m.getStudentAId().equals(user2) && m.getStudentBId().equals(user1)))
                .findFirst();
    }
}