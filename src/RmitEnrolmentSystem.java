import Enums.Option;
import Models.StudentEnrolment;
import Services.StudentEnrolmentManager;
import Services.StudentEnrolmentManagerImpl;
import Views.SystemView;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RmitEnrolmentSystem {

    static Scanner sc;
    static Option command = Option.INVALID;
    static StudentEnrolmentManager system;
    static List<StudentEnrolment> visibleEnrolments;

    public static void main(String[] args) {
        system = new StudentEnrolmentManagerImpl("src/default.csv", ",");
        System.out.println("Welcome to RMIT enrolment system");
        sc = new Scanner(System.in);
        do {
            SystemView.displayOptions();
            command = Objects.requireNonNullElse(getInputOption(command.getValue() / 10), Option.INVALID);
            switch (command) {
                case VIEW_ALL_ENROLMENTS:
                    visibleEnrolments = system.getAll();
                    SystemView.displayEnrolments(visibleEnrolments);
                    break;
                case SEARCH_ENROLMENTS_BY_STUDENT_AND_SEMESTER:
                    visibleEnrolments = searchEnrolmentsByStudentAndSemester();
                    SystemView.displayEnrolments(visibleEnrolments);
                    break;
                case SEARCH_ENROLMENTS_BY_COURSE_AND_SEMESTER:
                    visibleEnrolments = searchEnrolmentsByCourseAndSemester();
                    SystemView.displayEnrolments(visibleEnrolments);
                    break;
                case SEARCH_ENROLMENTS_BY_SEMESTER:
                    visibleEnrolments = searchEnrolmentsBySemester();
                    SystemView.displayEnrolments(visibleEnrolments);
                    break;
                case INVALID:
                    SystemView.displayInvalidInputMessage();
            }
        } while (command != Option.EXIT);
    }

    private static List<StudentEnrolment> searchEnrolmentsByStudentAndSemester() {
        String studentId = getInput("student id");
        String semester = getInput("semester");
        return system.getByStudentAndSemester(studentId, semester);
    }

    private static List<StudentEnrolment> searchEnrolmentsByCourseAndSemester() {
        String courseId = getInput("course id");
        String semester = getInput("semester");
        return system.getByCourseAndSemester(courseId, semester);
    }

    private static List<StudentEnrolment> searchEnrolmentsBySemester() {
        String semester = getInput("semester");
        return system.getBySemester(semester);
    }

    private static String getInput(String name) {
        System.out.print("Enter " + name + ": ");
        String input = sc.next();
        System.out.println(name + " received: " + input);
        sc.nextLine();
        return input;
    }

    private static Option getInputOption(int level) {
        try {
            System.out.print("Select an option by enter a corresponding integer: ");
            return Option.valueOf(level * 10 + sc.nextInt());
        } catch (InputMismatchException e) {
            System.out.println("Input is not valid integer");
        } finally {
            sc.nextLine();
        }
        return null;
    }
}
