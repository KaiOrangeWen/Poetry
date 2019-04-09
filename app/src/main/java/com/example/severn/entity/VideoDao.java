package com.example.severn.entity;


import android.net.Uri;

//视频选项的实体类
public class VideoDao {
    private String name;
    private String imageId;
    private  String author;


    public VideoDao() {
    }

    public VideoDao(String name, String author,String imageId) {
        this.name = name;
        this.imageId = imageId;
        this.author = author;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
