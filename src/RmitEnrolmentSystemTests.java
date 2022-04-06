import Services.StudentEnrolmentManager;
import Services.StudentEnrolmentManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Scanner;

public class RmitEnrolmentSystemTests {

    static StudentEnrolmentManager system;

    @BeforeAll
    public static void initializeTestData() {
        system = new StudentEnrolmentManagerImpl("src/default.csv", ",");
    }

    @Test
    public void testExportToCsvFile() {
        // Arrange
        String filePath = "src/test.csv";
        RmitEnrolmentSystem.visibleEnrolments = system.getAll();
        ByteArrayInputStream in = new ByteArrayInputStream((filePath + "\n").getBytes());
        System.setIn(in);
        RmitEnrolmentSystem.sc = new Scanner(System.in);
        File file = new File(filePath);

        // Act
        RmitEnrolmentSystem.exportToCsvFile();

        // Assert
        Assertions.assertTrue(file.exists() && !file.isDirectory());
    }

}
