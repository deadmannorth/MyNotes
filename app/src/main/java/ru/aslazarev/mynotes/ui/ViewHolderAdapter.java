package ru.aslazarev.mynotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.aslazarev.mynotes.R;
import ru.aslazarev.mynotes.data.Note;
import ru.aslazarev.mynotes.fragments.NotesListFragment;

public class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder>{

    private final NotesListFragment mFragment;
    private final LayoutInflater mInflater;
    private final ArrayList<Note> mNotes;
    private OnClickListener mOnClickListener;

    public ViewHolderAdapter(Fragment fragment, LayoutInflater inflater, ArrayList<Note> notes) {
        this.mFragment = (NotesListFragment) fragment;
        this.mInflater = inflater;
        this.mNotes = notes;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
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
        if (mNote.getCreateDate() != null){
            holder.date.setText(dateFormat.format(mNote.getCreateDate()));
        } else {
            holder.date.setText(dateFormat.format(new Date()));
        }

        holder.itemView.setOnClickListener(v -> {
            if (mOnClickListener != null) {
                mOnClickListener.onItemClick(mNote);
            }
        });
        holder.lastPosition(mFragment);
        mFragment.registerForContextMenu(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

}

