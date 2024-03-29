package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.httpsknighthacks.knighthacksandroid.Models.Sponsor;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class SponsorsTask {
    public static final String SPONSORS_COLLECTION = "sponsors";

    private WeakReference<Context> mContext;
    private ResponseListener<Sponsor> mResponseListener;

    private DatabaseReference mReference;

    public SponsorsTask(Context context, ResponseListener<Sponsor> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mResponseListener = responseListener;
        mReference = FirebaseDatabase.getInstance().getReference();
    }
    public void retrieveSponsors() {
        showLoading();
        mReference.child(SPONSORS_COLLECTION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Sponsor> sponsors = new ArrayList<>();
                for (DataSnapshot workshopDataSnapshot : dataSnapshot.getChildren()) {
                    Sponsor sponsor = workshopDataSnapshot.getValue(Sponsor.class);
                    sponsors.add(sponsor);
                }
                mResponseListener.onSuccess(sponsors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mResponseListener.onFailure();
            }
        });
    }
    private void showLoading() {
        mResponseListener.onStart();
    }
    public Context getContext() {
        return mContext.get();
    }
}