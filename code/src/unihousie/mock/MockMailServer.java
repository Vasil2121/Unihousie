package unihousie.mock;

import javax.swing.*;

public class MockMailServer {

    public boolean sendVerificationEmail(String userEmail, String token) {
        String msg = "[MockMailServer] Email → " + userEmail + "  |  Token: " + token + "  (valid 30 minutes)";
        System.out.println(msg);

        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                null,
                "<html><b>📧 Email εστάλη</b><br><br>"
                    + "Προς: <b>" + userEmail + "</b><br>"
                    + "Θέμα: <i>UniHousie — Επαλήθευση Λογαριασμού</i><br><br>"
                    + "Για να επαληθεύσετε τον λογαριασμό σας, παστάρετε τον παρακάτω κωδικό<br>"
                    + "στη φόρμα της εφαρμογής:<br><br>"
                    + "<b style='font-size:14px'>" + token + "</b><br><br>"
                    + "<i>Ισχύς: 30 λεπτά</i></html>",
                "Mock UPatras Mail Server",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        return true;
    }
}
