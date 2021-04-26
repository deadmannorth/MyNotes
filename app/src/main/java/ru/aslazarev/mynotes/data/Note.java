package ru.aslazarev.mynotes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Note implements Parcelable {

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_DATE = "date";


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    private String noteId;
    private String noteName;
    private String noteContent;
    private Date createDate;

    public Note(){

    }

    public Note(String noteName, String noteContent, Date createDate) {
        this.noteName = noteName;
        this.noteContent = noteContent;
        this.createDate = createDate;
    }

    public Note(String id, String noteName, String noteContent, Date createDate) {
        this(noteName, noteContent, createDate);
        this.noteId = id;
    }

    public Note(String id, Map<String, Object> fields){
        this.noteId = id;
        this.noteName = (String) fields.get(FIELD_NAME);
        this.noteContent = (String) fields.get(FIELD_CONTENT);
        try {
            this.createDate = dateFormat.parse((String) fields.get(FIELD_DATE));
        } catch (ParseException e) {
            this.createDate = new Date();
        }
    }

    protected Note(Parcel in) {
        noteId = in.readString();
        noteName = in.readString();
        noteContent = in.readString();
        try {
            createDate = dateFormat.parse(in.readString());
        } catch (ParseException e) {
            createDate = new Date();
        }
    }

    public final Map<String, Object> getFields(){
        HashMap<String, Object> fields = new HashMap<>();
        fields.put(FIELD_NAME, getNoteName());
        fields.put(FIELD_CONTENT, getNoteContent());
        fields.put(FIELD_DATE, dateFormat.format(getCreateDate()).toString());
        return Collections.unmodifiableMap(fields);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getNoteName() {
        return noteName;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public void setDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(noteName);
        parcel.writeString(noteContent);
        parcel.writeString(dateFormat.format(createDate));
    }
}
