package unihousie.boundary;

import unihousie.controller.VerificationController;
import unihousie.entity.Student;

import javax.swing.*;
import java.awt.*;

public class VerificationPage extends JFrame {

    private final Student student;
    private final VerificationController controller = new VerificationController();

    private final JLabel statusLabel = new JLabel();

    private final JTextField protocolField = new JTextField(20);
    private final JTextField otpField      = new JTextField(10);
    private final JButton sendOtpBtn       = new JButton("Αποστολή OTP");
    private final JButton verifyOtpBtn     = new JButton("Επαλήθευση OTP");
    private final JLabel  otpStatusLabel   = new JLabel(" ");

    private final JTextField emailField    = new JTextField(20);
    private final JTextField tokenField    = new JTextField(15);
    private final JButton sendEmailBtn     = new JButton("Αποστολή Email");
    private final JButton verifyTokenBtn   = new JButton("Επαλήθευση Token");
    private final JLabel  emailStatusLabel = new JLabel(" ");

    public VerificationPage(Student student) {
        super("UC01 — Επαλήθευση Φοιτητικής Ταυτότητας");
        this.student = student;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(620, 460);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        add(buildHeader(),                              BorderLayout.NORTH);
        add(buildFlowsTabs(),                           BorderLayout.CENTER);
        add(new JLabel("UniHousie — UC01 demo", SwingConstants.CENTER), BorderLayout.SOUTH);

        wireGovFlow();
        wireEmailFlow();
        refreshStatusLabel();
    }

    public void display() { setVisible(true); }

    private JComponent buildHeader() {
        JPanel header = new JPanel(new GridLayout(0, 1, 2, 2));
        header.setBorder(BorderFactory.createEmptyBorder(12, 16, 8, 16));
        JLabel name  = new JLabel("Φοιτητής: " + student.getFullName() + "  (" + student.getDepartment() + ")");
        name.setFont(name.getFont().deriveFont(Font.BOLD, 14f));
        JLabel email = new JLabel("Email: " + student.getEmail() + "    Τηλέφωνο: " + student.getPhone());
        header.add(name);
        header.add(email);
        header.add(statusLabel);
        return header;
    }

    private void refreshStatusLabel() {
        boolean verified = student.isVerified();
        statusLabel.setText("Κατάσταση επαλήθευσης: " + (verified ? "✓ VERIFIED" : "✗ UNVERIFIED"));
        statusLabel.setForeground(verified ? new Color(0x1d6e2c) : new Color(0xa32d2d));

        boolean enable = !verified;
        sendOtpBtn.setEnabled(enable);     verifyOtpBtn.setEnabled(enable);
        sendEmailBtn.setEnabled(enable);   verifyTokenBtn.setEnabled(enable);
    }

    private JComponent buildFlowsTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Βασική Ροή — gov.gr OTP",        buildGovFlowPanel());
        tabs.addTab("Εναλλακτική — Ακαδημαϊκό Email", buildEmailFlowPanel());
        return tabs;
    }

    private JComponent buildGovFlowPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        p.add(label("1. Δώστε αριθμό πρωτοκόλλου gov.gr (π.χ. GOV-" + student.getUserId() + "):"));
        p.add(row(protocolField, sendOtpBtn));
        p.add(Box.createVerticalStrut(14));
        p.add(label("2. Παστάρετε τον OTP που λάβατε με SMS:"));
        p.add(row(otpField, verifyOtpBtn));
        p.add(Box.createVerticalStrut(10));
        otpStatusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(otpStatusLabel);

        return p;
    }

    private JComponent buildEmailFlowPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        p.add(label("1. Δώστε το ακαδημαϊκό σας email (@ceid.upatras.gr):"));
        emailField.setText(student.getEmail());
        p.add(row(emailField, sendEmailBtn));
        p.add(Box.createVerticalStrut(14));
        p.add(label("2. Παστάρετε τον κωδικό που λάβατε με email:"));
        p.add(row(tokenField, verifyTokenBtn));
        p.add(Box.createVerticalStrut(10));
        emailStatusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(emailStatusLabel);

        return p;
    }

    private void wireGovFlow() {
        sendOtpBtn.addActionListener(e -> {
            try {
                controller.verifyProtocol(protocolField.getText().trim());
                setStatus(otpStatusLabel, "OTP εστάλη με SMS. Δείτε το popup.", false);
            } catch (IllegalArgumentException ex) {
                setStatus(otpStatusLabel, ex.getMessage(), true);
            }
        });
        verifyOtpBtn.addActionListener(e -> {
            boolean ok = controller.validateOTP(otpField.getText().trim());
            if (ok) {
                setStatus(otpStatusLabel, "Επιτυχής επαλήθευση. Ο λογαριασμός είναι πλέον VERIFIED.", false);
                JOptionPane.showMessageDialog(this, "✓ Επιτυχής επαλήθευση!",
                        "Verification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                setStatus(otpStatusLabel, "Αποτυχία: λάθος OTP ή λήξη.", true);
            }
            refreshStatusLabel();
        });
    }

    private void wireEmailFlow() {
        sendEmailBtn.addActionListener(e -> {
            try {
                controller.sendEmailLink(emailField.getText().trim());
                setStatus(emailStatusLabel, "Email εστάλη. Δείτε το popup για τον κωδικό.", false);
            } catch (IllegalArgumentException ex) {
                setStatus(emailStatusLabel, ex.getMessage(), true);
            }
        });
        verifyTokenBtn.addActionListener(e -> {
            boolean ok = controller.verifyToken(tokenField.getText().trim());
            if (ok) {
                setStatus(emailStatusLabel, "Επιτυχής επαλήθευση. Ο λογαριασμός είναι πλέον VERIFIED.", false);
                JOptionPane.showMessageDialog(this, "✓ Επιτυχής επαλήθευση!",
                        "Verification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                setStatus(emailStatusLabel, "Αποτυχία: λάθος ή ληγμένος κωδικός.", true);
            }
            refreshStatusLabel();
        });
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JPanel row(JComponent left, JComponent right) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(left);
        row.add(right);
        return row;
    }

    private void setStatus(JLabel target, String msg, boolean error) {
        target.setText(msg);
        target.setForeground(error ? new Color(0xa32d2d) : new Color(0x1d6e2c));
    }
}
