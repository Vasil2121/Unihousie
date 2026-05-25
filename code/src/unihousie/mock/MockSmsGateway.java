package unihousie.mock;

import javax.swing.*;

public class MockSmsGateway {

    public boolean sendOTP(String phone, String otp) {
        String msg = "[MockSmsGateway] SMS → " + phone + "  |  OTP: " + otp + "  (valid 5 minutes)";
        System.out.println(msg);

        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                null,
                "<html><b>📱 SMS εστάλη</b><br><br>"
                    + "Προς: <b>" + phone + "</b><br>"
                    + "Κωδικός μίας χρήσης (OTP): <b style='font-size:18px'>" + otp + "</b><br><br>"
                    + "<i>Ισχύς: 5 λεπτά</i></html>",
                "Mock SMS Gateway",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        return true;
    }
}
