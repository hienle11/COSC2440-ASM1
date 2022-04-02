package Services;

import Models.Course;
import Models.Student;
import Models.StudentEnrolment;

import java.util.List;

public interface StudentEnrolmentManager {
    StudentEnrolment add(StudentEnrolment studentEnrolment);

    void delete(StudentEnrolment studentEnrolment);

    StudentEnrolment getOne(int id);

    List<StudentEnrolment> getAll();

    List<StudentEnrolment> getByStudentAndSemester(String studentId, String semester);

    List<StudentEnrolment> getByCourseAndSemester(String courseId, String semester);

    List<StudentEnrolment> getBySemester(String semester);

    void load(String csvFilePath, String delimiter);

    Student getStudentById(String studentId);

    Course getCourseById(String courseId);

    List<Student> getAllStudents();

    List<Course> getAllCourses();
}
