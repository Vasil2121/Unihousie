package unihousie.entity;

import java.util.Date;

public class Message {
    private String messageId;
    private String senderId;
    private String matchId;
    private String text;
    private Date timestamp;

    public Message(String messageId, String matchId, String senderId, String text) {
        this.messageId = messageId;
        this.matchId = matchId;
        this.senderId = senderId;
        this.text = text;
        this.timestamp = new Date();
    }

    public String getMessageId() { return messageId; }
    public String getSenderId() { return senderId; }
    public String getMatchId() { return matchId; }
    public String getText() { return text; }
    public Date getTimestamp() { return timestamp; }

    public void save() {  }
}
