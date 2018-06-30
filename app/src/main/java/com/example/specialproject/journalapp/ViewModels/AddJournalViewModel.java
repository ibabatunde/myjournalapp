package com.example.specialproject.journalapp.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.specialproject.journalapp.Database.JournalDatabase;
import com.example.specialproject.journalapp.Models.JournalEntry;

// COMPLETED (5) Make this class extend ViewModel
public class AddJournalViewModel extends ViewModel {

    private LiveData<JournalEntry> entry;

    public AddJournalViewModel(JournalDatabase database, int taskId) {
        entry = database.journalDao().loadTaskById(taskId);
    }

    public LiveData<JournalEntry> getTask() {
        return entry;
    }
}
