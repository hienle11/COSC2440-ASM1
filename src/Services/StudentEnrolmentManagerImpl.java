package Services;

import Models.Course;
import Models.Student;
import Models.StudentEnrolment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentEnrolmentManagerImpl implements StudentEnrolmentManager {

    List<StudentEnrolment> studentEnrolmentsList = new ArrayList<>();
    List<Student> students = new ArrayList<>();
    List<Course> courses = new ArrayList<>();

    private static int index;

    public StudentEnrolmentManagerImpl(String csvFilePath, String delimiter) {
        index = 1;
        load(csvFilePath, delimiter);
    }

    @Override
    public StudentEnrolment add(StudentEnrolment studentEnrolment) {
        studentEnrolment.setId(index++);
        studentEnrolmentsList.add(studentEnrolment);
        return studentEnrolment;
    }

    @Override
    public void delete(StudentEnrolment studentEnrolment) {
        studentEnrolmentsList.remove(studentEnrolment);
    }

    @Override
    public StudentEnrolment getOne(int id) {
        for (StudentEnrolment studentEnrolment : studentEnrolmentsList) {
            if (id == studentEnrolment.getId()) {
                return studentEnrolment;
            }
        }
        return null;
    }

    @Override
    public List<StudentEnrolment> getAll() {
        return studentEnrolmentsList;
    }

    @Override
    public List<StudentEnrolment> getByStudentAndSemester(String studentId, String semester) {
        List<StudentEnrolment> results = new ArrayList<>();
        for (StudentEnrolment studentEnrolment : studentEnrolmentsList) {
            if (studentId.equals(studentEnrolment.getStudent().getId()) && semester.equals(studentEnrolment.getSemester())) {
                results.add(studentEnrolment);
            }
        }
        return results;
    }

    @Override
    public List<StudentEnrolment> getByCourseAndSemester(String courseId, String semester) {
        List<StudentEnrolment> results = new ArrayList<>();
        for (StudentEnrolment studentEnrolment : studentEnrolmentsList) {
            if (courseId.equals(studentEnrolment.getCourse().getId()) && semester.equals(studentEnrolment.getSemester())) {
                results.add(studentEnrolment);
            }
        }
        return results;
    }

    @Override
    public List<StudentEnrolment> getBySemester(String semester) {
        List<StudentEnrolment> results = new ArrayList<>();
        for (StudentEnrolment studentEnrolment : studentEnrolmentsList) {
            if (semester.equals(studentEnrolment.getSemester())) {
                results.add(studentEnrolment);
            }
        }
        return results;
    }

    @Override
    public Student getStudentById(String studentId) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(studentId))
                return student;
        }
        return null;
    }

    @Override
    public Course getCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getId().equalsIgnoreCase(courseId)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        return this.students;
    }

    @Override
    public List<Course> getAllCourses() {
        return this.courses;
    }

    private void load(String csvFilePath, String delimiter) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] tokens = line.split(delimiter);
                Student student = new Student(tokens[0], tokens[1], tokens[2]);
                Course course = new Course(tokens[3], tokens[4], Integer.parseInt(tokens[5]));
                StudentEnrolment studentEnrolment = new StudentEnrolment(student, course, tokens[6]);
                if (this.getStudentById(student.getId()) == null) {
                    this.students.add(student);
                }
                if (this.getCourseById(course.getId()) == null) {
                    this.courses.add(course);
                }
                add(studentEnrolment);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot load default file to populate data");
        }
    }
}
