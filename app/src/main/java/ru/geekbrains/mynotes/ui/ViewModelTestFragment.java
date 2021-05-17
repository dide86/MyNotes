package ru.geekbrains.mynotes.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.geekbrains.mynotes.ui.list.NotesListViewModel;

public class ViewModelTestFragment extends Fragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NotesListViewModel viewModel = new ViewModelProvider(requireActivity()).get(NotesListViewModel.class);
//        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
//            @Override
//            public void onChanged(List<Note> notes) {
//
//            }
//        });
    }
}
