package Views;

import Models.Course;
import Models.Student;
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
            System.out.println("---------------------------------------------");
            return;
        }
        for (StudentEnrolment studentEnrolment : studentEnrolments) {
            System.out.println(studentEnrolment);
        }
        System.out.println("---------------------------------------------");
    }

    public static void displayCoursesWhenReceivingInvalidInput(List<Course> courses) {
        System.out.println("---------------------------------------------");
        System.out.println("-----------Cannot find requested course------");
        System.out.println("---------------Available courses-------------");
        for (Course course: courses) {
            System.out.println(course);
        }
        System.out.println("---------------------------------------------");
    }

    public static void displayStudentsWhenReceivingInvalidInput(List<Student> students) {
        System.out.println("---------------------------------------------");
        System.out.println("---------Cannot find requested student-------");
        System.out.println("---------------Available students------------");
        for (Student student: students) {
            System.out.println(student);
        }
        System.out.println("---------------------------------------------");
    }
}
