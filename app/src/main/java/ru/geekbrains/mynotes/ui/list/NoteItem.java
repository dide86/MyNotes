package ru.geekbrains.mynotes.ui.list;

import ru.geekbrains.mynotes.domain.Note;

public class NoteItem implements AdapterItem {

    private final Note note;

    public NoteItem(Note note) {
        this.note = note;
    }

    @Override
    public String getUniqueTag() {
        return "NoteItem" + note.getId();
    }

    public Note getNote() {
        return note;
    }
}

