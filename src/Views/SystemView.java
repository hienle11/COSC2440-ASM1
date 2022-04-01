package Views;

import Models.StudentEnrolment;

import java.util.List;

public class SystemView {

    public static void displayOptions() {
        System.out.println("1. View all enrolments");
        System.out.println("2. Search enrolments by student and semester");
        System.out.println("3. Search enrolments by course and semester");
        System.out.println("4. Search enrolments by semester");
        System.out.println("5. Add new enrolment");
        System.out.println("6. Update an enrolment");
        System.out.println("7. Delete an enrolment");
        System.out.println("8. Export above enrolment list");
        System.out.println("9. Exit");
    }

    public static void displayInvalidInputMessage() {
        System.out.println("---------------------------------------------");
        System.out.println("----------------Invalid Input----------------");
        System.out.println("---------------------------------------------");
    }

    public static void displayEnrolments(List<StudentEnrolment> studentEnrolments) {
        System.out.println("---------------------------------------------");
        if(studentEnrolments == null || studentEnrolments.size() == 0) {
            System.out.println("There is no matching enrolments");
        }
        for (StudentEnrolment studentEnrolment : studentEnrolments) {
            System.out.println(studentEnrolment);
        }
        System.out.println("---------------------------------------------");
    }
}
