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

    /**
     * Inserts a new Issue by calling an AsyncTask and
     * inserting into the Dao in the background
     * @param issue: issue to add
     */
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

    /**
     * Updates an issue by calling an AsyncTask and
     * updating it from the Dao in the background
     * @param issue: the issue to update
     */
    public void update(Issue issue) {
        new updateAsyncTask(issueDao).execute(issue);
    }
    private static class updateAsyncTask extends AsyncTask<Issue, Void, Void> {
        private IssueDao mAsyncTaskDao;

        updateAsyncTask(IssueDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected  Void doInBackground(final Issue... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    /**
     * Deletes an issue by calling an AsyncTask and
     * deleting it from the Dao in the background
     * @param issue: the issue to delete
     */
    public void delete (Issue issue) { new deleteAsyncTask(issueDao).execute(issue); }
    private static class deleteAsyncTask extends AsyncTask<Issue, Void, Void> {
        private IssueDao mAsyncTaskDao;

        deleteAsyncTask(IssueDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected  Void doInBackground(final Issue... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    /**
     * Sorts all issues by calling an AsyncTask and
     * sorting it in the Dao by the method provided in the background
     * @param sort: method to sort by
     */
    public void sortBy(String sort) { new sortAsyncTask(issueDao, sort).execute(); }
    private static class sortAsyncTask extends AsyncTask<Void, Void, Void> {
        private IssueDao mAsyncTaskDao;
        private String sortMethod;

        sortAsyncTask(IssueDao dao, String sort) {
            mAsyncTaskDao = dao;
            sortMethod = sort;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            switch (sortMethod) {
                case "DateAsc":
                    break;
                default:
                    // unknown option
            }
            return null;
        }
    }
}
