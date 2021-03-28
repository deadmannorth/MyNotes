package ru.aslazarev.mynotes.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.aslazarev.mynotes.R;
import ru.aslazarev.mynotes.fragments.NotesListFragment;

public class ViewHolder extends RecyclerView.ViewHolder{

    final TextView name;
    final TextView date;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.r_name_note);
        date = itemView.findViewById(R.id.r_date_note);
    }

    public void lastPosition(NotesListFragment fragment){
        itemView.setOnLongClickListener(v -> {
            fragment.setLastSelectedPosition(getLayoutPosition());
            return false;
        });
    }

    public void clear(Fragment fragment) {
        itemView.setOnLongClickListener(null);
        fragment.unregisterForContextMenu(itemView);
    }


}
