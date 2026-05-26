package unihousie.boundary;

import unihousie.Session;
import unihousie.controller.ChatController;
import unihousie.entity.Message;
import unihousie.entity.MutualMatch;
import unihousie.entity.Student;
import unihousie.mock.DataStore;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatWindow extends JFrame {

    private String matchId;
    private final String currentUserId;
    private String otherUserId;
    private final ChatController controller;

    private JTextArea chatArea;
    private JTextField inputField;

    public ChatWindow(String matchId, String currentUserId, String otherUserId) {
        this.matchId = matchId;
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
        this.controller = new ChatController();
        initChatWindow();
    }

    public ChatWindow(String matchId, String otherUserId) {
        this(matchId, Session.getCurrentUserId(), otherUserId);
    }

    public ChatWindow() {
        this.currentUserId = Session.getCurrentUserId();
        this.controller = new ChatController();
        showMatchSelectionDialog();
    }

    private void initChatWindow() {
        if (!MutualMatch.validateActiveMatch(matchId, currentUserId)) {
            JOptionPane.showMessageDialog(this,
                    "Δεν έχεις ενεργό match με αυτόν τον χρήστη!",
                    "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle("💬 " + getOtherUserName());
        setSize(580, 760);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        loadChatHistory();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 0));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 122, 255));
        header.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("💬 " + getOtherUserName(), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFocusable(false);
        chatArea.setHighlighter(null);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 15));
        chatArea.setMargin(new Insets(15, 15, 15, 15));
        chatArea.setBackground(new Color(245, 247, 250));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(12, 0));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(14, 16, 18, 16));
        inputPanel.setBackground(Color.WHITE);

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setPreferredSize(new Dimension(0, 55));   // ← Μεγέθυνση

        JButton sendBtn = new JButton("Αποστολή");
        sendBtn.setFont(new Font("Arial", Font.BOLD, 15));
        sendBtn.setBackground(new Color(0, 122, 255));
        sendBtn.setForeground(Color.BLUE);
        sendBtn.setFocusPainted(false);
        sendBtn.setPreferredSize(new Dimension(110, 55));    // ← Μεγέθυνση

        sendBtn.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        controller.sendMessage(currentUserId, matchId, text);
        appendMessage("Εσύ: " + text);
        inputField.setText("");
    }

    private void appendMessage(String message) {
        chatArea.append(message + "\n\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void loadChatHistory() {
        chatArea.setText("");

        List<Message> messages = DataStore.findAll(Message.class);
        for (Message msg : messages) {
            if (msg.getMatchId() != null && msg.getMatchId().equals(matchId)) {
                String prefix = msg.getSenderId().equals(currentUserId) ? "Εσύ" : getOtherUserName();
                chatArea.append(prefix + ": " + msg.getText() + "\n\n");
            }
        }
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private String getOtherUserName() {
        Student s = DataStore.findStudent(otherUserId);
        return s != null ? s.getFullName() : otherUserId;
    }

    private void showMatchSelectionDialog() {
        List<MutualMatch> activeMatches = DataStore.getActiveMatchesForUser(currentUserId);

        if (activeMatches.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Δεν έχεις ακόμα ενεργά matches.\n\nΚάνε Like σε κάποιον από το UC04!",
                    "UC05 — Chat", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

        String[] options = new String[activeMatches.size()];
        for (int i = 0; i < activeMatches.size(); i++) {
            MutualMatch m = activeMatches.get(i);
            String otherId = m.getStudentAId().equals(currentUserId) ? m.getStudentBId() : m.getStudentAId();
            Student other = DataStore.findStudent(otherId);
            options[i] = other != null ? other.getFullName() : otherId;
        }

        String selected = (String) JOptionPane.showInputDialog(this,
                "Επιλέξτε με ποιον θέλετε να συνομιλήσετε:",
                "UC05 — Επιλογή Συνομιλίας",
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (selected != null) {
            for (int i = 0; i < activeMatches.size(); i++) {
                if (options[i].equals(selected)) {
                    MutualMatch m = activeMatches.get(i);
                    this.matchId = m.getMatchId();
                    this.otherUserId = m.getStudentAId().equals(currentUserId) ? m.getStudentBId() : m.getStudentAId();
                    initChatWindow();
                    return;
                }
            }
        } else {
            dispose();
        }
    }
}