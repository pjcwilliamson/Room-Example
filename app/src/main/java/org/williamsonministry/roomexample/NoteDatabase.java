package org.williamsonministry.roomexample;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}/*This is an array if I want more entities. Right now, an array.size()==1 */, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized /*synchronized means only one thread can access at a time*/ NoteDatabase getInstance(Context context)    {
        if (instance == null)   {
            instance = Room.databaseBuilder(context.getApplicationContext(),
            NoteDatabase.class, "note_database")
            .fallbackToDestructiveMigration()/*deletes database if new version number to stop crashes*/
            .build();
        }
        return instance;
    }
}