package com.example.specialproject.journalapp.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.specialproject.journalapp.Database.JournalDatabase;

public class AddJournalViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final JournalDatabase mDb;
    private final int mEntryId;

    public AddJournalViewModelFactory(JournalDatabase database, int entryId) {
        mDb = database;
        mEntryId = entryId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AddJournalViewModel(mDb, mEntryId);
    }
}
