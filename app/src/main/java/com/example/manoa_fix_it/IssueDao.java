package com.example.manoa_fix_it;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * This interface maps a Java method call to an SQL query
 */
@Dao
public interface IssueDao {
    // LiveData: data holder class that only holds latest version of data
    @Query("SELECT * from issue_table ORDER BY date(date) DESC")
    LiveData<List<Issue>> getAllIssues();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Issue issue);

    @Update
    void update(Issue... issue);

    @Delete
    void delete(Issue issue);

    @Query("DELETE FROM issue_table")
    void deleteAll();

    @Query("SELECT * from issue_table ORDER BY title ASC")
    LiveData<List<Issue>> sortByTitleAsc();

    @Query("SELECT * from issue_table ORDER BY title DESC")
    LiveData<List<Issue>> sortByTitleDesc();

    @Query("SELECT * from issue_table ORDER BY date(date) ASC")
    LiveData<List<Issue>> sortByDateAsc();

    @Query("SELECT * from issue_table ORDER BY date(date) DESC")
    LiveData<List<Issue>> sortByDateDesc();

    @Query("SELECT * from issue_table ORDER BY points DESC")
    LiveData<List<Issue>> sortByPoints();
}
