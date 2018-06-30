package com.example.specialproject.journalapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.specialproject.journalapp.Models.JournalEntry;
import com.example.specialproject.journalapp.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private Context mContext;

    private static final String DATE_FORMAT = "dd/MM/yyy";

    final private EntryClickListener entryClickListener;
    private List<JournalEntry> journalEntriesList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public JournalAdapter(Context mContext, EntryClickListener entryClickListener) {
        this.mContext = mContext;
        this.entryClickListener = entryClickListener;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.display_info, null);
        return new JournalViewHolder(view);
    }

    public void setEntries(List<JournalEntry> journalEntries) {
        journalEntriesList = journalEntries;
        notifyDataSetChanged();
    }

    public List<JournalEntry> getEntries() {
        return journalEntriesList;
    }

    public interface EntryClickListener {
        void onItemClickListener(int itemId);
    }

    @Override
    public void onBindViewHolder(@NonNull final JournalViewHolder holder, int position) {
        JournalEntry entries = journalEntriesList.get(position);
        String dateText = dateFormat.format(entries.getUpdatedAt());

        holder.titleOfEntry.setText(entries.getTitle());
        holder.bodyOfEntry.setText(entries.getDescription());
        holder.dateText.setText(dateText);
        String firstLetter = entries.getTitle().substring(0, 1);
        if ("aeiouAEIOU".indexOf(firstLetter) != -1) {
            holder.firstText.setBackgroundResource(R.drawable.oval_holder_blue);
        } else {
            holder.firstText.setBackgroundResource(R.drawable.oval_holder_red);
        }
        holder.firstText.setText(firstLetter);
    }

    @Override
    public int getItemCount() {
        if (journalEntriesList == null) {
            return 0;
        }
        return journalEntriesList.size();
    }

    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView firstText, titleOfEntry, bodyOfEntry, dateText;

        public JournalViewHolder(View itemView) {
            super(itemView);
            firstText = itemView.findViewById(R.id.first_letter);
            titleOfEntry = itemView.findViewById(R.id.title_of_entry);
            bodyOfEntry = itemView.findViewById(R.id.body_of_entry);
            dateText = itemView.findViewById(R.id.date_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = journalEntriesList.get(getAdapterPosition()).getId();
            entryClickListener.onItemClickListener(elementId);
        }
    }
}
