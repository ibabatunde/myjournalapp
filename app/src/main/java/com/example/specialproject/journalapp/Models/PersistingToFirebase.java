package com.example.specialproject.journalapp.Models;

public class PersistingToFirebase {

    private String journalID;
    private String journalTitle;
    private String journalMessage;

    public PersistingToFirebase() {
        //For reading from the firebase
    }

    public PersistingToFirebase(String journalID, String journalTitle, String journalMessage) {
        this.journalID = journalID;
        this.journalTitle = journalTitle;
        this.journalMessage = journalMessage;
    }

    public String getJournalID() {
        return journalID;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public String getJournalMessage() {
        return journalMessage;
    }
}
