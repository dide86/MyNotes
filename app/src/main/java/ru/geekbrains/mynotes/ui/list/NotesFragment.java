package ru.geekbrains.mynotes.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
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
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState == null) {
            viewModel.requestNotes();
        }

        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), new Observer<List<AdapterItem>>() {
            @Override
            public void onChanged(List<AdapterItem> notes) {
                adapter.setData(notes);
            }
        });

        RecyclerView notesList = view.findViewById(R.id.notes_list);

//        viewModel.getNoteAddedLiveData().observe(getViewLifecycleOwner(), new Observer<Note>() {
//            @Override
//            public void onChanged(Note note) {
//                int position = adapter.addData(note);
//                notesList.smoothScrollToPosition(position);
//            }
//        });

//        viewModel.getNoteDeletedLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer position) {
//                adapter.delete(position);
//            }
//        });

        // RecyclerView.LayoutManager lm = /*new GridLayoutManager(this, 2);*/new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        GridLayoutManager lm = new GridLayoutManager(requireContext(), 2);

        lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getSpanSize(position);
            }
        });

        notesList.setLayoutManager(lm);

        notesList.setAdapter(adapter);

//        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.items_separator));
//
//        notesList.addItemDecoration(itemDecoration);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
//        itemAnimator.setRemoveDuration(5000L);
//        itemAnimator.setAddDuration(5000L);
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
            Toast.makeText(requireContext(), "Open", Toast.LENGTH_SHORT).show();

            return true;
        }

        if (item.getItemId() == R.id.action_update) {

            if (getActivity() instanceof RouterHolder) {
                AppRouter router = ((RouterHolder)getActivity()).getRouter();

                router.editNote(adapter.getItemAt(adapter.getLongClickedPosition()));
            }

            return true;
        }

        if (item.getItemId() == R.id.action_delete) {
            viewModel.deleteClicked(adapter.getItemAt(adapter.getLongClickedPosition()));
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
