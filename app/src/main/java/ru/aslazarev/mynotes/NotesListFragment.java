package ru.aslazarev.mynotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static ru.aslazarev.mynotes.MainActivity.notes;

public class NotesListFragment extends Fragment {

    public static final String CURRENT_NOTE = "current.note";
    private Note currentNote;

    private boolean isLandscape;


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_notes_list, container, false);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ViewHolderAdapter(inflater, notes));
        return recyclerView;
    }

    private class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final LayoutInflater mInflater;
        private final ArrayList<Note> mNotes;

        public ViewHolderAdapter(LayoutInflater inflater, ArrayList<Note> notes) {
            this.mInflater = inflater;
            this.mNotes = notes;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = mInflater.inflate(R.layout.list_item, (ViewGroup) parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
                Note mNote = mNotes.get(position);
                holder.name.setText(mNote.getNoteName());
                holder.date.setText(dateFormat.format(mNote.getCreateDate()));
                holder.item.setOnClickListener(v -> {
                showFragment(mNote);
                });
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView name;
        final TextView date;
        final LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.r_name_note);
            date = itemView.findViewById(R.id.r_date_note);
            item = itemView.findViewById(R.id.r_item);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if(savedInstanceState != null){
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = notes.get(0);
        }
        if (isLandscape) {
            showNoteFragmentLand(currentNote);
        }


    }

    private void showFragment(Note note) {
        if (isLandscape) {
            showNoteFragmentLand(note);
        } else {
            showNoteFragment(note);
        }

    }

    private void showNoteFragmentLand(Note note) {
        NoteFragment fragment = NoteFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.land_note_content, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }



    private void showNoteFragment(Note note) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteFragmentActivity.class);
        intent.putExtra(NoteFragment.ARG_INDEX, currentNote);
        startActivity(intent);
    }

}