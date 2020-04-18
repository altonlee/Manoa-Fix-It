package com.example.manoa_fix_it;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * ViewModel holds the app's UI data, separating it from Activity classes.
 * Solely responsible for holding and processing all data required by UI.
 */
public class IssueViewModel extends AndroidViewModel {
    private IssueRepo mRepository;
    private LiveData<List<Issue>> allIssues;

    public IssueViewModel(Application application) {
        super(application);
        mRepository = new IssueRepo(application);
        allIssues = mRepository.getAllIssues();
    }

    LiveData<List<Issue>> getAllIssues() { return allIssues; }

    public void insert(Issue issue) {mRepository.insert(issue); }
    public void delete(Issue issue) {mRepository.deleteIssue(issue); }
    public void sortBy(String sortMethod) { mRepository.sortBy(sortMethod); }
}
