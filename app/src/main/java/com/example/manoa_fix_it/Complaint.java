package com.example.manoa_fix_it;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "complaint_table")
public class Complaint {

    @PrimaryKey(autoGenerate = true)
    private int postId;
    @ColumnInfo(name = "userId")
    private int userId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "loc")
    private String loc;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "desc")
    private String desc;
    @ColumnInfo(name = "points")
    private int points;

    public Complaint(int userId, String title, String loc, String date, String desc, int points) {
        this.userId = userId;
        this.title = title;
        this.loc = loc;
        this.date = date;
        this.desc = desc;
        this.points = points;
        //this.discussion = discussion;
    }
    @Ignore
    public Complaint(int postId, int userId, String title, String loc, String date, String desc, int points) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.loc = loc;
        this.date = date;
        this.desc = desc;
        this.points = points;
        //this.discussion = discussion;
    }

    /**
     * Gets user ID.
     * @return user ID.
     */
    int getPostId() {
        return postId;
    }
    /**
     * Gets user ID.
     * @return user ID.
     */
    void setPostId(int postId) {
        this.postId = postId;
    }
    /**
     * Gets user ID.
     * @return user ID.
     */
    int getUserId() {
        return userId;
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
     * Gets discussion.
     * @return discussion.
     */
    //Object[] getDiscussion() { return discussion; }
}
