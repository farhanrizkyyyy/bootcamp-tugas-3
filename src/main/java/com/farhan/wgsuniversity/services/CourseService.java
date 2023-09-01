package com.farhan.wgsuniversity.services;

import com.farhan.wgsuniversity.models.Course;
import com.farhan.wgsuniversity.utilities.InputValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private static List<Course> courses = new ArrayList<>();
    private static String responseMessage = "";

    public String getResponseMessage() {
        return responseMessage;
    }

    public String generateId(String majorId) {
        List<Course> coursesByMajorId = getCoursesByMajorId(majorId.trim());
        String courseId;

        if (coursesByMajorId.isEmpty()) courseId = "001";
        else {
            int lastCourseId = Integer.parseInt(coursesByMajorId.get(coursesByMajorId.size() - 1).getId().substring(3));
            lastCourseId++;
            courseId = String.format("%03d", lastCourseId);
        }

        return majorId + courseId;
    }

    public List<Course> getCourses(boolean active) {
        seedCourses();

        responseMessage = "";
        List<Course> result = new ArrayList<>();

        for (Course course : courses) {
            if (active) {
                if (course.isActive()) result.add(course);
            } else {
                if (!course.isActive()) result.add(course);
            }
        }

        if (result.isEmpty()) responseMessage = "Courses empty";

        return result;
    }

    public Course getCourseById(String id) {
        seedCourses();

        responseMessage = "";
        Course result = null;

        for (Course course : courses) {
            if (course.getId().equals(id.trim())) {
                result = course;
                break;
            }
        }

        if (result == null) responseMessage = "Cannot find course with ID " + id.trim();

        return result;
    }

    public List<Course> getCoursesByMajorId(String majorId) {
        seedCourses();

        responseMessage = "";
        List<Course> result = new ArrayList<>();

        for (Course course : courses) {
            if (course.isActive() && course.getMajorId().equals(majorId.trim())) result.add(course);
        }

        if (result.isEmpty()) responseMessage = "There is no courses on major " + majorId.trim();

        return result;
    }

    public Course addCourse(String majorId, String name) {
        seedCourses();

        responseMessage = "";

        if (InputValidation.validateThreeDigitsId(majorId.trim())) {
            if (InputValidation.validateName(name.trim())) {
                String id = generateId(majorId.trim());
                Course newCourse = new Course(id, majorId.trim(), name.trim());
                courses.add(newCourse);
                responseMessage = newCourse.getId() + " - " + newCourse.getName() + " successfully added";

                return newCourse;
            } else responseMessage = "Course's name must contain alphanumeric only";
        } else responseMessage = "Major ID must be 3 digit number";

        return null;
    }

    public Course updateCourse(String id, String newMajorId, String newName) {
        seedCourses();

        responseMessage = "";
        Course selectedCourse = getCourseById(id.trim());

        if (selectedCourse != null) {
            if (InputValidation.validateThreeDigitsId(newMajorId.trim())) {
                if (InputValidation.validateName(newName.trim())) {
                    selectedCourse.setMajorId(newMajorId.trim());
                    selectedCourse.setName(newName.trim());
                    responseMessage = "Data on course with ID " + selectedCourse.getId() + " successfully updated";
                } else responseMessage = "Course's name must contain alphanumeric only";
            } else responseMessage = "Major ID must be 3 digit number";
        }

        return selectedCourse;
    }

    public void updateCourseStatus(String id, boolean isDelete) {
        seedCourses();

        responseMessage = "";
        Course selectedCourse = getCourseById(id.trim());

        if (selectedCourse != null) {
            if (isDelete) {
                selectedCourse.setActive(false);
                responseMessage = selectedCourse.getName() + " successfully deleted";
            } else {
                selectedCourse.setActive(true);
                responseMessage = selectedCourse.getName() + " successfully restored";
            }
        }
    }

    private void seedCourses() {
        if (courses.isEmpty()) {
            courses.add(new Course("001001", "001", "Database"));
            courses.add(new Course("001002", "001", "Java"));
            courses.add(new Course("002001", "002", "Force"));
            courses.add(new Course("002002", "002", "Momentum"));
            courses.add(new Course("003001", "003", "Algebra"));
            courses.add(new Course("004001", "004", "Matrix"));
        }
    }
}
