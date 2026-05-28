package unihousie.controller;

import unihousie.entity.HousingListing;
import unihousie.entity.InterestExpression;
import unihousie.mock.DataStore;

public class InterestController {

    public void registerInterest(String studentId, String listingId) {
        HousingListing listing = DataStore.findListing(listingId);
        if (listing == null || !HousingListing.ACTIVE.equals(listing.getStatus())) {
            System.out.println(" Το ακίνητο δεν είναι διαθέσιμο.");
            return;
        }

        InterestExpression interest = new InterestExpression(
                DataStore.nextId("interest_", DataStore.interests.size()),
                studentId,
                listingId,
                InterestExpression.PENDING_CONTACT
        );

        DataStore.interests.add(interest);
        System.out.println(" Εκδήλωση ενδιαφέροντος καταχωρήθηκε για το listing " + listingId);
    }
}