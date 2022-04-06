import Models.StudentEnrolment;
import Services.StudentEnrolmentManagerImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class RmitEnrolmentSystemTests {

    static File testFile;
    static String filePath = "src/test.csv";

    @AfterAll
    public static void cleanUpTestFile() {
        testFile.delete();
    }

    @Test
    public void testViewAllEnrolments() {
        // Arrange
        String option = "1";
        String userInput = option + "\n" + "7\n" + filePath + "\n8\n";
        initilizeInput(userInput);

        // Act
        RmitEnrolmentSystem.main(null);

        // Assert
        List<StudentEnrolment> testStudentEnrolments = new StudentEnrolmentManagerImpl(filePath, ",").getAll();
        Assertions.assertEquals(15, testStudentEnrolments.size());
    }

    @Test
    public void testSearchEnrolmentsByStudentAndSemester() {
        // Arrange
        String option = "2";
        String studentId = "S101312\n";
        String semester = "2021A\n";
        String userInput = option + "\n" + studentId + semester + "7\n" + filePath + "\n8\n";
        initilizeInput(userInput);

        // Act
        RmitEnrolmentSystem.main(null);

        // Assert
        List<StudentEnrolment> testStudentEnrolments = new StudentEnrolmentManagerImpl(filePath, ",").getAll();
        Assertions.assertEquals(1, testStudentEnrolments.size());
    }

    @Test
    public void testSearchEnrolmentsByCourseAndSemester() {
        // Arrange
        String option = "3";
        String courseId = "COSC3321\n";
        String semester = "2021A\n";
        String userInput = option + "\n" + courseId + semester + "7\n" + filePath + "\n8\n";
        initilizeInput(userInput);

        // Act
        RmitEnrolmentSystem.main(null);

        // Assert
        List<StudentEnrolment> testStudentEnrolments = new StudentEnrolmentManagerImpl(filePath, ",").getAll();
        Assertions.assertEquals(4, testStudentEnrolments.size());
    }

    @Test
    public void testSearchEnrolmentsBySemester() {
        // Arrange
        String option = "4";
        String semester = "2021A\n";
        String userInput = option + "\n" + semester + "7\n" + filePath + "\n8\n";
        initilizeInput(userInput);

        // Act
        RmitEnrolmentSystem.main(null);

        // Assert
        List<StudentEnrolment> testStudentEnrolments = new StudentEnrolmentManagerImpl(filePath, ",").getAll();
        Assertions.assertEquals(7, testStudentEnrolments.size());
    }

    @Test
    public void testProcessEnrolmentAddAction() {
        // Arrange
        String option = "5";
        String studentId = "S101312\n";
        String courseId = "COSC3321\n";
        String semester = "2022A\n";
        String userInput = option + "\n" + studentId + courseId + semester + "7\n" + filePath + "\n8\n";
        initilizeInput(userInput);

        // Act
        RmitEnrolmentSystem.main(null);

        // Assert
        StudentEnrolment addedEnrolment = new StudentEnrolmentManagerImpl(filePath, ",").getOne(16);
        Assertions.assertAll("New enrolment should be added",
                () -> Assertions.assertEquals("Alex Mike", addedEnrolment.getStudent().getName()),
                () -> Assertions.assertEquals("Artificial Intelligence", addedEnrolment.getCourse().getName()),
                () -> Assertions.assertEquals("2022A", addedEnrolment.getSemester()));
    }

    @Test
    public void testProcessEnrolmentDeleteAction() {
        // Arrange
        String option = "6";
        int enrolmentId = 1;
        String userInput = option + "\n" + enrolmentId + "\n" + "7\n" + filePath + "\n8\n";
        initilizeInput(userInput);

        // Act
        RmitEnrolmentSystem.main(null);

        // Assert
        List<StudentEnrolment> testStudentEnrolments = new StudentEnrolmentManagerImpl(filePath, ",").getAll();
        Assertions.assertAll("The enrolment with selected id should be removed",
                () -> Assertions.assertEquals(RmitEnrolmentSystem.system.getAll().size(), testStudentEnrolments.size()),
                () -> Assertions.assertNull(RmitEnrolmentSystem.system.getOne(enrolmentId)));

    }

    @Test
    public void testExportToCsvFile() {
        // Arrange
        String option = "7";
        String userInput = option + "\n" + filePath + "\n8\n";
        initilizeInput(userInput);

        // Act
        RmitEnrolmentSystem.main(null);

        // Assert
        List<StudentEnrolment> testStudentEnrolments = new StudentEnrolmentManagerImpl(filePath, ",").getAll();
        Assertions.assertEquals(RmitEnrolmentSystem.system.getAll().size(), testStudentEnrolments.size());
    }

    private void initilizeInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        RmitEnrolmentSystem.sc = new Scanner(System.in);
        testFile = new File(filePath);
    }
}
