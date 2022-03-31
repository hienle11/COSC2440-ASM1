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
    static int index = 0;

    public StudentEnrolmentManagerImpl(String csvFilePath, String delimiter) {
        load(csvFilePath, delimiter);
    }

    @Override
    public void add(StudentEnrolment studentEnrolment) {
        studentEnrolment.setId(index++);
        studentEnrolmentsList.add(studentEnrolment);
    }

    @Override
    public void update(StudentEnrolment updatedStudentEnrolment) {
        StudentEnrolment studentEnrolment = getOne(updatedStudentEnrolment.getId());
        studentEnrolmentsList.remove(studentEnrolment);
        studentEnrolmentsList.add(studentEnrolment);
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
    public void load(String csvFilePath, String delimiter) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] tokens = line.split(delimiter);
                Student student = new Student(tokens[0], tokens[1], tokens[2]);
                Course course = new Course(tokens[3], tokens[4], Integer.parseInt(tokens[5]));
                StudentEnrolment studentEnrolment = new StudentEnrolment(student, course, tokens[6]);
                add(studentEnrolment);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot load default file to populate data");
        }
    }
}
