package ru.geekbrains.mynotes.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.geekbrains.mynotes.domain.MockNotesRepository;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.domain.NotesRepository;

public class NotesListViewModel extends ViewModel {

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    private final NotesRepository repository = new MockNotesRepository();

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    public void requestNotes() {
        notesLiveData.setValue(repository.getNotes());
    }

    public void addClicked() {
        repository.addNote();
        notesLiveData.setValue(repository.getNotes());

    }


    public void deleteClicked(int longClickedPosition) {
        repository.removeAtPosition(longClickedPosition);
        notesLiveData.setValue(repository.getNotes());


    }

}
