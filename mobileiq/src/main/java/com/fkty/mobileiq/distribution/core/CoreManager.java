package com.fkty.mobileiq.distribution.core;

import android.util.Log;

import com.fkty.mobileiq.distribution.constant.WebServerConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;

/**
 * Created by frank_tracy on 2018/3/8.
 */

public class CoreManager implements Runnable {
    private static CoreManager task;
    private int failSize = 0;
    private ScheduledExecutorService service = null;

    public static CoreManager getInstance()
    {
        if (task == null)
            task = new CoreManager();
        return task;
    }
    @Override
    public void run() {
       // Log.d("CoreManager","start run");
        ((GetBuilder)OkHttpUtils.get().url(WebServerConstant.WEB_GET_TESTRESULT)).build().execute(new StringCallback()
        {
            public void onError(Call paramCall, Exception paramException, int paramInt)
            {
//                Log.d("CoreManager","onError:2222222222");
                failSize++;
                if (CoreManager.this.failSize >= 5)
                {
                    CoreNotifier.getInstance().notify("{\"errorCode\":-100}");
                }
            }

            public void onResponse(String paramString, int paramInt)
            {
//                Log.d("CoreManager","onResponse:1111111111111");
                CoreNotifier.getInstance().notify(paramString);
            }
        });
    }

    public void start()
    {
        if (this.service == null)
        {
            this.service = Executors.newSingleThreadScheduledExecutor();
            if (this.service != null)
                this.service.scheduleWithFixedDelay(this, 0L, 500L, TimeUnit.MILLISECONDS);
        }
    }
}
