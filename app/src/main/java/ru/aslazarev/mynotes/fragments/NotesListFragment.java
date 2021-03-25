package ru.aslazarev.mynotes.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.aslazarev.mynotes.NoteFragmentActivity;
import ru.aslazarev.mynotes.R;
import ru.aslazarev.mynotes.data.Note;
import ru.aslazarev.mynotes.ui.ViewHolderAdapter;

import static ru.aslazarev.mynotes.MainActivity.notes;

public class NotesListFragment extends Fragment {

    public static RecyclerView recyclerView;
    public static ViewHolderAdapter vha;
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
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_notes_list, container, false);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.docoration));
        recyclerView.addItemDecoration(decoration);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        vha = new ViewHolderAdapter(this, inflater, notes);
        vha.setOnClickListener(note -> {
            showFragment(note);
        });
        recyclerView.setAdapter(vha);
        return recyclerView;
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