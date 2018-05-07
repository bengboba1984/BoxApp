package com.fkty.mobileiq.distribution.inter;

import android.os.Bundle;

/**
 * Created by frank_tracy on 2017/12/5.
 */

public interface IBasicView extends IBasicHandler {
    void controlDialog(int paramInt, String paramString);

    void onDataUpdate(Bundle paramBundle);

    void toastMsg(String paramString);
}
