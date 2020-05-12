package com.example.manoa_fix_it;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Complaint.class}, version = 1, exportSchema = false)
public abstract class ComplRoomDatabase extends RoomDatabase {
    public abstract ComplaintDao complaintDao();
    private static Context cont;
    private static ComplRoomDatabase INSTANCE;

    static ComplRoomDatabase getDatabase(final Context context) {
        cont = context;
        if (INSTANCE == null) {
            synchronized (ComplRoomDatabase.class) {
                if (INSTANCE == null) {
                    // creates database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ComplRoomDatabase.class, "complaint_database")
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
            private final ComplaintDao dao;
            int[] userId = cont.getResources().getIntArray(R.array.issue_user_id);
            String[] titles = cont.getResources().getStringArray(R.array.compl_titles);
            String[] locs = cont.getResources().getStringArray(R.array.issue_locs);
            String[] dates = cont.getResources().getStringArray(R.array.issue_dates);
            String[] descs = cont.getResources().getStringArray(R.array.compl_info);
            int[] pts = cont.getResources().getIntArray(R.array.issue_points);
            PopulateDbAsync(ComplRoomDatabase db) {
                dao = db.complaintDao();
            }

            @Override
            protected Void doInBackground(final Void... params) {
                dao.deleteAll();

                for (int i = 0; i < 2; i++) {
                    dao.insert(new Complaint(
                            userId[i + 1],
                            titles[i],
                            locs[i],
                            Long.parseLong(dates[i + 2]),
                            descs[i],
                            pts[i + 1]
                    ));
                }
                return null;
            }
        }
    }
