package unihousie.entity;

import java.util.Date;

public class InterestExpression {
    public static final String PENDING_CONTACT = "PENDING_CONTACT";
    public static final String CONTACTED = "CONTACTED";
    public static final String DECLINED = "DECLINED";

    private String interestId;
    private String seekerId;
    private String listingId;
    private String status;
    private Date createdAt;

    public InterestExpression(String interestId, String seekerId, String listingId, String status) {
        this.interestId = interestId;
        this.seekerId = seekerId;
        this.listingId = listingId;
        this.status = status;
        this.createdAt = new Date();
    }

    public String getInterestId() { return interestId; }
    public String getSeekerId() { return seekerId; }
    public String getListingId() { return listingId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }

    public void save() {  }
}
