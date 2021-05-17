package ru.geekbrains.mynotes.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import ru.geekbrains.mynotes.R;
import ru.geekbrains.mynotes.router.AppRouter;
import ru.geekbrains.mynotes.router.RouterHolder;


public class AuthFragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private GoogleSignInClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(requireContext(), options);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = client.getSignInIntent();

                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {

            Task<GoogleSignInAccount> result = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = result.getResult(ApiException.class);

                if (getActivity() instanceof RouterHolder) {
                    AppRouter router = ((RouterHolder) getActivity()).getRouter();

                    router.showNotesList();
                }

            } catch (ApiException e) {
                Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}