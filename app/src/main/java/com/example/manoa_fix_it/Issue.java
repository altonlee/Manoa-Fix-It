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
    // Member variables represent title and details of issue
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "loc")
    private String loc;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "desc")
    private String desc;
    @ColumnInfo(name = "points")
    private int points;
    private final int imageResource;

    /**
     * Constructor for Issue card
     *
     * @param title Issue title.
     * @param info details of issue.
     */
    public Issue(String title, String loc, String status, String date, String desc, int points, int imageResource) {
        this.title = title;
        this.loc = loc;
        this.status = status;
        this.date = date;
        this.desc = desc;
        this.points = points;
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
     * Gets issue location.
     * @return issue details.
     */
    String getLoc() {
        return loc;
    }
    /**
     * Gets issue status.
     * @return issue details.
     */
    String getStatus() {
        return status;
    }
    /**
     * Gets issue date.
     * @return issue details.
     */
    String getDate() {
        return date;
    }
    /**
     * Gets issue details.
     * @return issue details.
     */
    String getDesc() {
        return desc;
    }
    /**
     * Gets issue details.
     * @return issue details.
     */
    int getPoints() {
        return points;
    }
    /**
     * Gets issue image header.
     * @return index of image.
     */
    public int getImageResource() {
        return imageResource;
    }
}
