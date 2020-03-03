package org.httpsknighthacks.knighthacksandroid;


import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import org.httpsknighthacks.knighthacksandroid.Tasks.LoginTask;
import org.httpsknighthacks.knighthacksandroid.Tasks.LogoutTask;

public class Profile extends AppCompatActivity implements LogoutBottomSheet.BottomSheetListener,
        LogoutTask.OnLogoutListener, LoginTask.ResponseListener, SignedOutFragment.OnFragmentInteractionListener {
    public static final int LOG_IN_TYPE_REQUEST = 1;
    private SharedPreferences mSharedPreferences;
    private LogInStatus logInStatus;

    @Override
    public void onCardSelected(int position) {
        switch (position) {
            case 0:
                break;

            case 1:
                logInStatus = LogInStatus.ALTERNATE_LOG_IN;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.profileFragments, new AlternativeLogInFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }

    public enum LogInStatus {
        LOGGED_IN,
        NOT_LOGGED_IN,
        ALTERNATE_LOG_IN
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        addFragment();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        replaceFragment();
    }

    private void addFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (mSharedPreferences.contains("authCode")) {
            ft.add(R.id.profileFragments, new SignedInFragment());
            logInStatus = LogInStatus.LOGGED_IN;
        }

        else {
            SignedOutFragment signedOutFragment = new SignedOutFragment();
            signedOutFragment.setOnFragmentInteractionListener(this);
            ft.add(R.id.profileFragments, signedOutFragment);

            logInStatus = LogInStatus.NOT_LOGGED_IN;
        }

        ft.commit();
    }

    private void replaceFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (logInStatus) {
            case LOGGED_IN:
                ft.replace(R.id.profileFragments, new SignedInFragment());
                break;

            case NOT_LOGGED_IN:
                SignedOutFragment signedOutFragment = new SignedOutFragment();
                signedOutFragment.setOnFragmentInteractionListener(this);
                ft.replace(R.id.profileFragments, signedOutFragment);
                break;

            case ALTERNATE_LOG_IN:
                ft.replace(R.id.profileFragments, new AlternativeLogInFragment());
                break;
        }
        ft.commit();
    }

    @Override
    public void onButtonClicked() {
        LogoutTask logoutTask = new LogoutTask(this);
        logoutTask.execute();
    }

    @Override
    public void onSuccess() {
        logInStatus = LogInStatus.LOGGED_IN;
        replaceFragment();
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onLogoutSuccess() {
        finish();
    }

    @Override
    public void onLogoutFailure() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOG_IN_TYPE_REQUEST) {
            if (resultCode == RESULT_OK && mSharedPreferences.contains("authCode")) {
                logInStatus = LogInStatus.LOGGED_IN;
            }

            else if (resultCode == RESULT_OK && !mSharedPreferences.contains("authCode")) {
                logInStatus = LogInStatus.ALTERNATE_LOG_IN;
            }

            else if (resultCode == RESULT_CANCELED) {
                logInStatus = LogInStatus.NOT_LOGGED_IN;
            }
        }
    }
}
