package org.williamsonministry.roomexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

//Repositories aren't super necessary when there's only one data source, but what a repository does is make all data be treated the same whether it comes from the internet or from a db or whatevs.

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao(); /*Normally we can't call an abstract method, but we can here because the builder in the database.getInstance uses fancy Room whatevers to make the noteDao() method a subclass or something*/
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note)   {
        new InsertNoteAsyncTask(noteDao).execute(note); /*.execute comes from AsyncTask extension and passing note as required by first argument in extension below*/
    }

    public void update(Note note)   {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note)   {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes()    {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes; /*Room automatically executes LiveData functions on the database on a background thread, so this one is good. The rest we have to manually make background or else it'll crash cos Room doesn't allow foreground db operations0*/
    }

    private static /*must be static so it doesn't have a reference to the repository itself - that would cause a memory leak*/ class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>  /*pass, update, return*/  {
        private NoteDao noteDao;

        private InsertNoteAsyncTask (NoteDao noteDao)   {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask (NoteDao noteDao)   {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask (NoteDao noteDao)   {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask (NoteDao noteDao)   {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
