package unihousie.entity;

import java.util.Date;

public class VerificationAttempt {
    public static final String METHOD_OTP_GOVGR = "OTP_GOVGR";
    public static final String METHOD_EMAIL_LINK = "EMAIL_LINK";

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED = "FAILED";

    private String attemptId;
    private String studentId;
    private String method;
    private String status;
    private Date timestamp;

    private String otpCode;
    private String emailToken;
    private Date expiresAt;

    public VerificationAttempt(String attemptId, String studentId, String method) {
        this.attemptId = attemptId;
        this.studentId = studentId;
        this.method = method;
        this.status = STATUS_PENDING;
        this.timestamp = new Date();
    }

    public String getAttemptId() { return attemptId; }
    public String getStudentId() { return studentId; }
    public String getMethod() { return method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getTimestamp() { return timestamp; }

    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }

    public String getEmailToken() { return emailToken; }
    public void setEmailToken(String emailToken) { this.emailToken = emailToken; }

    public Date getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Date expiresAt) { this.expiresAt = expiresAt; }

    public boolean isExpired() {
        return expiresAt != null && new Date().after(expiresAt);
    }

    public void save() {  }
}
