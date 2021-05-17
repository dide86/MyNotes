package ru.geekbrains.mynotes.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FirestoreNotesRepository implements NotesRepository {
    private static final String NOTES = "notes";
    private static final String TITLE = "title";
    private static final String TEXT = "noteText";
    private static final String CREATED = "createDate";

    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        fireStore.collection(NOTES)
                .orderBy(CREATED)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Note> tmp = new ArrayList<>();
                            List<DocumentSnapshot> docs = task.getResult().getDocuments();

                            for (DocumentSnapshot doc : docs) {
                                String id = doc.getId();
                                String title = doc.getString(TITLE);
                                String noteText = doc.getString(TEXT);
                                Date createDate = doc.getDate(CREATED);
                                tmp.add(new Note(id, title, noteText, createDate));
                            }
                            callback.onSuccess(tmp);
                        } else {
                            callback.onError(task.getException());
                        }

                    }
                });
    }

    @Override
    public void addNote(String title, String noteText, Callback<Note> callback) {

        HashMap<String, Object> data = new HashMap<>();

        Date date = new Date();

        data.put(TITLE, title);
        data.put(TEXT, noteText);
        data.put(CREATED, date);

        fireStore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        if (task.isSuccessful()) {
                            callback.onSuccess(new Note(task.getResult().getId(), title, noteText, date));
                        } else {
                            callback.onError(task.getException());
                        }
                    }
                });

    }

    @Override
    public void remove(Note item, Callback<Object> callback) {

        fireStore.collection(NOTES)
                .document(item.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            callback.onSuccess(new Object());
                        } else {
                            callback.onError(task.getException());
                        }

                    }
                });

    }
}