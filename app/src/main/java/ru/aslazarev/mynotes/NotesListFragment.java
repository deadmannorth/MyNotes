package ru.aslazarev.mynotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import static ru.aslazarev.mynotes.MainActivity.notes;

public class NotesListFragment extends Fragment {

    public static final String CURRENT_NOTE = "current.note";
    private Note currentNote;

    private boolean isLandscape;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNoteList(view);
    }

    private void initNoteList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        for (int i = 0; i < notes.size(); i++) {
            Note nextNote = notes.get(i);
            LinearLayout noteLayout = new LinearLayout(getContext());
            noteLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView noteName = new TextView(getContext());
            noteName.setText(nextNote.getNoteName() + "     ");
            noteName.setTextSize(30);
            noteLayout.addView(noteName);
            TextView noteDate = new TextView(getContext());
            noteDate.setText(dateFormat.format(nextNote.getCreateDate()).toString());
            noteDate.setTextSize(30);
            noteLayout.addView(noteDate);
            final int index = i;
            noteLayout.setOnClickListener(v -> {
                currentNote = notes.get(index);
                showFragment(currentNote);
            });
            layoutView.addView(noteLayout);
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
        //касяк переделывай
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