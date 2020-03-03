package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.AdministrativeFields;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class AdministrativeFieldsTask {
    public static final String ADMINISTRATIVE_FIELDS_COLLECTION = "administrative_fields";

    private DatabaseReference mReference;
    private WeakReference<Context> mContext;
    private ResponseListener mListener;

    public interface ResponseListener {
        void onSuccess();
        void onFailure();
    }

    public AdministrativeFieldsTask(Context context, ResponseListener listener) {
        mContext = new WeakReference<>(context);
        mReference = FirebaseDatabase.getInstance().getReference();
        mListener = listener;
    }

    public void retrieveAdministrativeFields() {
        SharedPreferences pref = mContext.get().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        mReference.child(ADMINISTRATIVE_FIELDS_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot adminFields : dataSnapshot.getChildren()) {
                    String publicUuid = adminFields.child("publicUuid").getValue(String.class);

                    //AdministrativeFields aFields = adminFields.getValue(AdministrativeFields.class);
                    if (Objects.requireNonNull(pref.getString("publicUuid", ""))
                            .equals(publicUuid)) {

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("pointsCount", adminFields.child("pointsCount").getValue(Integer.class));
                        editor.putString("pointsGroup", adminFields.child("pointsGroup").getValue(String.class));
                        editor.putString("foodGroup", adminFields.child("foodGroup").getValue(String.class));
                        editor.putString("hackerKey", adminFields.getKey());
                        editor.commit();
                        mListener.onSuccess();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
