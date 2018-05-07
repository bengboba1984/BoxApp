package com.fkty.mobileiq.distribution.app.model.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.fkty.mobileiq.distribution.app.model.IManualModel;
import com.fkty.mobileiq.distribution.basic.BasicModel;
import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.QuestionConstant;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.core.CoreNotifier;
import com.fkty.mobileiq.distribution.core.ICoreListener;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.inter.IBasicHandler;
import com.fkty.mobileiq.distribution.result.TestResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by frank_tracy on 2018/3/26.
 */

public class ManualModel extends BasicModel
        implements IManualModel, QuestionConstant, ICoreListener, INetNotify
{
    private static String TAG = ManualModel.class.getSimpleName();
    private IBasicHandler.Callback testCallback;
    private List<TestParamsBean> testDataParams;

    protected void handleMessage(Message paramMessage)
    {
//        switch (paramMessage.what)
//        {
//            default:
//                break;
//            case TEST_START_MSG:
//                WebHttpUtils.getInstance().startTest(this.testDataParams, 3, this, TEST_START_MSG);
//                break;
//            case TEST_STOP_MSG:
//                WebHttpUtils.getInstance().stopTest(3, this, TEST_STOP_MSG);
//                break;
//        }

    }

    public boolean onCoreMessage(String paramString)
    {
        Bundle localBundle = new Bundle();
        if (TestResult.getInstance().isNeedSave())
            TestResult.getInstance().setResult(null);
        return true;
//        Log.e(TAG, "onCoreMessage:" + paramString);
//        while (true)
//        {
//            int i;
//            try
//            {
//                JSONObject localJSONObject1 = new JSONObject(paramString);
//                if (localJSONObject1.optInt("errorCode") != 0)
//                    continue;
//                JSONArray localJSONArray = localJSONObject1.optJSONArray("modleResult");
//                if ((localJSONArray != null) && (localJSONArray.length() > 0))
//                {
//                    i = 0;
//                    if (i < localJSONArray.length())
//                    {
//                        JSONObject localJSONObject2 = localJSONArray.optJSONObject(i);
//                        if (localJSONObject2.optInt("modelType") != 3)
//                            break label185;
//                        localBundle.putString("message", localJSONObject2.toString());
//                        this.testCallback.call(localJSONObject2.optInt("modleState"), localBundle);
//                        return false;
//                        if (localJSONObject1.optInt("errorCode") == -100)
//                        {
//                            this.testCallback.call(-100, null);
//                            return false;
//                        }
//                    }
//                }
//            }
//            catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//            return false;
//            label185: i++;
//        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        CoreNotifier.getInstance().removeListener(this);
    }

    public void onErrorNetClient(int paramInt, String paramString)
    {
        Log.e(TAG, "onErrorNetClient:" + paramString);
        Bundle localBundle = new Bundle();
        localBundle.putString("msg", paramString);
        switch (paramInt)
        {
            default:
                break;
            case TEST_START_MSG:
                this.testCallback.onFailed(TEST_START_FAILED, localBundle);
                break;
            case TEST_STOP_MSG:
                this.testCallback.onFailed(TEST_STOP_FAILED, localBundle);
                break;
        }

    }

    public void onFailedNetClient(int paramInt, String paramString)
    {
    }

    public void onSuccessNetClient(int paramInt, String paramString)
    {
        int i;
        Bundle localBundle;
        try
        {
            JSONObject resultJson = new JSONObject(paramString);
            Log.e(TAG, "onSuccessNetClient:" + paramString);
            i = resultJson.optInt("errorCode");
            localBundle = new Bundle();
            localBundle.putString("msg", paramString);
            switch (paramInt)
            {
                default:
                    break;
                case TEST_START_MSG:
                    if (i == ServerErrorCode.ERROR_CODE_SUCCESS){
                        this.testCallback.onSuccess(TEST_START_SUCCESS, localBundle);
//                        CoreNotifier.getInstance().registerListener(this);
                    }else{
                        this.testCallback.onFailed(TEST_START_FAILED, localBundle);
                    }
                    break;
                case TEST_STOP_MSG:
                    if (i == ServerErrorCode.ERROR_CODE_SUCCESS){
                        this.testCallback.onSuccess(TEST_STOP_SUCCESS, localBundle);
//                        CoreNotifier.getInstance().removeListener(this);
                    }else{
                        this.testCallback.onFailed(TEST_STOP_FAILED, localBundle);
                    }
                    break;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void startTest(Context paramContext, List<TestTypeBean> paramList, List<TestParamsBean> paramList1, IBasicHandler.Callback paramCallback)
    {
        this.testCallback = paramCallback;
        this.testDataParams = paramList1;
        WebHttpUtils.getInstance().startTest(this.testDataParams, CommonField.MODLE_TYPE_MANUAL_TEST, this, TEST_START_MSG);
    }

    public void stopTest()
    {
        WebHttpUtils.getInstance().stopTest(CommonField.MODLE_TYPE_MANUAL_TEST, this, TEST_STOP_MSG);
    }
}