package com.farhan.wgsuniversity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Major {
    private final String id;
    private String name;
    private boolean isActive;

    public Major(String id, String name) {
        this.id = id;
        this.name = name;
        this.isActive = true;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
