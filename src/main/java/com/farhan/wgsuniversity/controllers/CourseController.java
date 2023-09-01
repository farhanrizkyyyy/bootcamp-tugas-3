package com.farhan.wgsuniversity.controllers;

import com.farhan.wgsuniversity.models.ApiResponse;
import com.farhan.wgsuniversity.models.Course;
import com.farhan.wgsuniversity.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService service;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getCourses(@RequestParam(required = false) Boolean active, @RequestParam(required = false) String major_id) {
        if (active == null) active = true;

        List<Course> result;

        if (major_id != null) result = service.getCoursesByMajorId(major_id);
        else result = service.getCourses(active);

        ApiResponse response = new ApiResponse(service.getResponseMessage(), result);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable("id") String id) {
        HttpStatus httpStatus;
        Course course = service.getCourseById(id.trim());

        if (course != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), course);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addCourse(@RequestBody Course course) {
        HttpStatus httpStatus;
        Course newCourse = service.addCourse(course.getMajorId().trim(), course.getName().trim());

        if (service.getResponseMessage().contains("success")) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.BAD_REQUEST;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), newCourse);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable("id") String id, @RequestBody Course course) {
        HttpStatus httpStatus;
        Course updatedCourse = service.updateCourse(id.trim(), course.getMajorId().trim(), course.getName().trim());

        if (updatedCourse != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), updatedCourse);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable("id") String id) {
        HttpStatus httpStatus;
        Course selectedCourse = service.getCourseById(id.trim());

        service.updateCourseStatus(id.trim(), true);

        if (selectedCourse != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), null);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/restore")
    public ResponseEntity<ApiResponse> restoreStudent(@RequestBody Course course) {
        HttpStatus httpStatus;
        Course selectedCourse = service.getCourseById(course.getId());

        service.updateCourseStatus(course.getId().trim(), false);

        if (selectedCourse != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), selectedCourse);
        return ResponseEntity.status(httpStatus).body(response);
    }
}
