package com.fkty.mobileiq.distribution;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.fkty.mobileiq.distribution.common.SystemManager;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.core.CoreNotifier;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.manager.OTTProperty;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.fkty.mobileiq.distribution.constant.NetWorkConstant.CAPTURE_HTTP_CODE;

/**
 * Created by frank_tracy on 2017/12/12.
 */

public class DistributedMobileIQApplication extends Application {
    public static String APP_NAME="DistributedMobileIQ";
    private static DistributedMobileIQApplication instance=null;
    public static boolean isDebug = false;

    public static DistributedMobileIQApplication getInstance()
    {
        return instance;
    }

    public void onCreate()
    {
        super.onCreate();
        instance = this;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(300000L, TimeUnit.MILLISECONDS)
                .readTimeout(300000L, TimeUnit.MILLISECONDS)
                .writeTimeout(300000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
//        OkHttpUtils.getInstance().getOkHttpClient().connectTimeoutMillis().readTimeoutMillis()
        SystemManager.getInstance().initialize(this);
        CoreNotifier.getInstance().registerListener(OTTProperty.getInstance());
//        DataManager.getInstance().setUrl(CommonField.DEFAULT_PLATEFORM_URL);


    }

    public void onTerminate()
    {
        super.onTerminate();
        CoreNotifier.getInstance().removeListener(OTTProperty.getInstance());
    }
}
