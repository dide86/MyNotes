package ru.geekbrains.mynotes.ui.list;

import android.annotation.SuppressLint;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ru.geekbrains.mynotes.domain.Callback;
import ru.geekbrains.mynotes.domain.FirestoreNotesRepository;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.domain.NotesRepository;

public class NotesListViewModel extends ViewModel {

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

//    private final MutableLiveData<Note> noteAddedLiveData = new MutableLiveData<>();
//
//    private final MutableLiveData<Integer> noteDeletedLiveData = new MutableLiveData<>();

    private final NotesRepository repository = new FirestoreNotesRepository();

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");

    public LiveData<List<AdapterItem>> getNotesLiveData() {
        return Transformations.map(notesLiveData, new Function<List<Note>, List<AdapterItem>>() {
            @Override
            public List<AdapterItem> apply(List<Note> input) {

                ArrayList<AdapterItem> result = new ArrayList<>();

                Date date = null;

                for (Note note: input) {

                    Calendar calendar = Calendar.getInstance();

                    calendar.setTime(note.getCreatedAt());
                    calendar.set(Calendar.HOUR, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.set(Calendar.SECOND, 0);

                    Date noteDate = calendar.getTime();

                    if (!noteDate.equals(date)) {
                        result.add(new HeaderItem(simpleDateFormat.format(noteDate)));

                        date = noteDate;
                    }

                    result.add(new NoteItem(note));
                }

                return result;
            }
        });
    }

    public void requestNotes() {
        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> value) {
                notesLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public void addClicked() {
        repository.addNote(UUID.randomUUID().toString(), "Новое событие", new Callback<Note>() {
            @Override
            public void onSuccess(Note value) {
                if (notesLiveData.getValue() != null) {

                    ArrayList<Note> notes = new ArrayList<>(notesLiveData.getValue());

                    notes.add(value);

                    notesLiveData.setValue(notes);
                } else {
                    ArrayList<Note> notes = new ArrayList<>();
                    notes.add(value);

                    notesLiveData.setValue(notes);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
//        notesLiveData.setValue(repository.getNotes());
        //noteAddedLiveData.setValue(note);
    }

    // public LiveData<Note> getNoteAddedLiveData() {
//        return noteAddedLiveData;
//    }

    public void deleteClicked(Note note) {

        repository.remove(note, new Callback<Object>() {
            @Override
            public void onSuccess(Object value) {

                if (notesLiveData.getValue() != null) {

                    ArrayList<Note> notes = new ArrayList<>(notesLiveData.getValue());

                    notes.remove(note);

                    notesLiveData.setValue(notes);
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });

//        repository.removeAtPosition(longClickedPosition);
//        notesLiveData.setValue(repository.getNotes());

//        noteDeletedLiveData.setValue(longClickedPosition);
    }

//    public LiveData<Integer> getNoteDeletedLiveData() {
//        return noteDeletedLiveData;
//    }
}
