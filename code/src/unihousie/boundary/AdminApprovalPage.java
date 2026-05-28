package unihousie.boundary;

import unihousie.controller.AdminApprovalController;
import unihousie.entity.HousingListing;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminApprovalPage extends JFrame {

    private final AdminApprovalController controller = new AdminApprovalController();
    private JPanel listingsPanel;
    private String selectedListingId;

    public AdminApprovalPage() {
        super("UC06 — Listing Approval Queue");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(880, 680);
        setLocationRelativeTo(null);
        buildUI();
        loadPendingListings();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Ουρά Εγκρίσεων Αγγελιών", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        listingsPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        listingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        add(new JScrollPane(listingsPanel), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton approveBtn = new JButton(" Εγκρίνω");
        JButton rejectBtn = new JButton(" Απόρριψη");

        approveBtn.addActionListener(e -> approveSelected());
        rejectBtn.addActionListener(e -> rejectSelected());

        actionPanel.add(approveBtn);
        actionPanel.add(rejectBtn);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private void loadPendingListings() {
        listingsPanel.removeAll();
        List<HousingListing> pending = controller.getPendingListings();

        if (pending.isEmpty()) {
            listingsPanel.add(new JLabel("Δεν υπάρχουν αγγελίες σε αναμονή έγκρισης.", SwingConstants.CENTER));
        } else {
            for (HousingListing listing : pending) {
                listingsPanel.add(createListingTile(listing));
            }
        }

        listingsPanel.revalidate();
        listingsPanel.repaint();
    }

    private JPanel createListingTile(HousingListing l) {
        JPanel tile = new JPanel(new BorderLayout(10, 5));
        tile.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JLabel info = new JLabel(
                "<html><b>" + l.getTitle() + "</b><br>" +
                        l.getAddress() + "<br>" +
                        l.getRent() + "€/μήνα • " + l.getRooms() + " δωμάτια</html>"
        );

        JButton selectBtn = new JButton("Επιλογή");
        selectBtn.addActionListener(e -> {
            selectedListingId = l.getListingId();
            JOptionPane.showMessageDialog(this, "Επιλέχθηκε: " + l.getTitle());
        });

        tile.add(info, BorderLayout.CENTER);
        tile.add(selectBtn, BorderLayout.EAST);

        return tile;
    }

    private void approveSelected() {
        if (selectedListingId == null) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε αγγελία.", "Προσοχή", JOptionPane.WARNING_MESSAGE);
            return;
        }
        controller.processApproval(selectedListingId);
        controller.notifyApprovalSuccess();
        JOptionPane.showMessageDialog(this, "Η αγγελία εγκρίθηκε επιτυχώς!", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        loadPendingListings();
    }

    private void rejectSelected() {
        if (selectedListingId == null) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε αγγελία.", "Προσοχή", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String reason = JOptionPane.showInputDialog(this, "Λόγος απόρριψης:", "Απόρριψη Αγγελίας", JOptionPane.QUESTION_MESSAGE);
        if (reason != null && !reason.trim().isEmpty()) {
            controller.processRejection(selectedListingId, reason);
            controller.notifyRejectionProcessed();
            JOptionPane.showMessageDialog(this, "Η αγγελία απορρίφθηκε.", "Ολοκληρώθηκε", JOptionPane.INFORMATION_MESSAGE);
            loadPendingListings();
        }
    }

    public void display() {
        setVisible(true);
    }
}