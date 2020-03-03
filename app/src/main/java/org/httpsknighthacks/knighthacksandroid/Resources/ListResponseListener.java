package org.httpsknighthacks.knighthacksandroid.Resources;

import java.util.ArrayList;

public interface ListResponseListener<T> {
    void onStart();
    void onSuccess(ArrayList<T> response);
    void onFailure();
}
