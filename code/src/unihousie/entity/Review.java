package unihousie.entity;

import java.util.Date;

public class Review {
    private String reviewId;
    private String studentId;
    private String listingId;
    private int stars;
    private String text;
    private Date timestamp;

    public Review(String reviewId, String studentId, String listingId, int stars, String text) {
        this.reviewId = reviewId;
        this.studentId = studentId;
        this.listingId = listingId;
        this.stars = stars;
        this.text = text;
        this.timestamp = new Date();
    }

    public String getReviewId() { return reviewId; }
    public String getStudentId() { return studentId; }
    public String getListingId() { return listingId; }

    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Date getTimestamp() { return timestamp; }

    public void save() {  }
}
