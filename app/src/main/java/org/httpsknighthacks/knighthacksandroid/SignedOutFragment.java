package org.httpsknighthacks.knighthacksandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class SignedOutFragment extends Fragment {
    private CardView mScannerCardView;
    private CardView mAlternateLoginCardview;
    private OnFragmentInteractionListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_not_signed_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mScannerCardView = view.findViewById(R.id.profile_scan_qr_code_button);
        mScannerCardView.setOnClickListener(v -> {
            Intent newActivity = new Intent(view.getContext(), LiveBarcodeScanningActivity.class);
            getActivity().startActivityForResult(newActivity, Profile.LOG_IN_TYPE_REQUEST);
        });

        mAlternateLoginCardview = view.findViewById(R.id.profile_email_login_btn);
        mAlternateLoginCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCardSelected(1);
            }
        });
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener listener) {
        mListener = listener;
    }

    public interface OnFragmentInteractionListener {
        void onCardSelected(int position);
    }
}
