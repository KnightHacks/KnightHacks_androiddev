package org.httpsknighthacks.knighthacksandroid.Resources;

import java.util.ArrayList;

public interface ResponseListener<T> {
    void onStart();
    void onSuccess(ArrayList<T> response);
    void onFailure();
}
