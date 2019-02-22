package org.httpsknighthacks.knighthacksandroid.Resources;

import java.util.ArrayList;

public interface ResponseListener<T> {
    public void onStart();
    public void onSuccess(ArrayList<T> response);
    public void onFailure();
    public void onComplete(ArrayList<T> response);
}
