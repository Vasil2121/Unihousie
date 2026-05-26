package unihousie.controller;

import unihousie.entity.HousingListing;
import java.util.List;

public class ListingController {

    public String submitListing(String landlordId, String title, String address, double price,
                                String description, List<String> amenities, List<String> photos) {

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Ο τίτλος είναι υποχρεωτικός");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Η τιμή πρέπει να είναι μεγαλύτερη από 0");
        }

        HousingListing listing = HousingListing.createNew(landlordId, title, address,
                price, description, amenities, photos);

        notifySubmissionSuccess(listing.getListingId());
        return listing.getListingId();
    }

    public void notifySubmissionSuccess(String listingId) {
        System.out.println("✅ Η αγγελία " + listingId + " υποβλήθηκε επιτυχώς (PENDING_APPROVAL)");
    }
}