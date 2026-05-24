package unihousie.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HousingListing {
    public static final String PENDING_APPROVAL = "PENDING_APPROVAL";
    public static final String ACTIVE = "ACTIVE";
    public static final String REJECTED = "REJECTED";
    public static final String DELETED = "DELETED";

    private String listingId;
    private String landlordId;
    private String title;
    private String address;
    private String area;
    private String type;
    private int rooms;
    private double sqm;
    private double rent;
    private String availability;
    private String description;
    private List<String> photos;
    private String status;
    private double averageRating;
    private Date createdAt;

    public HousingListing(String listingId, String landlordId, String title,
                          String address, String area, String type,
                          int rooms, double sqm, double rent,
                          String availability, String description,
                          List<String> photos) {
        this.listingId = listingId;
        this.landlordId = landlordId;
        this.title = title;
        this.address = address;
        this.area = area;
        this.type = type;
        this.rooms = rooms;
        this.sqm = sqm;
        this.rent = rent;
        this.availability = availability;
        this.description = description;
        this.photos = (photos != null) ? photos : new ArrayList<>();
        this.status = PENDING_APPROVAL;
        this.averageRating = 0.0;
        this.createdAt = new Date();
    }

    public String getListingId() { return listingId; }
    public String getLandlordId() { return landlordId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getRooms() { return rooms; }
    public void setRooms(int rooms) { this.rooms = rooms; }

    public double getSqm() { return sqm; }
    public void setSqm(double sqm) { this.sqm = sqm; }

    public double getRent() { return rent; }
    public void setRent(double rent) { this.rent = rent; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getPhotos() { return photos; }
    public void setPhotos(List<String> photos) { this.photos = photos; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isActive() { return ACTIVE.equals(status); }
    public boolean isAvailable() { return ACTIVE.equals(status); }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public Date getCreatedAt() { return createdAt; }

    public void save() {  }
}
