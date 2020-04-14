package com.example.android.materialme;

public class Issue {

    private String title;
    private String description;
    private String location;
    private String date;
    private final int imageResource;

    public Issue(String title, String description, String location, String date, int imageResource) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImageResource() {
        return this.imageResource;
    }
}
