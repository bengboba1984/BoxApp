package com.fkty.mobileiq.distribution.basic;

import android.os.Bundle;
import android.os.Message;

import com.fkty.mobileiq.distribution.inter.IBasicHandler;
import com.fkty.mobileiq.distribution.inter.IBasicModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by frank_tracy on 2018/3/13.
 */

public abstract class BasicModel
        implements IBasicModel
{
    public static final int CACHED_THREAD = 0;
    public static final int MAIN_THREAD = -1;
    public static final int SINGLE_THREAD = 1;
    private static final String TAG = "BasicModel";
    private boolean isDestroyed = false;
    private ExecutorService mExecutorService;
    private Handler mUIHandler;
    private Handler mWorkHandler;
    private IBasicHandler.Callback mWorkingCallback;

    public BasicModel()
    {
        this(CACHED_THREAD);
    }

    public BasicModel(int paramInt)
    {
        this.mWorkHandler = new Handler();
        this.mUIHandler = new UIHandler();

        if (paramInt == CACHED_THREAD){
            this.mExecutorService = Executors.newCachedThreadPool();
        }
        if (paramInt == MAIN_THREAD) {
            this.mExecutorService = Executors.newFixedThreadPool(paramInt);
        }
        if (paramInt == SINGLE_THREAD){
                this.mExecutorService = Executors.newSingleThreadExecutor();
        }


    }

    public Object getData(int paramInt, Bundle paramBundle)
    {
        return null;
    }

    protected Handler getUIHandler()
    {
        return this.mUIHandler;
    }

    protected Handler getWorkHandler()
    {
        return this.mWorkHandler;
    }

    protected IBasicHandler.Callback getWorkingCallback()
    {
        return this.mWorkingCallback;
    }

    protected abstract void handleMessage(Message paramMessage);

    protected void handleMessageOnUIThread(Message paramMessage)
    {
    }

    public void handleTask(IBasicHandler.Callback paramCallback, int paramInt, Bundle paramBundle)
    {
    }

    protected boolean isDestroyed()
    {
        return this.isDestroyed;
    }

    public void onCreate(Bundle paramBundle)
    {
    }

    public void onDestroy()
    {
        if (!this.isDestroyed)
        {
            this.isDestroyed = true;
            if (this.mExecutorService != null)
            {
                this.mExecutorService.shutdownNow();
                this.mExecutorService = null;
            }
            this.mWorkHandler = null;
            this.mUIHandler = null;
        }
    }

    public void onPause()
    {
    }

    public void onResume()
    {
    }

    public void onStart()
    {
    }

    public void onStop()
    {
    }

    public void setWorkCallback(IBasicHandler.Callback paramCallback)
    {
        this.mWorkingCallback = paramCallback;
    }

    private class Handler extends android.os.Handler
    {
        public Handler()
        {
            super();
        }

        public void dispatchMessage(Message paramMessage)
        {
            if (!BasicModel.this.isDestroyed)
            {
                if (BasicModel.this.mExecutorService == null)
                    super.dispatchMessage(paramMessage);
            }
            else
                return;
            if (paramMessage.getCallback() != null)
            {
                BasicModel.this.mExecutorService.execute(paramMessage.getCallback());
                return;
            }
            BasicModel.this.mExecutorService.execute(new BasicModel.MyRunnable( Message.obtain(paramMessage)));
        }

        public void handleMessage(Message paramMessage)
        {
            BasicModel.this.handleMessage(paramMessage);
        }
    }

    private class MyRunnable
            implements Runnable
    {
        private Message msg;

        MyRunnable(Message arg2)
        {
            this.msg = arg2;
        }

        public void run()
        {
            if (!BasicModel.this.isDestroyed)
                BasicModel.this.handleMessage(this.msg);
            this.msg.recycle();
        }
    }

    private class UIHandler extends Handler
    {
        public UIHandler()
        {
            super();
        }

        public void handleMessage(Message paramMessage)
        {
            BasicModel.this.handleMessageOnUIThread(paramMessage);
        }
    }
}
