package org.httpsknighthacks.knighthacksandroid.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.httpsknighthacks.knighthacksandroid.R;

public class SignedInFragment extends Fragment {

    public SignedInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signed_in, container, false);
    }

}
