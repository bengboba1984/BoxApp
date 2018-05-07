package com.fkty.mobileiq.distribution.app.presenter;

import android.content.Context;

import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.inter.IBasicPresenter;

import java.util.List;

/**
 * Created by frank_tracy on 2018/3/26.
 */

public interface IManualPresenter extends IBasicPresenter
{
    void startTest(Context paramContext, List<TestTypeBean> paramList, List<TestParamsBean> paramList1);

    void stopTest();
}
