package ru.aslazarev.mynotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import static ru.aslazarev.mynotes.MainActivity.notes;

public class NotesListFragment extends Fragment {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

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
                showNoteFragment(index);
            });
            layoutView.addView(noteLayout);
        }

    }

    private void showNoteFragment(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteFragmentActivity.class);
        intent.putExtra(NoteFragment.ARG_INDEX, index);
        startActivity(intent);


    }

}