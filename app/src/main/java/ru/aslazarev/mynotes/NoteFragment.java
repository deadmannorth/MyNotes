package ru.aslazarev.mynotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    public static final String ARG_INDEX = "note";
    private Note note;

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);
        Note noteF = note;
        TextView name = view.findViewById(R.id.noteName);
        TextView date = view.findViewById(R.id.noteDate);
        TextView content = view.findViewById(R.id.noteContent);
        name.setText(noteF.getNoteName().toString());
        name.setTextSize(30);
        date.setText(dateFormat.format(noteF.getCreateDate()).toString());
        date.setTextSize(30);
        content.setText(noteF.getNoteContent());
        content.setTextSize(30);
        return view;
    }
}