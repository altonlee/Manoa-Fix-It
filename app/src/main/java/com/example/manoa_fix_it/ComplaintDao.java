package com.example.manoa_fix_it;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ComplaintDao {
    // LiveData: data holder class that only holds latest version of data
    @Query("SELECT * from complaint_table ORDER BY date(date) DESC")
    LiveData<List<Complaint>> getAllComplaints();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Complaint compl);

    @Update
    void update(Complaint... compl);

    @Delete
    void delete(Complaint compl);

    @Query("DELETE FROM complaint_table")
    void deleteAll();
}
