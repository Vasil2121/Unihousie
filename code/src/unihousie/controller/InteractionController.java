package unihousie.controller;

import unihousie.boundary.ChatWindow;
import unihousie.entity.MutualMatch;
import javax.swing.JOptionPane;
import java.util.Optional;

public class InteractionController {

    public void registerLike(String studentId, String targetUserId) {
        Optional<MutualMatch> existingPending = MutualMatch.findPending(studentId, targetUserId);

        if (existingPending.isPresent()) {
            MutualMatch match = existingPending.get();
            match.setStatus("ACTIVE");
            match.setUpdatedAt(java.time.LocalDateTime.now());
            unihousie.mock.DataStore.save(match);

            System.out.println("🎉 MUTUAL MATCH! Μεταξύ " + studentId + " και " + targetUserId);

            JOptionPane.showMessageDialog(null,
                    "🎉 Αμοιβαίο Match!\n\nΜπορείτε τώρα να συνομιλήσετε!",
                    "Mutual Match", JOptionPane.INFORMATION_MESSAGE);

            new ChatWindow(match.getMatchId(), studentId, targetUserId).setVisible(true);

        } else {
            MutualMatch.createNew(studentId, targetUserId, "PENDING");
            System.out.println("Like καταχωρήθηκε (PENDING) από " + studentId + " προς " + targetUserId);
        }
    }
}