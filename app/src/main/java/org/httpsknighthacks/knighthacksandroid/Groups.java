package org.httpsknighthacks.knighthacksandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.httpsknighthacks.knighthacksandroid.Tasks.AdministrativeFieldsTask;

public class Groups extends AppCompatActivity implements AdministrativeFieldsTask.ResponseListener {
    TextView mFoodGroupTextView;
    TextView mPointsGroupTextView;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        mFoodGroupTextView = findViewById(R.id.foodGroupText);
        mPointsGroupTextView = findViewById(R.id.pointsGroupText);

        mPref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        if (!mPref.contains("foodGroup") && !mPref.contains("pointsGroup"))
            getGroupsInfo();

        else {
            mFoodGroupTextView.setText(mPref.getString("foodGroup", "Information unavailable."));
            mPointsGroupTextView.setText(mPref.getString("pointsGroup", "Information unavailable."));
        }
    }

    private void getGroupsInfo() {
        AdministrativeFieldsTask administrativeFieldsTask = new AdministrativeFieldsTask(this, this);
        administrativeFieldsTask.retrieveAdministrativeFields();
    }

    @Override
    public void onSuccess() {
        mFoodGroupTextView.setText(mPref.getString("foodGroup", "Information unavailable."));
        mPointsGroupTextView.setText(mPref.getString("pointsGroup", "Information unavailable."));
    }

    @Override
    public void onFailure() {

    }
}
