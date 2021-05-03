package ru.geekbrains.mynotes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

public class Note implements Parcelable {

    private final String title;
    private final String content;
    @DrawableRes
    private final int drawbleRes;


    public Note(String title, String content, int drawbleRes) {
        this.title = title;
        this.content = content;
        this.drawbleRes = drawbleRes;
    }

    protected Note(Parcel in) {
        title = in.readString();
        content = in.readString();
        drawbleRes = in.readInt();
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

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getDrawbleRes() {
        return drawbleRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(drawbleRes);
    }
}
