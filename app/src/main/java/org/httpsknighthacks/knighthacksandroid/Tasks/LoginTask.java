package org.httpsknighthacks.knighthacksandroid.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginTask extends AsyncTask<String, Void, Void> {
    private WeakReference<Context> mContext;
    private SharedPreferences mPref;

    public interface ResponseListener {
        void onSuccess();
        void onFailure();
    }

    public LoginTask(Context context) {
        mContext = new WeakReference<>(context);
        mPref = mContext.get().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL("https://us-central1-prackn-bad9b.cloudfunctions.net/loginHacker");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("publicUuid", strings[0]);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(jsonParam.toString());
            dStream.flush();
            dStream.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseOutput = new StringBuilder();

            while ((line = br.readLine()) != null) {
                responseOutput.append(line);
            }
            Log.d("KEVIN", "Response: " + responseOutput.toString());
            br.close();
            setAuthCode(parseResponse(responseOutput.toString()));
            setUuid(strings[0]);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (mPref.contains("authCode")) {
            ((ResponseListener) mContext.get()).onSuccess();
        }

        else {
            ((ResponseListener) mContext.get()).onFailure();
        }
    }

    private static String parseResponse(String response) throws JSONException {
        JSONObject jObject = new JSONObject(response);
        String uuid = jObject.getString("authCode");
        return uuid;
    }

    private void setAuthCode(String authCode) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("authCode", authCode);
        editor.apply();
    }

    private void setUuid(String uuid) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("publicUuid", uuid);
        editor.apply();
    }
}