package Services;

import Models.StudentEnrolment;

import java.util.List;

public interface StudentEnrolmentManager {
    void add(StudentEnrolment studentEnrolment);

    void update(StudentEnrolment studentEnrolment);

    void delete(StudentEnrolment studentEnrolment);

    StudentEnrolment getOne(int id);

    List<StudentEnrolment> getAll();

    List<StudentEnrolment> getByStudentAndSemester(String studentId, String semester);

    List<StudentEnrolment> getByCourseAndSemester(String courseId, String semester);

    List<StudentEnrolment> getBySemester(String semester);

    void load(String csvFilePath, String delimiter);

    static void print(List<StudentEnrolment> studentEnrolments) {
        for (StudentEnrolment studentEnrolment : studentEnrolments) {
            System.out.println(studentEnrolment);
        }
    }
}
