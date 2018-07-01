package com.example.specialproject.journalapp.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.specialproject.journalapp.Executors.AppExecutors;
import com.example.specialproject.journalapp.Database.JournalDatabase;
import com.example.specialproject.journalapp.Models.JournalEntry;
import com.example.specialproject.journalapp.Models.PersistingToFirebase;
import com.example.specialproject.journalapp.R;
import com.example.specialproject.journalapp.ViewModels.AddJournalViewModel;
import com.example.specialproject.journalapp.ViewModels.AddJournalViewModelFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    EditText titleEdit, bodyEdit;

    // Extra for the entry ID to be received in the intent
    public static final String EXTRA_ENTRY_ID = "extraEntryId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_ENTRY_ID = "instanceEntryId";

    private static final int DEFAULT_ENTRY_ID = -1;

    private int mEntryId = DEFAULT_ENTRY_ID;
    //Database instance
    private JournalDatabase mDb;

    //Firebase database declaration
    DatabaseReference journalReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        mDb = JournalDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ENTRY_ID)) {
            mEntryId = savedInstanceState.getInt(INSTANCE_ENTRY_ID, DEFAULT_ENTRY_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_ENTRY_ID)) {
            if (mEntryId == DEFAULT_ENTRY_ID) {
                mEntryId = intent.getIntExtra(EXTRA_ENTRY_ID, DEFAULT_ENTRY_ID);
                AddJournalViewModelFactory factory = new AddJournalViewModelFactory(mDb, mEntryId);
                final AddJournalViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddJournalViewModel.class);

                viewModel.getTask().observe(this, new Observer<JournalEntry>() {
                    @Override
                    public void onChanged(@Nullable JournalEntry journalEntry) {
                        viewModel.getTask().removeObserver(this);
                        populateUI(journalEntry);
                    }
                });
            }
        }

        initView();
    }

    private void populateUI(JournalEntry entry) {
        if (entry == null) {
            return;
        }

        titleEdit.setText(entry.getTitle());
        bodyEdit.setText(entry.getDescription());
    }

    private void initView() {
        titleEdit = findViewById(R.id.title_edit);
        bodyEdit = findViewById(R.id.body_edit);
        journalReference = FirebaseDatabase.getInstance().getReference(getString(R.string.noteStoredFirebase));
    }

    public void addJournal() {
        String journalTitle = titleEdit.getText().toString().trim();
        String journalBody = bodyEdit.getText().toString().trim();

        if (!TextUtils.isEmpty(journalTitle) && !TextUtils.isEmpty(journalBody)) {
            String id = journalReference.push().getKey();
            PersistingToFirebase ptf = new PersistingToFirebase(id, journalTitle, journalBody);
            journalReference.child(id).setValue(ptf);

        } else {
            Toast.makeText(this, R.string.toastForEmpty, Toast.LENGTH_SHORT).show();
        }
    }

    public void saveNote(View view) {

        String title = titleEdit.getText().toString();
        String description = bodyEdit.getText().toString();
        Date date = new Date();

        final JournalEntry journalEntry = new JournalEntry(title, description, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.journalDao().insertEntry(journalEntry);
                finish();
            }
        });
        addJournal();
    }

}
