package com.fkty.mobileiq.distribution.http;

import android.util.Log;

import com.fkty.mobileiq.distribution.bean.LoginInfo;
import com.fkty.mobileiq.distribution.constant.HttpConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;

import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.fkty.mobileiq.distribution.http.HttpUtil.NetFunctionID.GET_FOREGIN_SERVER;

/**
 * Created by frank_tracy on 2017/12/18.
 */

public class HttpUtil implements Runnable {
    private static final String TAG = HttpUtil.class.getSimpleName();
    private static HttpUtil httpUtil;
    private List<File> files;
    private int id;
    private LoginInfo loginInfo;
    private String loginMsg = "";
    private INetNotify notify;
    private Thread thread;

    public static HttpUtil getInstance(){
        if(httpUtil==null){
            httpUtil=new HttpUtil();
        }
        return httpUtil;
    }



    public boolean getForeginServer(int paramInt, INetNotify paramINetNotify)
    {
        if (this.thread != null)
        {
            if (this.thread.isAlive())
                return false;
            this.thread = null;
        }
        this.id = paramInt;
        this.notify = paramINetNotify;
        this.thread = new Thread(this, GET_FOREGIN_SERVER);
        this.thread.start();
        return true;
    }

    public void getForeginServerTask()
    {
        Log.d(this.getClass().getName(),"start getForeginServerTask=============");
        try
        {
            Response localResponse = OkHttpUtils.get().url(HttpConstant.HTTP_GET_FOREIGN).id(this.id).build().connTimeOut(5000).execute();
            this.notify.onSuccessNetClient(this.id, localResponse.body().string());
        }
        catch (IOException localIOException)
        {
            Log.d(this.getClass().getName(),"IOException =============");
            localIOException.printStackTrace();
            this.notify.onFailedNetClient(this.id, localIOException.getMessage());
        }
    }

    public  void run(){
        String str = Thread.currentThread().getName();
        if (str.equals(GET_FOREGIN_SERVER)){
            getForeginServerTask();
        }
    }
    public static class NetFunctionID
    {
        public static final String GET_FOREGIN_SERVER = "get_foregin_server";
        public static final String UPLOAD_FILE = "upload_file";
        public static final String USER_LOGIN_USERNAME_ID = "THREAD_USER_LOGIN_USERNAME";
    }
}
