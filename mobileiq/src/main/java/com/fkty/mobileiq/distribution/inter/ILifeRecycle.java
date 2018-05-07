package com.fkty.mobileiq.distribution.inter;

import android.os.Bundle;

/**
 * Created by frank_tracy on 2017/12/20.
 */

public interface ILifeRecycle {
    void onCreate(Bundle paramBundle);

    void onDestroy();

    void onPause();

    void onResume();

    void onStart();

    void onStop();

    enum LifeStatus
    {
        ON_CREATE(0),ON_START(1),ON_RESUME(2),ON_PAUSE(3),ON_STOP(4),ON_DESTROY(5);

        public final int value;
        LifeStatus(int paramInt)
        {
            this.value = paramInt;
        }
    }

}
