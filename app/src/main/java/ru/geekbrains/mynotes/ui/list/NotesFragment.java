package ru.geekbrains.mynotes.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;
import ru.geekbrains.mynotes.router.AppRouter;
import ru.geekbrains.mynotes.router.RouterHolder;

public class NotesFragment extends Fragment {

    private NotesListViewModel viewModel;

    private NotesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NotesListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add) {
                    viewModel.addClicked();
                    return true;
                }
                return false;
            }
        });

        adapter = new NotesAdapter(this);

        adapter.setClickListener(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {

            }
        });

        if (savedInstanceState == null) {
            viewModel.requestNotes();
        }

        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setData(notes);
            }
        });

        RecyclerView notesList = view.findViewById(R.id.notes_list);


        RecyclerView.LayoutManager lm = new GridLayoutManager(requireContext(), 2);//new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        notesList.setLayoutManager(lm);

        notesList.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);

        notesList.addItemDecoration(itemDecoration);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();

        notesList.setItemAnimator(itemAnimator);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.menu_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_open) {


            return true;
        }

        if (item.getItemId() == R.id.action_update) {

            if (getActivity() instanceof RouterHolder) {
                AppRouter router = ((RouterHolder) getActivity()).getRouter();

                router.editNote(adapter.getItemAt(adapter.getLongClickedPosition()));
            }

            return true;
        }

        if (item.getItemId() == R.id.action_delete) {
            viewModel.deleteClicked(adapter.getLongClickedPosition());
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
