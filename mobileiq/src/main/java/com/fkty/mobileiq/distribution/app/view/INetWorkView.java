package com.fkty.mobileiq.distribution.app.view;

import android.os.Bundle;

import com.fkty.mobileiq.distribution.inter.IBasicView;

/**
 * Created by frank_tracy on 2018/4/3.
 */

public interface INetWorkView extends IBasicView
{
    void onStartFailed(int paramInt, Bundle paramBundle);

    void onStartSuccess(int paramInt, Bundle paramBundle);

    void onStopFailed(int paramInt, Bundle paramBundle);

    void onStopSuccess(int paramInt, String paramString);

    void onGetCaptureFileSuccess(int paramInt);
}
