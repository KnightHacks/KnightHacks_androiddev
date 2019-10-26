package org.httpsknighthacks.knighthacksandroid.Tasks;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import org.httpsknighthacks.knighthacksandroid.Models.Sponsor;
import org.httpsknighthacks.knighthacksandroid.Models.Workshop;
import org.httpsknighthacks.knighthacksandroid.Resources.RequestQueueSingleton;
import org.httpsknighthacks.knighthacksandroid.Resources.ResponseListener;
import org.json.JSONArray;
import org.json.JSONException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
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