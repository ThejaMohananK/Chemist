package com.example.chemist;

public class Img {
    private String images;


    public Img() {}

    public Img(String images) {
        this.images = images;


    }

    // Getters and setters for the properties
    public String getImages() {
        return images;
    }
    public void setImage(String images) {
        this.images = images;
    }


    @Override
    public String toString() {

        return images + ":";
    }
}
