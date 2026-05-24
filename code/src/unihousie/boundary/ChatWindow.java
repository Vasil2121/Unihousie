package unihousie.boundary;

import javax.swing.*;

public class ChatWindow extends JFrame {

    public ChatWindow() {
        super("ChatWindow");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);

    }

    public void display() {
        setVisible(true);
    }
}
