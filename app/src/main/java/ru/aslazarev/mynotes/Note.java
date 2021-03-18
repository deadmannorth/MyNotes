package ru.aslazarev.mynotes;

import java.util.Date;

public class Note {

    private String noteName;
    private String noteContent;
    private Date createDate;

    public Note(String noteName, String noteContent, Date createDate) {
        this.noteName = noteName;
        this.noteContent = noteContent;
        this.createDate = createDate;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
