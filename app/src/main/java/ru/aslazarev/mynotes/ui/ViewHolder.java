package ru.aslazarev.mynotes.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.aslazarev.mynotes.R;

public class ViewHolder extends RecyclerView.ViewHolder{

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
