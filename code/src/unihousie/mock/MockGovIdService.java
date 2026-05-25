package unihousie.mock;

import unihousie.entity.Student;

public class MockGovIdService {

    public static class StudentData {
        public final String studentId;
        public final String fullName;
        public final String phone;

        public StudentData(String studentId, String fullName, String phone) {
            this.studentId = studentId;
            this.fullName = fullName;
            this.phone = phone;
        }
    }

    public StudentData validateStudent(String protocolNum) {
        if (protocolNum == null || !protocolNum.toUpperCase().startsWith("GOV-")) {
            System.out.println("[MockGovIdService] Invalid protocol format: " + protocolNum);
            return null;
        }
        String studentId = protocolNum.substring(4).trim();
        Student s = DataStore.findStudent(studentId);
        if (s == null) {
            System.out.println("[MockGovIdService] Unknown student id in protocol: " + studentId);
            return null;
        }
        System.out.println("[MockGovIdService] Validated student " + s.getFullName());
        return new StudentData(s.getUserId(), s.getFullName(), s.getPhone());
    }
}
