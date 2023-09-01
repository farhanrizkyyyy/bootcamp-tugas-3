package com.farhan.wgsuniversity.controllers;

import com.farhan.wgsuniversity.models.ApiResponse;
import com.farhan.wgsuniversity.models.Major;
import com.farhan.wgsuniversity.services.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/majors")
public class MajorController {
    @Autowired
    private MajorService service;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getMajors(@RequestParam(required = false) Boolean active) {
        if (active == null) active = true;

        List<Major> result = service.getMajors(active);
        ApiResponse response = new ApiResponse(service.getResponseMessage(), result);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getMajorsById(@PathVariable("id") String id) {
        HttpStatus httpStatus;
        Major major = service.getMajorById(id);

        if (major != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), major);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addMajor(@RequestBody Major major) {
        HttpStatus httpStatus;
        Major newMajor = service.addMajor(major.getName().trim());

        if (service.getResponseMessage().contains("success")) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.BAD_REQUEST;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), newMajor);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMajor(@PathVariable("id") String id, @RequestBody Major major) {
        HttpStatus httpStatus;
        Major updatedMajor = service.updateMajor(id.trim(), major.getName().trim());

        if (updatedMajor != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), updatedMajor);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMajor(@PathVariable("id") String id) {
        HttpStatus httpStatus;
        Major selectedMajor = service.getMajorById(id.trim());

        service.updateMajorStatus(id.trim(), true);

        if (selectedMajor != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), null);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PutMapping("/restore")
    public ResponseEntity<ApiResponse> restoreMajor(@RequestBody Major major) {
        HttpStatus httpStatus;
        Major selectedMajor = service.getMajorById(major.getId());

        service.updateMajorStatus(major.getId().trim(), false);

        if (selectedMajor != null) httpStatus = HttpStatus.OK;
        else httpStatus = HttpStatus.NOT_FOUND;

        ApiResponse response = new ApiResponse(service.getResponseMessage(), selectedMajor);
        return ResponseEntity.status(httpStatus).body(response);
    }
}
