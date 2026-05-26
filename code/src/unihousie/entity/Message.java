package unihousie.entity;

import unihousie.mock.DataStore;
import java.time.LocalDateTime;

public class Message {

    private String id;
    private String matchId;
    private String senderId;
    private String text;
    private LocalDateTime timestamp;

    public static Message createNew(String matchId, String senderId, String text) {
        Message msg = new Message();
        msg.setMatchId(matchId);
        msg.setSenderId(senderId);
        msg.setText(text);
        msg.setTimestamp(LocalDateTime.now());
        DataStore.save(msg);
        return msg;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}