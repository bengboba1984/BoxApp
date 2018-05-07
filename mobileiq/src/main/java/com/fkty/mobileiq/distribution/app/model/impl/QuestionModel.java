package com.fkty.mobileiq.distribution.app.model.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.fkty.mobileiq.distribution.app.model.IQuestionModel;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank_tracy on 2018/3/17.
 */

public class QuestionModel extends BasicModel
        implements IQuestionModel, QuestionConstant, ICoreListener, INetNotify
{
    private static String TAG = QuestionModel.class.getSimpleName();
    private List<TestTypeBean> data;
    private IBasicHandler.Callback testCallback;
    private List<TestParamsBean> testDataParams;
  //  private static int count=0;

    public QuestionModel()
    {

//        CoreNotifier.getInstance().registerListener(this);
    }

    protected void handleMessage(Message paramMessage)
    {
        switch (paramMessage.what)
        {
            default:
                break;
            case TEST_START_MSG:
                WebHttpUtils.getInstance().startTest(this.testDataParams, CommonField.MODLE_TYPE_MALFUNCTION_ELIMINATION, this, TEST_START_MSG);
                break;
            case TEST_STOP_MSG:
                WebHttpUtils.getInstance().stopTest(CommonField.MODLE_TYPE_MALFUNCTION_ELIMINATION, this, TEST_STOP_MSG);
                break;
        }

    }

    public boolean onCoreMessage(String paramString)
    {
        Bundle localBundle = new Bundle();
        if (TestResult.getInstance().isNeedSave())
            TestResult.getInstance().setResult(null);
        Log.e(TAG, "onCoreMessage:");
        try
        {
            int i=3;
//            if(count<10){
//                i=1;
//            }else if(count>10 && count<20){
//                i=2;
//            }else if(count>20 && count<30){
//                i=4;
//            }
//            StringBuffer testJson=new StringBuffer("{'errorCode':0,'modleResult':[{'modelType':1,'modleState':"+i);
//            testJson.append(",'deviceSeq':200000,'parameters':[{'testName':'PING测试','testState':").append(i).append(",'testType':1,'testResult':{'avgDelay':1000,'avgJitter':567,'hostIp':'www.baidu.com','lossPercent':98}}]}],'configResult':{'BoxConfigInfo':{'ConfigType':2},'BoxDeviceInfo':{'userName':'123','deviceSeq':200000,'cpu':'28.0%','memory':'48.0%','hardDisk':'15.0%','ipAddress':'192.168.31.240','subNetMask':'255.255.255.0','gateway':'192.168.31.1','dns':'192.168.31.1'}},'url':'http://211.136.99.12:4100','capture':{'captureState':1}}");
//            Log.d(TAG,testJson.toString());
           // count++;
//            paramString=testJson;
            JSONObject localJSONObject1 = new JSONObject(paramString.toString());
            if (localJSONObject1.optInt("errorCode") == ServerErrorCode.ERROR_CODE_SUCCESS)
            {
                JSONArray localJSONArray = localJSONObject1.optJSONArray("modleResult");
                if ((localJSONArray != null) && (localJSONArray.length() >0))
                {

                    for (int j=0;j < localJSONArray.length();j++)
                    {
                        JSONObject localJSONObject2 = localJSONArray.optJSONObject(j);
                        if (localJSONObject2.optInt("modelType") == CommonField.MODLE_TYPE_MALFUNCTION_ELIMINATION){
                            localBundle.putString("message", localJSONObject2.toString());
                            this.testCallback.call(localJSONObject2.optInt("modleState"), localBundle);
                        }
                        return true;
                    }
                }
            }else if (localJSONObject1.optInt("errorCode") == ServerErrorCode.ERROR_CODE_FAILED){
                this.testCallback.call(ServerErrorCode.ERROR_CODE_FAILED, null);
            }
            return false;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return false;
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
            JSONObject localJSONObject1 = new JSONObject(paramString);
            Log.e(TAG, "onSuccessNetClient:paramInt="+paramInt+"/////" + paramString);
            i = localJSONObject1.optInt("errorCode");
            localBundle = new Bundle();
            localBundle.putString("msg", paramString);
            switch (paramInt){
                default:
                    break;
                case TEST_START_MSG:
//                    i=0;//测试，上线后去除
                    if (i == ServerErrorCode.ERROR_CODE_SUCCESS){
                        this.testCallback.onSuccess(TEST_START_SUCCESS, localBundle);
                    }else{
                        this.testCallback.onFailed(TEST_START_FAILED, localBundle);
                    }
                    break;
                case TEST_STOP_MSG:

                    if (i == ServerErrorCode.ERROR_CODE_SUCCESS){
                        this.testCallback.onSuccess(TEST_STOP_SUCCESS, localBundle);
                    }else{
                        this.testCallback.onFailed(TEST_STOP_FAILED, localBundle);
                    }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void setCallBack(IBasicHandler.Callback paramCallback)
    {
    }

    public void setCallback(IBasicHandler.Callback paramCallback)
    {
        this.testCallback = paramCallback;
    }

    public void startTest(Context paramContext, List<TestTypeBean> paramList, List<TestParamsBean> paramList1, IBasicHandler.Callback paramCallback)
    {
        if (paramList == null){
            this.data = new ArrayList();
        }else{
            this.data = paramList;
        }
        this.testDataParams = paramList1;
        Log.d(TAG," WebHttpUtils.getInstance().startTest");
        WebHttpUtils.getInstance().startTest(this.testDataParams, CommonField.MODLE_TYPE_MALFUNCTION_ELIMINATION, this, TEST_START_MSG);
    }

    public void startTest(Context paramContext, int testingIndex, List<TestParamsBean> paramList1, IBasicHandler.Callback paramCallback)
    {
//        if (paramList == null){
//            this.data = new ArrayList();
//        }else{
//            this.data = paramList;
//        }
        List<TestParamsBean>  temp= new ArrayList();
        temp.add(paramList1.get(testingIndex));
        this.testDataParams = temp;
        Log.d(TAG," WebHttpUtils.getInstance().startTest");
        WebHttpUtils.getInstance().startTest(this.testDataParams, CommonField.MODLE_TYPE_MALFUNCTION_ELIMINATION, this, TEST_START_MSG);
    }

    public void stopTest()
    {
        Log.d(TAG," WebHttpUtils.getInstance().stopTest");
        WebHttpUtils.getInstance().stopTest(CommonField.MODLE_TYPE_MALFUNCTION_ELIMINATION, this, TEST_STOP_MSG);
    }
}
