package com.example.severn.entity;

public class PoemDao {
    private String poemItem;
    public String getPoemItem() {
        return poemItem;
    }
    public void setPoemItem(String poemItem) {
        this.poemItem = poemItem;
    }
    public PoemDao(String poemItem) {
        this.poemItem = poemItem;
    }
    public PoemDao() {
    }
}
