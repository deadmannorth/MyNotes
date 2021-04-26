package ru.aslazarev.mynotes.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.aslazarev.mynotes.R;
import ru.aslazarev.mynotes.data.Note;


public class NoteEditorFragment extends Fragment {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    private static final String ARG_ITEM_EDITOR = "NoteEditorFragment.item";

    Calendar dateAndTime = Calendar.getInstance();

    // TODO: Rename and change types of parameters
    private Note note;

    public NoteEditorFragment() {
        // Required empty public constructor
    }


    public static NoteEditorFragment newInstance(Note note) {
        NoteEditorFragment fragment = new NoteEditorFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM_EDITOR, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_ITEM_EDITOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_note_editor, container, false);
        TextInputEditText editName = view.findViewById(R.id.set_noteName);
        MaterialButton editDate = view.findViewById(R.id.set_noteDate);
        TextInputEditText editContent = view.findViewById(R.id.set_noteContent);
        MaterialButton saveNote = view.findViewById(R.id.save_new_note);
        if (note.getNoteName() != null){
            editName.setText(note.getNoteName());
            editDate.setText(note.getCreateDate().toString());
            editContent.setText(note.getNoteContent());
        }
        editDate.setOnClickListener(v -> {
            setDate(container);
            editDate.setText(dateFormat.format(dateAndTime.getTimeInMillis()).toString());
        });
        saveNote.setOnClickListener(v -> {
                note.setNoteName(editName.getText().toString());
                note.setDate(new Date(dateAndTime.getTimeInMillis()));
                note.setNoteContent(editContent.getText().toString());
                getFragmentManager().popBackStack();
        });
        return view;
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
    };

    public void setDate(View v) {
        new DatePickerDialog(getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

}