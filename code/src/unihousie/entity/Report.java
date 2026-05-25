package unihousie.entity;

import java.util.Date;

public class Report {
    public static final String OPEN = "OPEN";
    public static final String RESOLVED = "RESOLVED";
    public static final String DISMISSED = "DISMISSED";

    private String reportId;
    private String reporterId;
    private String reportedEntityId;
    private String reasonCode;
    private String text;
    private String status;
    private Date createdAt;

    public Report(String reportId, String reporterId, String reportedEntityId,
                  String reasonCode, String text, String status) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.reportedEntityId = reportedEntityId;
        this.reasonCode = reasonCode;
        this.text = text;
        this.status = status;
        this.createdAt = new Date();
    }

    public String getReportId() { return reportId; }
    public String getReporterId() { return reporterId; }
    public String getReportedEntityId() { return reportedEntityId; }

    public String getReasonCode() { return reasonCode; }
    public void setReasonCode(String reasonCode) { this.reasonCode = reasonCode; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }

    public void save() {  }
}
