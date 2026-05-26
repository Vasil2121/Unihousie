package unihousie.controller;

import unihousie.entity.Message;
import unihousie.entity.MutualMatch;

public class ChatController {

    public void sendMessage(String senderId, String matchId, String messageText) {
        if (messageText == null || messageText.trim().isEmpty()) return;

        if (!MutualMatch.validateActiveMatch(matchId, senderId)) {
            System.out.println("⚠ Δεν έχεις ενεργό match!");
            return;
        }

        Message.createNew(matchId, senderId, messageText.trim());
        System.out.println("📨 Μήνυμα από " + senderId + " στο match " + matchId);
    }
}