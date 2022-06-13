package org.williamsonministry.roomexample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}/*This is an array if I want more entities. Right now, an array.size()==1 */, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized /*synchronized means only one thread can access at a time*/ NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()/*deletes database if new version number to stop crashes*/
                    .addCallback(roomCallback) /*This callback the DB initiate with some initial data*/
                    .build();
        }
        return instance;
    }

    // TODO: 6/12/2022 THIS VIDEO NEXT: https://www.youtube.com/watch?v=JLwW5HivZg4 

    //This below is all to make it make a database when we first open the app.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1", "Desc1", 1));
            noteDao.insert(new Note("Title2", "Desc2", 2));
            noteDao.insert(new Note("Title3", "Desc3", 3));
            return null;
        }
    }
}