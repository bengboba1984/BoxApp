package com.fkty.mobileiq.distribution.app.model;

import android.content.Context;

import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.inter.IBasicHandler;
import com.fkty.mobileiq.distribution.inter.IBasicModel;

import java.util.List;

/**
 * Created by frank_tracy on 2018/3/17.
 */

public interface IQuestionModel extends IBasicModel
{
    void startTest(Context paramContext, List<TestTypeBean> paramList, List<TestParamsBean> paramList1, IBasicHandler.Callback paramCallback);

    void stopTest();
}
