package com.farhan.wgsuniversity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Student {
    private final String id;
    private String majorId;
    private String name;
    private short year;
    private boolean isActive;

    public Student(String id, String majorId, String name, short year) {
        this.id = id;
        this.majorId = majorId;
        this.name = name;
        this.year = year;
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

    public short getYear() {
        return this.year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    @JsonIgnore
    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
