package com.example.manoa_fix_it;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ComplaintRepo {
    private ComplaintDao complaintDao;
    private LiveData<List<Complaint>> allComplaints;

    ComplaintRepo(Application application) {
        ComplRoomDatabase db = ComplRoomDatabase.getDatabase(application);
        complaintDao = db.complaintDao();
        allComplaints = complaintDao.getAllComplaints();
    }
    LiveData<List<Complaint>> getAllComplaints() { return allComplaints; }

    public void insert(Complaint complaint) { new insertAsyncTask(complaintDao).execute(complaint); }
    private static class insertAsyncTask extends AsyncTask<Complaint, Void, Void> {
        private ComplaintDao mAsyncTaskDao;

        insertAsyncTask(ComplaintDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Complaint... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
