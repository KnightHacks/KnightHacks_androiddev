package org.httpsknighthacks.knighthacksandroid;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService {
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */

    public static final String TOKEN_BROADCAST = "GOKnights";

    @Override
    public void onNewToken(String token)
    {
        Log.d("myFirebaseId", "Refreshed token: " + token);

        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));

        // sendRegistrationToServer(token);
    }
}
