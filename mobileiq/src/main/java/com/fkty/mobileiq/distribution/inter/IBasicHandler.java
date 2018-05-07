package com.fkty.mobileiq.distribution.inter;

import android.os.Bundle;

/**
 * Created by frank_tracy on 2017/12/5.
 */

public interface IBasicHandler {
    Object getData(int paramInt, Bundle paramBundle);

     void handleTask(Callback paramCallback, int paramInt, Bundle paramBundle);

     interface Callback
    {
         void call(int paramInt, Bundle paramBundle);

         void onError(int paramInt, Bundle paramBundle);

         void onFailed(int paramInt, Bundle paramBundle);

         void onSuccess(int paramInt, Bundle paramBundle);
    }
}
