package ru.geekbrains.mynotes.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockNotesRepository implements NotesRepository {

    private final ArrayList<Note> data = new ArrayList<>();

    @Override
    public List<Note> getNotes() {
        return new ArrayList<>(data);
    }

    @Override
    public Note addNote() {

        ArrayList<Note> notes = new ArrayList<>();

        notes.add(new Note("id1", "Новая заметка", "текст заметки"));
        notes.add(new Note("id2", "Новая заметка", "текст заметки"));
        notes.add(new Note("id3", "Новая заметка", "текст заметки"));
        notes.add(new Note("id4", "Новая заметка", "текст заметки"));
        notes.add(new Note("id5", "Новая заметка", "текст заметки"));
        notes.add(new Note("id6", "Новая заметка", "текст заметки"));
        notes.add(new Note("id7", "Новая заметка", "текст заметки"));


        Note added = notes.get(new Random().nextInt(notes.size()));
        data.add(added);
        return added;
    }

    @Override
    public void removeAtPosition(int longClickedPosition) {
        data.remove(longClickedPosition);
    }
}
