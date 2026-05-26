package unihousie.boundary;

import unihousie.controller.SearchController;
import unihousie.entity.HousingListing;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.List;

public class SearchPropertyPage extends JFrame {

    private final SearchController controller = new SearchController();
    private JPanel resultsPanel;

    private JTextField maxPriceField;
    private JTextField minRoomsField;

    public SearchPropertyPage() {
        super("UC07 — Αναζήτηση Ακινήτων");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(920, 700);
        setLocationRelativeTo(null);
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));


        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 12));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Φίλτρα Αναζήτησης"));

        maxPriceField = new JTextField(10);
        minRoomsField = new JTextField(6);


        maxPriceField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;
                if (str.matches("\\d*")) {
                    super.insertString(offs, str, a);
                }
            }
        });

        minRoomsField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;
                if (str.matches("\\d*")) {
                    super.insertString(offs, str, a);
                }
            }
        });

        JButton searchBtn = new JButton("🔍 Αναζήτηση");
        searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
        searchBtn.addActionListener(e -> performSearch());

        filterPanel.add(new JLabel("Μέγιστη Τιμή (€):"));
        filterPanel.add(maxPriceField);
        filterPanel.add(new JLabel("Ελάχιστα Δωμάτια:"));
        filterPanel.add(minRoomsField);
        filterPanel.add(searchBtn);

        add(filterPanel, BorderLayout.NORTH);


        resultsPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void performSearch() {
        double maxPrice = parseDouble(maxPriceField.getText(), 2000.0);
        int minRooms = parseInt(minRoomsField.getText(), 1);

        List<HousingListing> results = controller.queryListings("", maxPrice, minRooms);
        displayPropertyCards(results);
    }

    public void display() {
        setVisible(true);
    }

    public void displayPropertyCards(List<HousingListing> listings) {
        resultsPanel.removeAll();

        if (listings.isEmpty()) {
            JLabel empty = new JLabel("Δεν βρέθηκαν ακίνητα με αυτά τα κριτήρια.", SwingConstants.CENTER);
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
        JPanel card = new JPanel(new BorderLayout(10, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        card.setBackground(Color.WHITE);

        JLabel title = new JLabel(l.getTitle());
        title.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel info = new JLabel(
                l.getRooms() + " δωμάτια • " + l.getSqm() + " τ.μ. • " +
                        String.format("%.0f €/μήνα", l.getRent())
        );

        JLabel address = new JLabel(l.getAddress());
        address.setForeground(Color.GRAY);

        JButton detailsBtn = new JButton("Δες Λεπτομέρειες");
        detailsBtn.addActionListener(e -> clickPropertyCard(l.getListingId()));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(detailsBtn);

        card.add(title, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        card.add(address, BorderLayout.SOUTH);
        card.add(bottom, BorderLayout.EAST);

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