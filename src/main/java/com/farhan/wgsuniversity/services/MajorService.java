package com.farhan.wgsuniversity.services;

import com.farhan.wgsuniversity.models.Major;
import com.farhan.wgsuniversity.utilities.InputValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MajorService {
    private static List<Major> majors = new ArrayList<>();
    private static String responseMessage = "";

    public String getResponseMessage() {
        return responseMessage;
    }

    public String generateId() {
        if (majors.isEmpty()) return "001";
        else {
            int lastMajorId = Integer.parseInt(majors.get(majors.size() - 1).getId());
            lastMajorId++;

            return String.format("%03d", lastMajorId);
        }
    }

    public List<Major> getMajors(boolean active) {
        seedMajors();

        responseMessage = "";
        List<Major> result = new ArrayList<>();

        for (Major major : majors) {
            if (active) {
                if (major.isActive()) result.add(major);
            } else {
                if (!major.isActive()) result.add(major);
            }
        }

        if (result.isEmpty()) responseMessage = "Majors empty";

        return result;
    }

    public Major getMajorById(String id) {
        seedMajors();

        responseMessage = "";
        Major result = null;

        for (Major major : majors) {
            if (major.getId().equals(id.trim())) {
                result = major;
                break;
            }
        }

        if (result == null) responseMessage = "Cannot find major with ID " + id.trim();

        return result;
    }

    public Major addMajor(String name) {
        seedMajors();

        responseMessage = "";

        if (InputValidation.validateName(name.trim())) {
            Major newMajor = new Major(generateId(), name.trim());
            majors.add(newMajor);
            responseMessage = name.trim() + " successfully added";

            return newMajor;
        } else {
            responseMessage = "Major's name must contain alphanumeric only";

            return null;
        }
    }

    public Major updateMajor(String id, String newName) {
        seedMajors();

        responseMessage = "";
        Major selectedMajor = getMajorById(id.trim());

        if (selectedMajor != null) {
            if (InputValidation.validateName(newName.trim())) {
                String oldMajorName = selectedMajor.getName();
                selectedMajor.setName(newName.trim());
                responseMessage = oldMajorName + " successfully renamed to " + selectedMajor.getName();

                return selectedMajor;
            } else responseMessage = "Major name must contain alphanumeric only";
        }

        return null;
    }

    public void updateMajorStatus(String id, boolean isDelete) {
        seedMajors();

        responseMessage = "";
        Major selectedMajor = getMajorById(id.trim());

        if (selectedMajor != null) {
            if (isDelete) {
                selectedMajor.setActive(false);
                responseMessage = selectedMajor.getName() + " successfully deleted";
            } else {
                selectedMajor.setActive(true);
                responseMessage = selectedMajor.getName() + " successfully restored";
            }
        }
    }

    private void seedMajors() {
        if (majors.isEmpty()) {
            majors.add(new Major("001", "Informatics"));
            majors.add(new Major("002", "Physics"));
            majors.add(new Major("003", "Mathematics"));
            majors.add(new Major("004", "Electrical"));
            majors.add(new Major("005", "Law"));
            majors.add(new Major("006", "Economy"));
            majors.add(new Major("007", "Business"));
            majors.add(new Major("008", "Administration"));
            majors.add(new Major("009", "Sociology"));
            majors.add(new Major("010", "Biology"));
        }
    }
}
