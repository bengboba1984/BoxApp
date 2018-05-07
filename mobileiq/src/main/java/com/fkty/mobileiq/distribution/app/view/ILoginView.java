package com.fkty.mobileiq.distribution.app.view;

import android.os.Bundle;

import com.fkty.mobileiq.distribution.bean.LoginInfo;
import com.fkty.mobileiq.distribution.inter.IBasicView;

/**
 * Created by frank_tracy on 2017/12/20.
 */

public interface ILoginView extends IBasicView {
    LoginInfo getLoginInfo();

    void onGetForeginFailed(int paramInt, Bundle paramBundle);

    void onGetForeginSuccess(Bundle paramBundle);

    void onLoginFailed(int paramInt, Bundle paramBundle);

    void onLoginSuccess(Bundle paramBundle);
}
