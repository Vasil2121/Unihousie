package unihousie.controller;

import unihousie.entity.HousingListing;
import java.util.List;

public class SearchController {

    public List<HousingListing> queryListings(String location, double maxPrice, int roomCount) {
        return HousingListing.findMatchingActive(location, maxPrice, roomCount);
    }

    public HousingListing getDetailedListing(String listingId) {
        return HousingListing.getFullData(listingId);
    }
}