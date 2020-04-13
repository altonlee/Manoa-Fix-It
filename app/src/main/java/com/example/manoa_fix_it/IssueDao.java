package com.example.manoa_fix_it;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * This interface maps a Java method call to an SQL query
 */
@Dao
public interface IssueDao {
    // LiveData: data holder class that only holds latest version of data
    @Query("SELECT * from issue_table ORDER BY issue ASC")
    LiveData<List<Issue>> getAllIssues();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Issue issue);

    @Query("DELETE FROM issue_table")
    void deleteAll();
}
