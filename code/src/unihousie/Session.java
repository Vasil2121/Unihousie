package unihousie;

public class Session {
    private static String currentUserId = "stud_1";   // default

    public static void setCurrentUser(String userId) {
        currentUserId = userId;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }
}