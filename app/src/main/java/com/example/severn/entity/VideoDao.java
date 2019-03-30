package com.example.severn.entity;


//视频选项的实体类
public class VideoDao {
    private String name;
    private int imageId;
    private  String author;
    private  String time;

    public VideoDao() {
    }

    public VideoDao(String name, int imageId, String author, String time) {
        this.name = name;
        this.imageId = imageId;
        this.author = author;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
