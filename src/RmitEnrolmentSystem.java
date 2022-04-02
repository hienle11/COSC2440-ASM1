import Enums.Option;
import Models.Course;
import Models.Student;
import Models.StudentEnrolment;
import Services.StudentEnrolmentManager;
import Services.StudentEnrolmentManagerImpl;
import Views.SystemView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        visibleEnrolments = system.getAll();
        SystemView.displayEnrolments(visibleEnrolments);
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
                case ENROLMENTS_ADD:
                    processEnrolmentAddAction();
                    break;
                case ENROLMENTS_DELETE:
                    processEnrolmentDeleteAction();
                    break;
                case ENROLMENTS_EXPORT:
                    exportToCsvFile();
                    break;
                case INVALID:
                    SystemView.displayInvalidInputMessage();
                    break;
            }
        } while (command != Option.EXIT);
    }

    private static void exportToCsvFile() {
        String filePath = getInput("Enter exported path");
        File file = new File(filePath);
        try {
            FileWriter writer = new FileWriter(file);
            for (StudentEnrolment enrolment : visibleEnrolments) {
                String[] line = {enrolment.getStudent().getId(), enrolment.getStudent().getName(), enrolment.getStudent().getBirthdate(),
                        enrolment.getCourse().getId(), enrolment.getCourse().getName(), String.valueOf(enrolment.getCourse().getCredits()),
                        enrolment.getSemester()};
                writer.append(String.join(",", line));
                writer.append("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error! Could not export to CSV File!");
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static void processEnrolmentDeleteAction() {
        StudentEnrolment selectedEnrolment = null;
        do {
            int enrolmentId = 0;
            try {
                enrolmentId = Integer.parseInt(getInput("enrolment id"));
            } catch (NumberFormatException e) {
                System.out.println("Id of enrolment is a number!");
                continue;
            }
            selectedEnrolment = system.getOne(enrolmentId);
            if (selectedEnrolment == null) {
                System.out.println("---------------------------------------------");
                System.out.println("There is no matching enrolments");
                SystemView.displayEnrolments(system.getAll());
            }
        } while (selectedEnrolment == null);
        system.delete(selectedEnrolment);
        System.out.println("Deleted: " + selectedEnrolment);
    }

    private static void processEnrolmentAddAction() {
        Student selectedStudent;
        do {
            String studentId = getInput("student id");
            selectedStudent = system.getStudentById(studentId);
            if (selectedStudent == null) {
                SystemView.displayStudentsWhenReceivingInvalidInput(system.getAllStudents());
            }
        } while (selectedStudent == null);

        Course selectedCourse;
        do {
            String courseId = getInput("course id");
            selectedCourse = system.getCourseById(courseId);
            if (selectedCourse == null) {
                SystemView.displayCoursesWhenReceivingInvalidInput(system.getAllCourses());
            }
        } while (selectedCourse == null);

        String semester;
        do {
            semester = getInput("semester");
            if (semester.length() < 5) {
                System.out.println("Invalid semester format!");
                continue;
            }
            try {
                int year = Integer.parseInt(semester.substring(0, 4));
                if (year < 2022) {
                    System.out.println("Year must be equal or greater than 2022!");
                    continue;
                }
                char semCode = semester.charAt(4);
                if (semCode == 'A' || semCode == 'B' || semCode == 'C') {
                    break;
                } else {
                    System.out.println("Semester must be either 'A', 'B' or 'C'!");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid semester format!");
            }
        } while (true);
        StudentEnrolment newStudentEnrolment = new StudentEnrolment(selectedStudent, selectedCourse, semester);
        System.out.println("----------------------------------------");
        System.out.println("Added: " + system.add(newStudentEnrolment));
        System.out.println("----------------------------------------");
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
