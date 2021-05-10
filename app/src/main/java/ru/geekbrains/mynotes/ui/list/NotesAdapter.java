package ru.geekbrains.mynotes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final ArrayList<Note> data = new ArrayList<>();
    private final Fragment fragment;
    private OnNoteClicked clickListener;
    private int longClickedPosition = -1;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setData(List<Note> toAdd) {

        NotesDiffUtilCallback callback = new NotesDiffUtilCallback(data, toAdd);

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        data.clear();
        data.addAll(toAdd);

        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = data.get(position);

        holder.title.setText(note.getTitle());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public OnNoteClicked getClickListener() {
        return clickListener;
    }

    public void setClickListener(OnNoteClicked clickListener) {
        this.clickListener = clickListener;
    }

    public int getLongClickedPosition() {
        return longClickedPosition;
    }

    public Note getItemAt(int longClickedPosition) {
        return data.get(longClickedPosition);
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    public static class NotesDiffUtilCallback extends DiffUtil.Callback {

        private final List<Note> oldList;
        private final List<Note> newList;

        public NotesDiffUtilCallback(List<Note> oldList, List<Note> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView noteText;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getClickListener() != null) {
                        getClickListener().onNoteClicked(data.get(getAdapterPosition()));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu();
                    longClickedPosition = getAdapterPosition();
                    return true;
                }
            });

            title = itemView.findViewById(R.id.title);
            noteText = itemView.findViewById(R.id.noteText);
        }
    }
}
