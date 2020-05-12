package com.example.manoa_fix_it;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ComplaintViewModel extends AndroidViewModel {
    private ComplaintRepo mRepository;
    private LiveData<List<Complaint>> allComplaints;

    public ComplaintViewModel(Application application) {
        super(application);
        mRepository = new ComplaintRepo(application);
        allComplaints = mRepository.getAllComplaints();
    }

    LiveData<List<Complaint>> getAllComplaints() { return allComplaints; }

    public void insert(Complaint complaint) { mRepository.insert(complaint); }
    public void update(Complaint complaint) { mRepository.update(complaint); }
    public void delete(Complaint complaint) { mRepository.delete(complaint); }
}
