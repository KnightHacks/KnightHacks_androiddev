package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class Profile extends AppCompatActivity {
    SharedPreferences mSharedPreferences;


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
        ft.add(R.id.profileFragments, mSharedPreferences.contains("authCode") ? new SignedInFragment() : new SignedOutFragment());
        ft.commit();
    }

    private void replaceFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.profileFragments, mSharedPreferences.contains("authCode") ? new SignedInFragment() : new SignedOutFragment());
        ft.commit();
    }
}
