package com.example.specialproject.journalapp.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.specialproject.journalapp.Database.JournalDatabase;

// COMPLETED (1) Make this class extend ViewModel ViewModelProvider.NewInstanceFactory
public class AddJournalViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // COMPLETED (2) Add two member variables. One for the database and one for the taskId
    private final JournalDatabase mDb;
    private final int mEntryId;

    // COMPLETED (3) Initialize the member variables in the constructor with the parameters received
    public AddJournalViewModelFactory(JournalDatabase database, int entryId) {
        mDb = database;
        mEntryId = entryId;
    }

    // COMPLETED (4) Uncomment the following method
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddJournalViewModel(mDb, mEntryId);
    }
}
