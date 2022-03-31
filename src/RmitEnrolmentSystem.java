import Services.StudentEnrolmentManager;
import Services.StudentEnrolmentManagerImpl;

public class RmitEnrolmentSystem {

    public static void main(String[] args) {
        StudentEnrolmentManager system = new StudentEnrolmentManagerImpl("src/default.csv", ",");

    }



}
