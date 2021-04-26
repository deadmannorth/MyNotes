package ru.aslazarev.mynotes.ui;

import android.view.View;

import ru.aslazarev.mynotes.data.Note;

public interface OnClickListener {
    void onItemClick(Note note);
}
