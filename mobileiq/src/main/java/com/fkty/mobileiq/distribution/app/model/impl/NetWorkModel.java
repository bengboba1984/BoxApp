package com.fkty.mobileiq.distribution.app.model.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fkty.mobileiq.distribution.app.model.INetWorkModel;
import com.fkty.mobileiq.distribution.basic.BasicModel;
import com.fkty.mobileiq.distribution.bean.CaptureFileBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.NetWorkConstant;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.inter.IBasicHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by frank_tracy on 2018/4/10.
 */

public class NetWorkModel extends BasicModel
        implements INetWorkModel, INetNotify,NetWorkConstant
{
    private static String TAG = NetWorkModel.class.getSimpleName();
    private IBasicHandler.Callback callback;
    private String fileName;
    private String startFileName;
    private String filePath;
    private String localPath;

    public void upload(String fileName, IBasicHandler.Callback paramCallback)
    {
        this.callback = paramCallback;
        this.fileName = fileName;
        Handler h = getWorkHandler();
        h.sendEmptyMessage(UPLOAD_FILE_MSG);
    }

    public void startCapture(String startFileName,IBasicHandler.Callback paramCallback)
    {
        this.callback = paramCallback;
        this.startFileName = startFileName;
        Handler h = getWorkHandler();
        h.sendEmptyMessage(START_CAPTURE_MSG);
    }
    public void getCaptureFile(String filePath,String localPath, IBasicHandler.Callback paramCallback)
    {
        this.callback = paramCallback;
        this.filePath = filePath;
        this.localPath=localPath;
        Handler h = getWorkHandler();
        h.sendEmptyMessage(GET_CAPTURE_FILE);
    }
    public void stopCapture(IBasicHandler.Callback paramCallback) {
        this.callback = paramCallback;
        Handler h = getWorkHandler();
        h.sendEmptyMessage(STOP_CAPTURE_MSG);
    }

    protected void handleMessage(Message paramMessage)
    {
        switch (paramMessage.what)
        {
            default:
                break;
            case START_CAPTURE_MSG:
                WebHttpUtils.getInstance().startCapture(this.startFileName, this, START_CAPTURE_ID);
                break;
            case GET_CAPTURE_FILE:
                WebHttpUtils.getInstance().getCaptureFile(this.filePath, this.localPath,this, GET_CAPTURE_FILE_ID);
                break;
            case STOP_CAPTURE_MSG:
                WebHttpUtils.getInstance().stopCapture(this, STOP_CAPTURE_ID);
                break;
            case UPLOAD_FILE_MSG:
                CaptureFileBean localCaptureFileBean = new CaptureFileBean();
                localCaptureFileBean.setFilePath(this.fileName);
//                WebHttpUtils.getInstance().downloadFile(localCaptureFileBean, "/sdcard/vixtel/distribution", this.fileName.substring(this.fileName.lastIndexOf("/")), this, UPLOAD_FILE_ID);
                break;
        }

    }

    public void onErrorNetClient(int paramInt, String paramString)
    {
        Bundle localBundle = new Bundle();
//        localBundle.putString("msg", paramString);
//        switch (paramInt)
//        {
//            default:
//            case 21:
//            case 22:
//            case 23:
//        }
//        do
//        {
//            do
//            {
//                do
//                    return;
//                while (this.callback == null);
//                this.callback.onFailed(1, localBundle);
//                return;
//            }
//            while (this.callback == null);
//            this.callback.onFailed(4, localBundle);
//            return;
//        }
//        while (this.callback == null);
//        this.callback.onFailed(6, localBundle);
        this.callback.onFailed(STOP_CAPTURE_FAILED,localBundle);
    }

    public void onFailedNetClient(int paramInt, String paramString)
    {
    }

    public void onSuccessNetClient(int paramInt, String paramString)
    {
        Log.d(TAG,"onSuccessNetClient:paramInt="+paramInt);
        if(paramInt!=GET_CAPTURE_FILE_ID){
            Bundle localBundle = new Bundle();
            localBundle.putString("msg", paramString);
            Log.d(TAG,"onSuccessNetClient:paramString="+paramString);
            try {

                if(paramString!=null && !"null".equals(paramString)){
                    JSONObject rspm=new JSONObject(paramString);
                    int i =-1;
                    i=rspm.optInt("errorCode");
                    switch (paramInt){
                        default:
                            break;
                        case START_CAPTURE_ID:
                            if(i== ServerErrorCode.ERROR_CODE_SUCCESS){
                                this.callback.onSuccess(START_CAPTURE_SUCCESS, localBundle);
                            }else{
                                this.callback.onSuccess(START_CAPTURE_FAILED, localBundle);
                            }
                            break;
                        case UPLOAD_FILE_ID:
                            if(i== ServerErrorCode.ERROR_CODE_SUCCESS){
                                this.callback.onSuccess(UPLOAD_FILE_SUCCESS, localBundle);
                            }else{
                                this.callback.onSuccess(UPLOAD_FILE_FAILED, localBundle);
                            }
                            break;
                        case STOP_CAPTURE_ID:
                            if(i== ServerErrorCode.ERROR_CODE_SUCCESS){
                                this.callback.onSuccess(STOP_CAPTURE_SUCCESS, localBundle);
                            }else{
                                this.callback.onSuccess(STOP_CAPTURE_FAILED, localBundle);
                            }
                            break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            this.callback.onSuccess(GET_CAPTURE_FILE_SUCCESS, new Bundle());
        }

    }


}