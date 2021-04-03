package ru.aslazarev.mynotes.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.aslazarev.mynotes.R;
import ru.aslazarev.mynotes.data.Note;
import ru.aslazarev.mynotes.ui.ViewHolderAdapter;

import static ru.aslazarev.mynotes.MainActivity.notes;
import static ru.aslazarev.mynotes.MainActivity.notesCollection;

public class NotesListFragment extends Fragment {

    public static RecyclerView recyclerView;
    public static ViewHolderAdapter vha;
    public static final String CURRENT_NOTE = "current.note";
    private Note currentNote;
    private boolean isLandscape;
    private int mLastSelectedPosition = -1;

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
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
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
            if (notes.size() != 0) {
                currentNote = notes.get(0);
            }
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
        NoteFragment fragment = NoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.land_note_content, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showNoteFragment(Note note) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_list_layout, NoteFragment.newInstance(currentNote));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setLastSelectedPosition(int i){
        mLastSelectedPosition = i;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.edit_note_context_menu){
            if (mLastSelectedPosition != -1) {
                currentNote = notes.get(mLastSelectedPosition);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_list_layout, NoteEditorFragment.newInstance(currentNote));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } else if (item.getItemId() == R.id.remove__note_context_menu){
            if (mLastSelectedPosition != -1) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(requireActivity()).
                                setTitle("delete note").
                                setMessage("A you sure ?").
                                setIcon(R.drawable.delete_icon).
                                setCancelable(true).
                                setPositiveButton("Yes", (dialog, which) -> {
                                    notesCollection.document(notes.get(mLastSelectedPosition).getNoteId()).delete();
                                    notes.remove(mLastSelectedPosition);
                                    vha.notifyItemRemoved(mLastSelectedPosition);
                                }).
                                setNegativeButton("No", (dialog, which) -> {
                                    return;
                                });
                builder.show();

            }
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}