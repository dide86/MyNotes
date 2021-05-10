package ru.geekbrains.mynotes.router;

import androidx.fragment.app.FragmentManager;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.ui.edit.EditNoteFragment;
import ru.geekbrains.mynotes.ui.list.NotesFragment;

public class AppRouter {

    private final FragmentManager fragmentManager;

    public AppRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotesList() {
        fragmentManager.beginTransaction()
                .replace(R.id.container, new NotesFragment())
                .commit();
    }

    public void editNote(Note note) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, EditNoteFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }
}
