package com.example.severn.entity;

public class DiscoverDao {
    private String name;
    private String imageId;
    public DiscoverDao() {
    }
    public DiscoverDao(String name, String imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
