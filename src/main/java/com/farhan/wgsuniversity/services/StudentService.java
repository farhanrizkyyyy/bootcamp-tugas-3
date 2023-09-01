package com.farhan.wgsuniversity.services;

import com.farhan.wgsuniversity.models.Student;
import com.farhan.wgsuniversity.utilities.InputValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private static List<Student> students = new ArrayList<>();
    private static String responseMessage = "";

    public String getResponseMessage() {
        return responseMessage;
    }

    public String generateId(short year, String majorId) {
        List<Student> studentsByMajorId = getStudentsByMajorId(majorId.trim());
        String yearForId = String.valueOf(year).substring(2);
        String studentId;

        if (studentsByMajorId.isEmpty()) studentId = "001";
        else {
            int lastStudentId = Integer.parseInt(studentsByMajorId.get(studentsByMajorId.size() - 1).getId().substring(5));
            lastStudentId++;
            studentId = String.format("%03d", lastStudentId);
        }

        return yearForId + majorId + studentId;
    }

    public List<Student> getStudents(boolean active) {
        seedStudents();

        responseMessage = "";
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (active) {
                if (student.isActive()) result.add(student);
            } else {
                if (!student.isActive()) result.add(student);
            }
        }

        if (result.isEmpty()) responseMessage = "Students empty";

        return result;
    }

    public Student getStudentById(String id) {
        seedStudents();

        responseMessage = "";
        Student result = null;

        for (Student student : students) {
            if (student.getId().equals(id.trim())) {
                result = student;
                break;
            }
        }

        if (result == null) responseMessage = "Cannot find student with ID " + id.trim();

        return result;
    }

    public List<Student> getStudentsByMajorId(String majorId) {
        seedStudents();

        responseMessage = "";
        List<Student> result = new ArrayList<>();

        for (Student student : students) {
            if (student.isActive() && student.getMajorId().equals(majorId.trim())) result.add(student);
        }

        if (result.isEmpty()) responseMessage = "There is no student on major " + majorId.trim();

        return result;
    }

    public Student addStudent(String majorId, String name, short year) {
        seedStudents();

        responseMessage = "";

        if (InputValidation.validateThreeDigitsId(majorId.trim())) {
            if (InputValidation.validateName(name.trim())) {
                if (InputValidation.validateYear(String.valueOf(year))) {
                    String id = generateId(year, majorId);
                    Student newStudent = new Student(id, majorId, name, year);
                    students.add(newStudent);
                    responseMessage = newStudent.getId() + " - " + newStudent.getName() + " successfully added";

                    return newStudent;
                } else responseMessage = "Year must be 4 digit number";
            } else responseMessage = "Student's name must contain alphanumeric only";
        } else responseMessage = "Major ID must be 3 digit number";

        return null;
    }

    public Student updateStudent(String id, String newMajorId, String newName, short newYear) {
        seedStudents();

        responseMessage = "";
        Student selectedStudent = getStudentById(id.trim());

        if (selectedStudent != null) {
            if (InputValidation.validateName(newName.trim())) {
                if (InputValidation.validateYear(String.valueOf(newYear))) {
                    selectedStudent.setMajorId(newMajorId.trim());
                    selectedStudent.setName(newName.trim());
                    selectedStudent.setYear(newYear);
                    responseMessage = "Data on student with ID " + selectedStudent.getId() + " successfully updated";
                } else responseMessage = "Year must be 4 digit number";
            } else responseMessage = "Student's name must contain alphanumeric only";
        }

        return selectedStudent;
    }

    public void updateStudentStatus(String id, boolean isDelete) {
        seedStudents();

        responseMessage = "";
        Student selectedStudent = getStudentById(id.trim());

        if (selectedStudent != null) {
            if (isDelete) {
                selectedStudent.setActive(false);
                responseMessage = selectedStudent.getName() + " successfully deleted";
            } else {
                selectedStudent.setActive(true);
                responseMessage = selectedStudent.getName() + " successfully restored";
            }
        }
    }

    private void seedStudents() {
        if (students.isEmpty()) {
            students.add(new Student("23001001", "001", "Farhan 1", (short) 2023));
            students.add(new Student("23001002", "001", "Farhan 2", (short) 2023));
            students.add(new Student("19002001", "002", "Farhan 3", (short) 2019));
            students.add(new Student("18004001", "004", "Farhan 4", (short) 2018));
            students.add(new Student("20003001", "003", "Farhan 5", (short) 2020));
        }
    }
}
