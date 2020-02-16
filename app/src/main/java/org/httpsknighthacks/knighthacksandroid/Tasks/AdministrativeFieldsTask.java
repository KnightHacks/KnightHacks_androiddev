package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.AdministrativeFields;
import org.httpsknighthacks.knighthacksandroid.SignedInFragment;

import java.lang.ref.WeakReference;

public class AdministrativeFieldsTask {
    public static final String ADMINISTRATIVE_FIELDS_COLLECTION = "administrative_fields";

    private DatabaseReference mReference;
    private WeakReference<Context> mContext;
    private StringResponseListener mListener;

    public interface StringResponseListener {
        void onSuccess(String value);
        void onFailure();
    }

    public AdministrativeFieldsTask(Context context, StringResponseListener listener) {
        mContext = new WeakReference<>(context);
        mReference = FirebaseDatabase.getInstance().getReference();
        mListener = listener;
    }

    public void retrieveAdministrativeFields() {
        mReference.child(ADMINISTRATIVE_FIELDS_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences mPref = mContext.get().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                for (DataSnapshot adminFields : dataSnapshot.getChildren()) {
                    AdministrativeFields aFields = adminFields.getValue(AdministrativeFields.class);

                    if (mListener instanceof SignedInFragment) {
                        mListener.onSuccess(String.valueOf(aFields.getPointCount()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
