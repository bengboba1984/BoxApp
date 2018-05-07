package com.fkty.mobileiq.distribution.app.view;

import android.os.Bundle;

import com.fkty.mobileiq.distribution.inter.IBasicView;

/**
 * Created by frank_tracy on 2018/3/22.
 */

public abstract interface IManualView extends IBasicView
{
    public abstract void call(int paramInt, Bundle paramBundle);

    public abstract void onStartFailed(int paramInt, Bundle paramBundle);

    public abstract void onStartSuccess(int paramInt, Bundle paramBundle);

    public abstract void onStopFailed(int paramInt, Bundle paramBundle);

    public abstract void onStopSuccess(int paramInt, Bundle paramBundle);
}