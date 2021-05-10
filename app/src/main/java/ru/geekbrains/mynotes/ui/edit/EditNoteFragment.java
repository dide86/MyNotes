package ru.geekbrains.mynotes.ui.edit;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.domain.Note;

public class EditNoteFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";

    public static EditNoteFragment newInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       EditText editName = view.findViewById(R.id.edit_name);

       editName.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {

               Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();

           }
       });

        DatePicker picker = view.findViewById(R.id.date_picker);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Toast.makeText(requireContext(), year + " " + monthOfYear + " " + dayOfMonth, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
