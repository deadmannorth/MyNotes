package ru.aslazarev.mynotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ru.aslazarev.mynotes.Note;
import ru.aslazarev.mynotes.R;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    ArrayList<Note> notesList;
    public NoteAdapter(ArrayList<Note> list){
        this.notesList = list;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.name.setText(note.getNoteName());
        holder.date.setText(dateFormat.format(note.getCreateDate()));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView name;
        final TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recile_name_note);
            date = itemView.findViewById(R.id.recile_date_note);
        }

    }

}
