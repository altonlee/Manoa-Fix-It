package com.example.manoa_fix_it;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * This class manages query threads and implements the logic to either
 * fetch data from a network, or use results cached from local database.
 */
public class IssueRepo {
    private IssueDao issueDao;
    private LiveData<List<Issue>> allIssues;

    IssueRepo(Application application) {
        IssueRoomDatabase db = IssueRoomDatabase.getDatabase(application);
        issueDao = db.issueDao();
        allIssues = issueDao.getAllIssues();
    }
    LiveData<List<Issue>> getAllIssues() { return allIssues; }

    public void insert(Issue issue) {
        new insertAsyncTask(issueDao).execute(issue);
    }
    private static class insertAsyncTask extends AsyncTask<Issue, Void, Void> {
        private IssueDao mAsyncTaskDao;

        insertAsyncTask(IssueDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected  Void doInBackground(final Issue... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
