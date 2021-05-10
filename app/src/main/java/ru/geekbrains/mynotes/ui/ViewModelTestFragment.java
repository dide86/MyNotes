package ru.geekbrains.mynotes.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.ui.list.NotesListViewModel;

public class ViewModelTestFragment extends Fragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NotesListViewModel viewModel = new ViewModelProvider(requireActivity()).get(NotesListViewModel.class);
        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

            }
        });
    }
}
