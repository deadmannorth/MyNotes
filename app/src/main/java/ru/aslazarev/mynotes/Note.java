package ru.aslazarev.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Parcelable {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    private String noteName;
    private String noteContent;
    private Date createDate;

    public Note(String noteName, String noteContent, Date createDate) {
        this.noteName = noteName;
        this.noteContent = noteContent;
        this.createDate = createDate;
    }

    protected Note(Parcel in) {
        noteName = in.readString();
        noteContent = in.readString();
        try {
            createDate = dateFormat.parse(in.readString());
        } catch (ParseException e) {
            createDate = new Date();
        }
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
