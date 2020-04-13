package com.example.manoa_fix_it;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "issue_table")
public class Issue {
    // Annotations
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "issue")
    // Member variables represent title and details of issue
    private String title;
    private String status;
    private String date;
    private String info;
    private final int imageResource;

    /**
     * Constructor for Issue card
     *
     * @param title Issue title.
     * @param info details of issue.
     */
    public Issue(String title, String status, String date, String info, int imageResource) {
        this.title = title;
        this.status = status;
        this.date = date;
        this.info = info;
        this.imageResource = imageResource;
    }
    /**
     * Gets title of issue.
     * @return issue title.
     */
    String getTitle() {
        return title;
    }
    /**
     * Gets issue details.
     * @return issue details.
     */
    String getStatus() {
        return status;
    }
    /**
     * Gets issue details.
     * @return issue details.
     */
    String getDate() {
        return date;
    }
    /**
     * Gets issue details.
     * @return issue details.
     */
    String getInfo() {
        return info;
    }
    /**
     * Gets issue image header.
     * @return index of image.
     */
    public int getImageResource() {
        return imageResource;
    }
}
