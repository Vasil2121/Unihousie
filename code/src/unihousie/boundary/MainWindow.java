package unihousie.boundary;

import unihousie.entity.Admin;
import unihousie.entity.Landlord;
import unihousie.entity.Student;
import unihousie.mock.DataStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("UniHousie — Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("UniHousie", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));
        add(title, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Φοιτητής",     buildStudentTab());
        tabs.addTab("Ιδιοκτήτης",   buildLandlordTab());
        tabs.addTab("Διαχειριστής", buildAdminTab());
        add(tabs, BorderLayout.CENTER);
    }

    private JComponent buildStudentTab() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        DefaultListModel<Student> model = new DefaultListModel<>();
        for (Student s : DataStore.students) model.addElement(s);
        JList<Student> list = new JList<>(model);
        list.setCellRenderer((lst, value, idx, sel, foc) -> {
            String tag = value.isVerified() ? "VERIFIED" : "UNVERIFIED";
            JLabel l = new JLabel(value.getFullName() + "   [" + tag + "]   — " + value.getDepartment());
            l.setOpaque(true);
            if (sel) { l.setBackground(lst.getSelectionBackground()); l.setForeground(lst.getSelectionForeground()); }
            l.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
            return l;
        });
        list.setSelectedIndex(0);
        panel.add(new JScrollPane(list), BorderLayout.WEST);

        JPanel actions = new JPanel(new GridLayout(0, 1, 6, 6));
        actions.setBorder(BorderFactory.createTitledBorder("Use cases φοιτητή"));
        actions.add(actionButton("UC01 — Verification",          e -> new VerificationPage().display()));
        actions.add(actionButton("UC02 — Lifestyle profile",     e -> new LifestyleProfilePage().display()));
        actions.add(actionButton("UC03 — Browse roommates",      e -> new BrowseRoommatesPage().display()));
        actions.add(actionButton("UC04 — Roommate card / Like",  e -> new RoommateCard().display()));
        actions.add(actionButton("UC05 — Chat window",           e -> new ChatWindow().display()));
        actions.add(actionButton("UC07 — Search property",       e -> new SearchPropertyPage().display()));
        actions.add(actionButton("UC08 — Express interest",      e -> new PropertyDetailsPage().display()));
        actions.add(actionButton("UC10 — Schedule visit",        e -> new ScheduleVisitPage().display()));
        actions.add(actionButton("UC11 — Submit review",         e -> new ReviewPage().display()));
        actions.add(actionButton("UC09 — Report a user",         e -> new ReportUserForm().display()));
        panel.add(actions, BorderLayout.CENTER);

        return panel;
    }

    private JComponent buildLandlordTab() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        DefaultListModel<Landlord> model = new DefaultListModel<>();
        for (Landlord l : DataStore.landlords) model.addElement(l);
        JList<Landlord> list = new JList<>(model);
        list.setCellRenderer((lst, value, idx, sel, foc) -> {
            JLabel l = new JLabel(value.getFullName() + "   — " + value.getEmail());
            l.setOpaque(true);
            if (sel) { l.setBackground(lst.getSelectionBackground()); l.setForeground(lst.getSelectionForeground()); }
            l.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
            return l;
        });
        list.setSelectedIndex(0);
        panel.add(new JScrollPane(list), BorderLayout.WEST);

        JPanel actions = new JPanel(new GridLayout(0, 1, 6, 6));
        actions.setBorder(BorderFactory.createTitledBorder("Use cases ιδιοκτήτη"));
        actions.add(actionButton("UC06 — New listing", e -> new NewListingPage().display()));
        panel.add(actions, BorderLayout.CENTER);

        return panel;
    }

    private JComponent buildAdminTab() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        DefaultListModel<Admin> model = new DefaultListModel<>();
        for (Admin a : DataStore.admins) model.addElement(a);
        JList<Admin> list = new JList<>(model);
        list.setCellRenderer((lst, value, idx, sel, foc) -> {
            JLabel l = new JLabel(value.getFullName() + "   — " + value.getEmail());
            l.setOpaque(true);
            if (sel) { l.setBackground(lst.getSelectionBackground()); l.setForeground(lst.getSelectionForeground()); }
            l.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
            return l;
        });
        list.setSelectedIndex(0);
        panel.add(new JScrollPane(list), BorderLayout.WEST);

        JPanel actions = new JPanel(new GridLayout(0, 1, 6, 6));
        actions.setBorder(BorderFactory.createTitledBorder("Use cases διαχειριστή"));
        actions.add(actionButton("UC06 — Listing approval queue", e -> new AdminApprovalPage().display()));
        actions.add(actionButton("UC09 — Reports queue",          e -> new AdminReportPage().display()));
        panel.add(actions, BorderLayout.CENTER);

        return panel;
    }

    private JButton actionButton(String label, java.awt.event.ActionListener action) {
        JButton b = new JButton(label);
        b.addActionListener(action);
        return b;
    }
}
