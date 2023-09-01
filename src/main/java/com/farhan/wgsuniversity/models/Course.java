package com.farhan.wgsuniversity.models;

public class Course {
    private final String id;
    private String majorId;
    private String name;
    private boolean isActive;

    public Course(String id, String majorId, String name) {
        this.id = id;
        this.majorId = majorId;
        this.name = name;
        this.isActive = true;
    }

    public String getId() {
        return this.id;
    }

    public String getMajorId() {
        return this.majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
