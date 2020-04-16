package com.example.manoa_fix_it;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

@Database(entities = {Issue.class}, version = 1, exportSchema = false)
public abstract class IssueRoomDatabase extends RoomDatabase {
    public abstract IssueDao issueDao();
    private static Context cont;
    private static IssueRoomDatabase INSTANCE;

    static IssueRoomDatabase getDatabase(final Context context) {
        cont = context;
        if (INSTANCE == null) {
            synchronized (IssueRoomDatabase.class) {
                if (INSTANCE == null) {
                    // creates database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IssueRoomDatabase.class, "issue_database")
                            // used to wipe and rebuild instead of migrating
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populates the database in the background. Starts the app
     * with a clean database every time the app is opened.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final IssueDao dao;
        String[] titles = cont.getResources().getStringArray(R.array.issue_titles);
        String[] locs = cont.getResources().getStringArray(R.array.issue_locs);
        String[] statuses = cont.getResources().getStringArray(R.array.issue_statuses);
        String[] dates = cont.getResources().getStringArray(R.array.issue_dates);
        String[] info = cont.getResources().getStringArray(R.array.issue_info);
        int[] pts = cont.getResources().getIntArray(R.array.issue_points);
        TypedArray images = cont.getResources().obtainTypedArray(R.array.issue_images);

        PopulateDbAsync(IssueRoomDatabase db) {
            dao = db.issueDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            dao.deleteAll();

            for (int i = 0; i < titles.length; i++) {
                dao.insert(new Issue(
                        titles[i],
                        locs[i],
                        statuses[i],
                        dates[i],
                        info[i],
                        pts[i],
                        images.getResourceId(i, 0)
                ));
            }
            return null;
        }
    }
}
