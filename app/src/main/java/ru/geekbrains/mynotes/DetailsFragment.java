package ru.geekbrains.mynotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.geekbrains.mynotes.domain.Note;


public class DetailsFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";

    public static DetailsFragment newInstance (Note note) {

        DetailsFragment fragment = new DetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);

        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = view.findViewById(R.id.title);
        TextView content = view.findViewById(R.id.content);

        Note note = getArguments().getParcelable(ARG_NOTE);

        title.setText(note.getTitle());
        content.setText(note.getContent());


    }
}