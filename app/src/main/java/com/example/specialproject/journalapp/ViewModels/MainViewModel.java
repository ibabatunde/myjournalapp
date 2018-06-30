package com.example.specialproject.journalapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.specialproject.journalapp.Database.JournalDatabase;
import com.example.specialproject.journalapp.Models.JournalEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<JournalEntry>> entry;

    public MainViewModel(Application application) {
        super(application);
        JournalDatabase database = JournalDatabase.getInstance(this.getApplication());
        entry = database.journalDao().loadAllEntries();
    }

    public LiveData<List<JournalEntry>> getEntries() {
        return entry;
    }
}
