package com.example.severn.entity;

public class DiscoverDao {
    private String name;
    private String imageId;
    private String videoPath;
    public DiscoverDao() {
    }
    public DiscoverDao(String name, String imageId, String videoPath) {
        this.name = name;
        this.imageId = imageId;
        this.videoPath = videoPath;
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

    public String getVideoPath() {
        return videoPath;
    }
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
