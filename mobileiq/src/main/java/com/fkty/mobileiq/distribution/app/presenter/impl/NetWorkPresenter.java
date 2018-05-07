package com.fkty.mobileiq.distribution.app.presenter.impl;

import android.os.Bundle;
import android.os.Message;

import com.fkty.mobileiq.distribution.app.model.INetWorkModel;
import com.fkty.mobileiq.distribution.app.model.impl.NetWorkModel;
import com.fkty.mobileiq.distribution.app.presenter.INetWorkPresenter;
import com.fkty.mobileiq.distribution.app.view.INetWorkView;
import com.fkty.mobileiq.distribution.basic.BasicPresenter;
import com.fkty.mobileiq.distribution.constant.NetWorkConstant;
import com.fkty.mobileiq.distribution.inter.IBasicHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by frank_tracy on 2018/4/10.
 */

public class NetWorkPresenter extends BasicPresenter<INetWorkView, INetWorkModel> implements INetWorkPresenter,NetWorkConstant {
//    private static NetWorkPresenter presenter;
    private INetWorkModel mBasicModel;
    private INetWorkView mBasicView;
    private IBasicHandler.Callback callback = new IBasicHandler.Callback()
    {
        public void call(int paramInt, Bundle paramBundle)
        {
        }

        public void onError(int paramInt, Bundle paramBundle)
        {
        }

        public void onFailed(int paramInt, Bundle paramBundle)
        {
            Message.obtain(NetWorkPresenter.this.getHandler(), paramInt, paramBundle).sendToTarget();
        }

        public void onSuccess(int paramInt, Bundle paramBundle)
        {
            Message.obtain(NetWorkPresenter.this.getHandler(), paramInt, paramBundle).sendToTarget();
        }
    };

    public NetWorkPresenter(INetWorkView paramINetWorkView)
    {
        super(paramINetWorkView, new NetWorkModel(), false);
        this.mBasicView = paramINetWorkView;
        this.mBasicModel = getBasicModel();
    }
    @Override
    public void start(String paramString) {
        this.mBasicModel.startCapture(paramString,this.callback);
    }

    @Override
    public void getCaptureFile(String filePath,String localPath) {
        this.mBasicModel.getCaptureFile(filePath,localPath, this.callback);
    }

    @Override
    public void stop() {
        this.mBasicModel.stopCapture(this.callback);
    }

    protected void handleMessage(Message paramMessage)
    {
        super.handleMessage(paramMessage);
        switch (paramMessage.what)
        {
            default:
                break;
            case START_CAPTURE_SUCCESS:
                if (this.mBasicView != null)
                    this.mBasicView.onStartSuccess(START_CAPTURE_SUCCESS, (Bundle)paramMessage.obj);
                break;
            case START_CAPTURE_FAILED:
                if (this.mBasicView != null)
                    this.mBasicView.onStartFailed(START_CAPTURE_FAILED, (Bundle)paramMessage.obj);
                break;
            case UPLOAD_FILE_SUCCESS:
                break;
            case UPLOAD_FILE_FAILED:
                break;
            case STOP_CAPTURE_SUCCESS:
                if (this.mBasicView != null){
                    JSONObject localJSONObject1;
                    try {
                        localJSONObject1 = new JSONObject(((Bundle)paramMessage.obj).getString("msg"));
                        this.mBasicView.onStopSuccess(STOP_CAPTURE_SUCCESS, localJSONObject1.optString("path"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case STOP_CAPTURE_FAILED:
                if (this.mBasicView != null)
                    this.mBasicView.onStopFailed(STOP_CAPTURE_FAILED, (Bundle)paramMessage.obj);
                break;
            case GET_CAPTURE_FILE_SUCCESS:
                if (this.mBasicView != null)
                    this.mBasicView.onGetCaptureFileSuccess(GET_CAPTURE_FILE_SUCCESS);
                break;
        }
    }
}
