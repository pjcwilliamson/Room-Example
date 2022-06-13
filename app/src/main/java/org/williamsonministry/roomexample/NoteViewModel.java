package org.williamsonministry.roomexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//ViewModels are nice because then when an activity resets (eg. in rotation and other configuration changes like a language change) the data source is not reset so the info is still present

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        //No activity references (incl. context) in ViewModel cos VM outlives them

        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note)   {
        repository.insert(note);
    }

    public void update(Note note)   {
        repository.update(note);
    }

    public void delete(Note note)   {
        repository.delete(note);
    }

    public void deleteAllNotes()   {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
