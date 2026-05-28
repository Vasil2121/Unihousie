package unihousie.controller;

import unihousie.entity.HousingListing;
import unihousie.mock.DataStore;
import java.util.List;

public class AdminApprovalController {

    public List<HousingListing> getPendingListings() {
        return HousingListing.findPendingApproval();
    }

    public HousingListing getListingDetails(String listingId) {
        return HousingListing.getFullData(listingId);
    }

    public void processApproval(String listingId) {
        HousingListing listing = HousingListing.getFullData(listingId);
        if (listing != null) {
            listing.setStatus(HousingListing.ACTIVE);
            System.out.println(" Η αγγελία " + listingId + " εγκρίθηκε και είναι πλέον ACTIVE.");
        }
    }

    public void processRejection(String listingId, String rejectionReason) {
        HousingListing listing = HousingListing.getFullData(listingId);
        if (listing != null) {
            listing.setStatus(HousingListing.REJECTED);
            System.out.println(" Η αγγελία " + listingId + " απορρίφθηκε. Λόγος: " + rejectionReason);
        }
    }

    public void notifyApprovalSuccess() {
        System.out.println(" Επιτυχής έγκριση αγγελίας.");
    }

    public void notifyRejectionProcessed() {
        System.out.println(" Απόρριψη αγγελίας καταχωρήθηκε.");
    }
}