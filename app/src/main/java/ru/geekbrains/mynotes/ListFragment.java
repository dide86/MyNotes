package ru.geekbrains.mynotes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.domain.NotesRepository;


public class ListFragment extends Fragment {

    public interface onNoteClicked  {
        void onNoteClicked (Note note);
    }

    private onNoteClicked onNoteClicked;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof onNoteClicked) {
            onNoteClicked = (onNoteClicked) context;
        }
    }

    @Override
    public void onDetach() {
        onNoteClicked = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Note> notes = new NotesRepository().getNotes();
        LinearLayout notesList = view.findViewById(R.id.notes_list);

        for (Note note : notes) {
            View noteView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesList, false);

            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNoteDetail(note);
                }
            });

            ImageView image = noteView.findViewById(R.id.image);
            image.setImageResource(note.getDrawbleRes());

            TextView title = noteView.findViewById(R.id.title);
            title.setText(note.getTitle());

            notesList.addView(noteView);
        }
    }

    private void openNoteDetail (Note note) {
        if (onNoteClicked != null) {
            onNoteClicked.onNoteClicked(note);
        }
    }
}