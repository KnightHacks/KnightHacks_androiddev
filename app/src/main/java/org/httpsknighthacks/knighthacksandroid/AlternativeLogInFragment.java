package org.httpsknighthacks.knighthacksandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.httpsknighthacks.knighthacksandroid.Tasks.LoginTask;

public class AlternativeLogInFragment extends Fragment {
    private Button mLoginButton;
    private EditText mInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mLoginButton = view.findViewById(R.id.email_login_btn);
        mInput = view.findViewById(R.id.plain_text_input);

        mLoginButton.setOnClickListener(v -> {
            LoginTask loginTask = new LoginTask(getActivity());
            loginTask.execute(mInput.getText().toString());
        });
    }
}
