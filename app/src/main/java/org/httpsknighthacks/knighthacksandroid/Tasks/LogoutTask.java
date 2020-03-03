package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LogoutTask extends AsyncTask<String, Void, Void> {
    private WeakReference<Context> mContext;

    public interface OnLogoutListener {
        void onLogoutSuccess();
        void onLogoutFailure();
    }

    public LogoutTask(Context context) {
        mContext = new WeakReference<>(context);
    }
    
    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL("https://us-central1-prackn-bad9b.cloudfunctions.net/logoutHacker");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setDoOutput(true);

            SharedPreferences pref = mContext.get().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("publicUuid", getUuid(pref));
            jsonParam.put("authCode", getAuthCode(pref));
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(jsonParam.toString());
            dStream.flush();
            dStream.close();

            SharedPreferences.Editor editor = pref.edit();
            editor.remove("publicUuid");
            editor.remove("authCode");
            editor.clear();
            editor.commit();

            if (connection.getResponseCode() != 200) {
                ((OnLogoutListener) mContext.get()).onLogoutFailure();
                return null;
            }

            ((OnLogoutListener) mContext.get()).onLogoutSuccess();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAuthCode(SharedPreferences pref) {
        return pref.getString("authCode", null);
    }

    private String getUuid(SharedPreferences pref) {
        return pref.getString("publicUuid", null);
    }
}
