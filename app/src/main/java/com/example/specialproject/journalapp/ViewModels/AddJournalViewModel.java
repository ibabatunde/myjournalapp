package com.example.specialproject.journalapp.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.specialproject.journalapp.Database.JournalDatabase;
import com.example.specialproject.journalapp.Models.JournalEntry;

// COMPLETED (5) Make this class extend ViewModel
public class AddJournalViewModel extends ViewModel {

    private LiveData<JournalEntry> mEntry;

    public AddJournalViewModel(JournalDatabase database, int taskId) {
        mEntry = database.journalDao().loadTaskById(taskId);
    }

    public LiveData<JournalEntry> getTask() {
        return mEntry;
    }
}
