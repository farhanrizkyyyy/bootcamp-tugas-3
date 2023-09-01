package com.farhan.wgsuniversity.controllers;

import com.farhan.wgsuniversity.models.ApiResponse;
import com.farhan.wgsuniversity.models.Student;
import com.farhan.wgsuniversity.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getStudents(@RequestParam(required = false) Boolean active, @RequestParam(required = false) String major_id) {
        if (active == null) active = true;

        List<Student> result;

        if (major_id != null) result = service.getStudentsByMajorId(major_id);
        else result = service.getStudents(active);

        ApiResponse response = new ApiResponse(service.getResponseMessage(), result);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable("id") String id) {
        HttpStatus httpStatus;
        Student student = service.getStudentById(id.trim());

        if (student != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), student);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addStudent(@RequestBody Student student) {
        HttpStatus httpStatus;
        Student newStudent = service.addStudent(student.getMajorId().trim(), student.getName().trim(), student.getYear());

        if (service.getResponseMessage().contains("success")) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.BAD_REQUEST;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), newStudent);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@PathVariable("id") String id, @RequestBody Student student) {
        HttpStatus httpStatus;
        Student updatedStudent = service.updateStudent(id.trim(), student.getMajorId().trim(), student.getName().trim(), student.getYear());

        if (updatedStudent != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), updatedStudent);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable("id") String id) {
        HttpStatus httpStatus;
        Student selectedStudent = service.getStudentById(id.trim());

        service.updateStudentStatus(id.trim(), true);

        if (selectedStudent != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), null);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/restore")
    public ResponseEntity<ApiResponse> restoreStudent(@RequestBody Student student) {
        HttpStatus httpStatus;
        Student selectedStudent = service.getStudentById(student.getId());

        service.updateStudentStatus(student.getId().trim(), false);

        if (selectedStudent != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), selectedStudent);
        return ResponseEntity.status(httpStatus).body(response);
    }
}
