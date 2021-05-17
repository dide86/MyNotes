package ru.geekbrains.mynotes.domain;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    void addNote(String title, String text, Callback<Note> callback);

    void remove(Note item, Callback<Object> callback);
}
