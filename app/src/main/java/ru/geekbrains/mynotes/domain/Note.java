package ru.geekbrains.mynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;


public class Note implements Parcelable {

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
    private String id;
    private String title;
    private String noteText;
    private Date createDate;

    public Note(String id, String title, String noteText, Date createDate) {
        this.id = id;
        this.title = title;
        this.noteText = noteText;
        this.createDate = createDate;
    }

    protected Note(Parcel in) {
        id = in.readString();
        title = in.readString();
        noteText = in.readString();
        createDate = (Date) in.readSerializable();

    }


    public Date getCreatedAt() {
        return createDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNoteText() {
        return noteText;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) &&
                Objects.equals(title, note.title)
                &&
                Objects.equals(noteText, note.noteText) &&
                Objects.equals(createDate, note.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, noteText, createDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(noteText);
        dest.writeSerializable(createDate);
    }
}
