package com.example.specialproject.journalapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.specialproject.journalapp.Models.JournalEntry;

@Database(entities = {JournalEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class JournalDatabase extends RoomDatabase {
    public static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "journals";
    public static JournalDatabase journalInstance;

    public static JournalDatabase getInstance(Context context) {
        if (journalInstance == null) {
            synchronized (LOCK) {
                journalInstance = Room.databaseBuilder(context.getApplicationContext(),
                        JournalDatabase.class, JournalDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return journalInstance;
    }

    public abstract JournalDao journalDao();
}
