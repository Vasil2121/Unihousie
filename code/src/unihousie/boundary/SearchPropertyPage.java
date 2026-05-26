package unihousie.boundary;

import unihousie.controller.SearchController;
import unihousie.entity.HousingListing;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchPropertyPage extends JFrame {

    private final SearchController controller = new SearchController();
    private JPanel resultsPanel;

    public SearchPropertyPage() {
        super("UC07 — Search Property");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(920, 700);
        setLocationRelativeTo(null);
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Φίλτρα Αναζήτησης"));

        JTextField locationField = new JTextField(15);
        JTextField maxPriceField = new JTextField(8);
        JTextField roomsField = new JTextField(5);

        JButton searchBtn = new JButton("🔍 Αναζήτηση");
        searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
        searchBtn.addActionListener(e -> performSearch(
                locationField.getText().trim(),
                parseDouble(maxPriceField.getText(), 800),
                parseInt(roomsField.getText(), 1)
        ));

        filterPanel.add(new JLabel("Περιοχή:"));
        filterPanel.add(locationField);
        filterPanel.add(new JLabel("Μέγ. Τιμή (€):"));
        filterPanel.add(maxPriceField);
        filterPanel.add(new JLabel("Ελάχ. Δωμάτια:"));
        filterPanel.add(roomsField);
        filterPanel.add(searchBtn);

        add(filterPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void performSearch(String location, double maxPrice, int minRooms) {
        List<HousingListing> results = controller.queryListings(location, maxPrice, minRooms);
        displayPropertyCards(results);
    }

    public void display() {
        setVisible(true);
    }

    public void searchProperties(String location, double maxPrice, int roomCount) {
        List<HousingListing> results = controller.queryListings(location, maxPrice, roomCount);
        displayPropertyCards(results);
    }

    public void displayPropertyCards(List<HousingListing> listings) {
        resultsPanel.removeAll();

        if (listings.isEmpty()) {
            JLabel empty = new JLabel("Δεν βρέθηκαν ακίνητα που να ταιριάζουν στα κριτήριά σας.", SwingConstants.CENTER);
            empty.setFont(new Font("Arial", Font.PLAIN, 16));
            resultsPanel.add(empty);
        } else {
            for (HousingListing listing : listings) {
                resultsPanel.add(createPropertyCard(listing));
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel createPropertyCard(HousingListing l) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        card.setBackground(Color.WHITE);

        JLabel title = new JLabel(l.getTitle());
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel details = new JLabel(
                "<html>" +
                        l.getRooms() + " δωμάτια • " + l.getSqm() + "τμ<br>" +
                        "<b>" + l.getRent() + " €/μήνα</b><br>" +
                        l.getAddress() +
                        "</html>"
        );

        JButton detailsBtn = new JButton("Δες Λεπτομέρειες");
        detailsBtn.addActionListener(e -> clickPropertyCard(l.getListingId()));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(detailsBtn);

        card.add(title, BorderLayout.NORTH);
        card.add(details, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        return card;
    }

    public void clickPropertyCard(String listingId) {
        HousingListing listing = controller.getDetailedListing(listingId);
        if (listing != null) {
            new PropertyDetailsPage(listing).setVisible(true);
        }
    }

    private double parseDouble(String text, double defaultValue) {
        try {
            return Double.parseDouble(text.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private int parseInt(String text, int defaultValue) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }
}