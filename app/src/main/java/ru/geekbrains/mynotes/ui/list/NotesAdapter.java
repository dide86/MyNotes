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

    private static final int NOTE = 0;
    private static final int HEADER = 1;
    private final ArrayList<AdapterItem> data = new ArrayList<AdapterItem>();
    private final Fragment fragment;
    private OnNoteClicked clickListener;
    private int longClickedPosition = -1;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setData(List<AdapterItem> toAdd) {

        NotesDiffUtilCallback callback = new NotesDiffUtilCallback(data, toAdd);

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        data.clear();
        data.addAll(toAdd);

        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof HeaderItem) {
            return HEADER;
        }

        if (data.get(position) instanceof NoteItem) {
            return NOTE;
        }

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        AdapterItem item = (AdapterItem) data.get(position);

        if (holder instanceof NotesViewHolder) {

            NotesViewHolder holder1 = (NotesViewHolder) holder;
            Note note = ((NoteItem) item).getNote();

            holder1.title.setText(note.getTitle());

//            Glide.with(holder1.image)
//                    .load(note.getImageUrl())
//                    .centerCrop()
//                    .into(holder1.image);

        }
//
//        if (holder instanceof HeaderViewHolder) {
//
//            HeaderViewHolder holder1 = (HeaderViewHolder) holder;
//            String title = ((HeaderItem) item).getTitle();
//
//            holder1.title.setText(title);
//        }
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

    public int getSpanSize(int position) {
        if (data.get(position) instanceof HeaderItem) {
            return 2;
        }
        return 1;
    }

    public int getLongClickedPosition() {
        return longClickedPosition;
    }

    public Note getItemAt(int longClickedPosition) {
        return ((NoteItem) data.get(longClickedPosition)).getNote();
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    public static class NotesDiffUtilCallback extends DiffUtil.Callback {

        private final List<AdapterItem> oldList;
        private final List<AdapterItem> newList;

        public NotesDiffUtilCallback(ArrayList<AdapterItem> oldList, List<AdapterItem> newList) {
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
            return oldList.get(oldItemPosition).getUniqueTag().equals(newList.get(newItemPosition).getUniqueTag());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
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
                        getClickListener().onNoteClicked(((NoteItem) data.get(getAdapterPosition())).getNote());
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
