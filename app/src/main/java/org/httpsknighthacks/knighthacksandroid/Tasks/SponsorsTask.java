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
    private FirebaseFirestore mFirestore;
    public SponsorsTask(Context context, ResponseListener<Sponsor> responseListener) {
        this.mContext = new WeakReference<>(context);
        this.mResponseListener = responseListener;
        mFirestore = FirebaseFirestore.getInstance();
    }
    public void retrieveSponsors() {
        showLoading();
        mFirestore.collection(SPONSORS_COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Sponsor> sponsors = new ArrayList<>();
                        if (task.isSuccessful()) {
                            sponsors = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                sponsors.add(document.toObject(Sponsor.class));
                            }
                            mResponseListener.onSuccess(sponsors);
                        } else {
                            mResponseListener.onFailure();
                        }
                        mResponseListener.onComplete(sponsors);
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