package unihousie.controller;

import unihousie.entity.Student;
import unihousie.entity.VerificationAttempt;
import unihousie.mock.DataStore;
import unihousie.mock.MockGovIdService;
import unihousie.mock.MockMailServer;
import unihousie.mock.MockSmsGateway;

import java.util.Date;
import java.util.UUID;

public class VerificationController {

    private static final String FIXED_OTP = "123456";

    private static final long OTP_TTL_MS   = 5L  * 60 * 1000;
    private static final long EMAIL_TTL_MS = 30L * 60 * 1000;

    private final MockGovIdService govIdService = new MockGovIdService();
    private final MockSmsGateway smsGateway     = new MockSmsGateway();
    private final MockMailServer mailServer     = new MockMailServer();

    private VerificationAttempt currentAttempt;

    public void verifyProtocol(String protocolNum) {
        MockGovIdService.StudentData data = govIdService.validateStudent(protocolNum);
        if (data == null) {
            throw new IllegalArgumentException("Μη έγκυρος αριθμός πρωτοκόλλου");
        }

        VerificationAttempt attempt = new VerificationAttempt(
                DataStore.nextId("att_", DataStore.verificationAttempts.size()),
                data.studentId,
                VerificationAttempt.METHOD_OTP_GOVGR
        );
        attempt.setOtpCode(FIXED_OTP);
        attempt.setExpiresAt(new Date(System.currentTimeMillis() + OTP_TTL_MS));
        DataStore.verificationAttempts.add(attempt);
        attempt.save();
        this.currentAttempt = attempt;

        smsGateway.sendOTP(data.phone, FIXED_OTP);
    }

    public boolean validateOTP(String inputOTP) {
        if (currentAttempt == null || !VerificationAttempt.METHOD_OTP_GOVGR.equals(currentAttempt.getMethod())) {
            System.out.println("[VerificationController] No active OTP attempt to validate.");
            return false;
        }
        if (currentAttempt.isExpired()) {
            currentAttempt.setStatus(VerificationAttempt.STATUS_FAILED);
            currentAttempt.save();
            System.out.println("[VerificationController] OTP attempt expired.");
            return false;
        }
        if (!currentAttempt.getOtpCode().equals(inputOTP)) {
            currentAttempt.setStatus(VerificationAttempt.STATUS_FAILED);
            currentAttempt.save();
            System.out.println("[VerificationController] OTP mismatch.");
            return false;
        }

        markStudentVerified(currentAttempt);
        return true;
    }

    public void sendEmailLink(String userEmail) {
        if (userEmail == null || !userEmail.toLowerCase().endsWith("@ceid.upatras.gr")) {
            throw new IllegalArgumentException("Το email πρέπει να ανήκει στο domain @ceid.upatras.gr");
        }
        Student s = findStudentByEmail(userEmail);
        if (s == null) {
            throw new IllegalArgumentException("Δεν βρέθηκε φοιτητής με αυτό το email");
        }

        String token = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        VerificationAttempt attempt = new VerificationAttempt(
                DataStore.nextId("att_", DataStore.verificationAttempts.size()),
                s.getUserId(),
                VerificationAttempt.METHOD_EMAIL_LINK
        );
        attempt.setEmailToken(token);
        attempt.setExpiresAt(new Date(System.currentTimeMillis() + EMAIL_TTL_MS));
        DataStore.verificationAttempts.add(attempt);
        attempt.save();
        this.currentAttempt = attempt;

        mailServer.sendVerificationEmail(userEmail, token);
    }

    public boolean verifyToken(String token) {
        if (currentAttempt == null || !VerificationAttempt.METHOD_EMAIL_LINK.equals(currentAttempt.getMethod())) {
            System.out.println("[VerificationController] No active email attempt to validate.");
            return false;
        }
        if (currentAttempt.isExpired()) {
            currentAttempt.setStatus(VerificationAttempt.STATUS_FAILED);
            currentAttempt.save();
            System.out.println("[VerificationController] Email token expired.");
            return false;
        }
        if (!currentAttempt.getEmailToken().equals(token)) {
            currentAttempt.setStatus(VerificationAttempt.STATUS_FAILED);
            currentAttempt.save();
            System.out.println("[VerificationController] Email token mismatch.");
            return false;
        }

        markStudentVerified(currentAttempt);
        return true;
    }

    private void markStudentVerified(VerificationAttempt attempt) {
        attempt.setStatus(VerificationAttempt.STATUS_SUCCESS);
        attempt.save();
        Student s = DataStore.findStudent(attempt.getStudentId());
        if (s != null) {
            s.setVerificationStatus(Student.VERIFIED);
            s.save();
            System.out.println("[VerificationController] Student " + s.getFullName() + " is now VERIFIED.");
        }
    }

    private Student findStudentByEmail(String email) {
        for (Student s : DataStore.students) {
            if (s.getEmail().equalsIgnoreCase(email)) return s;
        }
        return null;
    }

    public VerificationAttempt getCurrentAttempt() { return currentAttempt; }
}
