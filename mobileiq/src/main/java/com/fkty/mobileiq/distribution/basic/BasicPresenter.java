package com.fkty.mobileiq.distribution.basic;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.app.Application.ActivityLifecycleCallbacks;
import com.fkty.mobileiq.distribution.inter.IBasicHandler;
import com.fkty.mobileiq.distribution.inter.IBasicModel;
import com.fkty.mobileiq.distribution.inter.IBasicPresenter;
import com.fkty.mobileiq.distribution.inter.IBasicView;
import com.fkty.mobileiq.distribution.inter.ILifeRecycle;
import com.fkty.mobileiq.distribution.utils.NotCalledInCreateMethodException;
import com.fkty.mobileiq.distribution.utils.SuperNotCalledException;

import java.util.HashMap;
import java.util.Map;

import static com.fkty.mobileiq.distribution.inter.ILifeRecycle.LifeStatus.ON_DESTROY;

/**
 * Created by frank_tracy on 2017/12/22.
 */

public abstract class BasicPresenter<T extends IBasicView, K extends IBasicModel>
        implements IBasicPresenter {
    private static boolean DEBUG_LIFECYCLE = false;
    private static final int MSG_ON_ERROR_CALLED_ON_UI = -268435452;
    private static final int MSG_ON_FAILED_CALLED_ON_UI = -268435453;
    private static final int MSG_ON_SUCCESS_CALLED_ON_UI = -268435454;
    private static final int MSG_ON_WORKING_CALLED_ON_UI = -268435455;
    protected boolean isBand = true;
    private boolean isCalled = false;
    private boolean isCreate = false;
    private boolean isDestroyed = false;
    private BasicPresenter<T, K>.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;
    private K mBasicModel;
    private T mBasicView;
    private Map<Long, Boolean> mCalledStatus;
    private Activity mHostActivity;
    private ILifeRecycle.LifeStatus mLifeStatus = ILifeRecycle.LifeStatus.ON_CREATE;
    private Handler mUIHandler;
    protected IBasicHandler.Callback mWorkCallback = new IBasicHandler.Callback()
    {
        public void call(int paramInt, Bundle paramBundle)
        {
            if (BasicPresenter.this.isDestroyed)
                return;

            if(!((Boolean)BasicPresenter.this.mCalledStatus.get(Long.valueOf(Thread.currentThread().getId()))).booleanValue()){
                BasicPresenter.this.mCalledStatus.put(Long.valueOf(Thread.currentThread().getId()), Boolean.valueOf(false));
                BasicPresenter.this.onWorkingCalledOnWorkThread(paramInt, paramBundle);
            }
            Message.obtain(BasicPresenter.this.mUIHandler, MSG_ON_WORKING_CALLED_ON_UI, paramInt, 0, paramBundle).sendToTarget();
        }

        public void onError(int paramInt, Bundle paramBundle)
        {
            if (BasicPresenter.this.isDestroyed)
                return;
            if(!((Boolean)BasicPresenter.this.mCalledStatus.get(Long.valueOf(Thread.currentThread().getId()))).booleanValue()){
                BasicPresenter.this.mCalledStatus.put(Long.valueOf(Thread.currentThread().getId()), Boolean.valueOf(false));
                BasicPresenter.this.onErrorCalledOnWorkThread(paramInt, paramBundle);
            }
            Message.obtain(BasicPresenter.this.mUIHandler, MSG_ON_ERROR_CALLED_ON_UI, paramInt, 0, paramBundle).sendToTarget();
        }

        public void onFailed(int paramInt, Bundle paramBundle)
        {
            if (BasicPresenter.this.isDestroyed)
                return;
            if(!((Boolean)BasicPresenter.this.mCalledStatus.get(Long.valueOf(Thread.currentThread().getId()))).booleanValue())
            {
                BasicPresenter.this.mCalledStatus.put(Long.valueOf(Thread.currentThread().getId()), Boolean.valueOf(false));
                BasicPresenter.this.onFailedCalledOnWorkThread(paramInt, paramBundle);
            }
            Message.obtain(BasicPresenter.this.mUIHandler, MSG_ON_FAILED_CALLED_ON_UI, paramInt, 0, paramBundle).sendToTarget();
        }

        public void onSuccess(int paramInt, Bundle paramBundle)
        {
            if (BasicPresenter.this.isDestroyed)return;
            if(!((Boolean)BasicPresenter.this.mCalledStatus.get(Long.valueOf(Thread.currentThread().getId()))).booleanValue())
            {
                BasicPresenter.this.mCalledStatus.put(Long.valueOf(Thread.currentThread().getId()), Boolean.valueOf(false));
                BasicPresenter.this.onSuccessCalledOnWorkThread(paramInt, paramBundle);
            }
            Message.obtain(BasicPresenter.this.mUIHandler, MSG_ON_SUCCESS_CALLED_ON_UI, paramInt, 0, paramBundle).sendToTarget();
        }
    };

    protected void onSuccessCalledOnWorkThread(int paramInt, Bundle paramBundle)
    {
        this.mCalledStatus.put(Long.valueOf(Thread.currentThread().getId()), Boolean.valueOf(true));
    }
    protected void onFailedCalledOnWorkThread(int paramInt, Bundle paramBundle)
    {
        this.mCalledStatus.put(Long.valueOf(Thread.currentThread().getId()), Boolean.valueOf(true));
    }
    protected void onWorkingCalledOnWorkThread(int paramInt, Bundle paramBundle)
    {
        this.mCalledStatus.put(Long.valueOf(Thread.currentThread().getId()), Boolean.valueOf(true));
    }
    protected void onErrorCalledOnWorkThread(int paramInt, Bundle paramBundle)
    {
        this.mCalledStatus.put(Long.valueOf(Thread.currentThread().getId()), Boolean.valueOf(true));
    }
    public BasicPresenter(T paramT, K paramK)
    {
        this.mBasicView = paramT;
        this.mBasicModel = paramK;
        this.mUIHandler = new Handler();
        this.mCalledStatus = new HashMap();
        this.mBasicModel.setWorkCallback(this.mWorkCallback);
        this.mHostActivity = getViewActivity();
        if ((this.mHostActivity != null) && (this.isBand))
        {
            Application localApplication = this.mHostActivity.getApplication();
            if (localApplication == null)
                throw new NotCalledInCreateMethodException("BasicPresenter " + this + " did not call in activity's onCreate() or after activity's onCreate()");
            this.mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks();
            localApplication.registerActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
        }
    }


    public BasicPresenter(T paramT, K paramK, boolean paramBoolean)
    {
        this.mBasicView = paramT;
        this.mBasicModel = paramK;
        this.mUIHandler = new Handler();
        this.mCalledStatus = new HashMap();
        this.mBasicModel.setWorkCallback(this.mWorkCallback);
        this.mHostActivity = getViewActivity();
        if ((this.mHostActivity != null) && (paramBoolean))
        {
            Application localApplication = this.mHostActivity.getApplication();
            if (localApplication == null)
                throw new NotCalledInCreateMethodException("BasicPresenter " + this + " did not call in activity's onCreate() or after activity's onCreate()");
            this.mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks();
            localApplication.registerActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
        }
    }

    private Activity getViewActivity()
    {
        if ((this.mBasicView instanceof Activity))
            return (Activity)this.mBasicView;
        if ((this.mBasicView instanceof Fragment))
            return ((Fragment)this.mBasicView).getActivity();
        if ((this.mBasicView instanceof View))
        {
            Context localContext = ((View)this.mBasicView).getContext();
            if ((localContext != null) && ((localContext instanceof Activity)))
                return (Activity)localContext;
        }
        return null;
    }

    private class ActivityLifecycleCallbacks
            implements Application.ActivityLifecycleCallbacks{
        private ActivityLifecycleCallbacks()
        {
        }
        @Override
        public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {
            if (paramActivity == BasicPresenter.this.mHostActivity)
            {
                BasicPresenter.this.onCreate(paramBundle);
                if (!isCalled)
                    throw new SuperNotCalledException("BasicPresenter " + BasicPresenter.this + " did not call through to super.onCreate()");
            }
        }

        @Override
        public void onActivityStarted(Activity paramActivity) {
            if (paramActivity == BasicPresenter.this.mHostActivity)
            {
                BasicPresenter.this.onStart();
                if (!BasicPresenter.this.isCalled)
                    throw new SuperNotCalledException("BasicPresenter " + BasicPresenter.this + " did not call through to super.onStart()");
            }
        }

        @Override
        public void onActivityResumed(Activity paramActivity) {
            if (paramActivity == BasicPresenter.this.mHostActivity)
            {
                BasicPresenter.this.onResume();
                if (!BasicPresenter.this.isCalled)
                    throw new SuperNotCalledException("BasicPresenter " + BasicPresenter.this + " did not call through to super.onResume()");
            }
        }

        @Override
        public void onActivityPaused(Activity paramActivity) {
            if (paramActivity == BasicPresenter.this.mHostActivity)
            {
                BasicPresenter.this.onPause();
                if (!BasicPresenter.this.isCalled)
                    throw new SuperNotCalledException("BasicPresenter " + BasicPresenter.this + " did not call through to super.onPause()");
            }
        }

        @Override
        public void onActivityStopped(Activity paramActivity) {
            if (paramActivity == BasicPresenter.this.mHostActivity)
            {
                BasicPresenter.this.onStop();
                if (!BasicPresenter.this.isCalled)
                    throw new SuperNotCalledException("BasicPresenter " + BasicPresenter.this + " did not call through to super.onStop()");
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity paramActivity) {
            if (paramActivity == BasicPresenter.this.mHostActivity)
            {
                BasicPresenter.this.onDestroy();
                if (!BasicPresenter.this.isCalled)
                    throw new SuperNotCalledException("BasicPresenter " + BasicPresenter.this + " did not call through to super.onDestroy()");
            }
        }
    }

    private class Handler extends android.os.Handler{
        private Handler()
        {
            super();
        }

        public void handleMessage(Message paramMessage)
        {
            if (BasicPresenter.this.isDestroyed)
                return;
            switch (paramMessage.what)
            {
                default:
                    BasicPresenter.this.handleMessage(paramMessage);
                    break;
                case MSG_ON_WORKING_CALLED_ON_UI:
                    BasicPresenter.this.onWorkingCalledOnUIThread(paramMessage.arg1, (Bundle)paramMessage.obj);
                    break;
                case MSG_ON_SUCCESS_CALLED_ON_UI:
                    BasicPresenter.this.onSuccessCalledOnUIThread(paramMessage.arg1, (Bundle)paramMessage.obj);
                    break;
                case MSG_ON_FAILED_CALLED_ON_UI:
                    BasicPresenter.this.onFailedCalledOnUIThread(paramMessage.arg1, (Bundle)paramMessage.obj);
                    break;
                case MSG_ON_ERROR_CALLED_ON_UI:
                    BasicPresenter.this.onErrorCalledOnUIThread(paramMessage.arg1, (Bundle)paramMessage.obj);
                    break;
            }
        }
    }
    protected void onErrorCalledOnUIThread(int paramInt, Bundle paramBundle)
    {
    }
    protected void onFailedCalledOnUIThread(int paramInt, Bundle paramBundle)
    {
    }
    protected void onSuccessCalledOnUIThread(int paramInt, Bundle paramBundle)
    {
    }
    protected void onWorkingCalledOnUIThread(int paramInt, Bundle paramBundle)
    {
    }

    protected void handleMessage(Message paramMessage)
    {
    }
    protected void moveLife(ILifeRecycle.LifeStatus paramLifeStatus, Bundle paramBundle)
    {
        switch (paramLifeStatus)
        {
            default:
                break;
            case ON_CREATE:
                if(this.mLifeStatus.value <= ILifeRecycle.LifeStatus.ON_CREATE.value)
                {
                    this.isCreate = true;
                    getBasicModel().onCreate(paramBundle);
                    this.mLifeStatus = ILifeRecycle.LifeStatus.ON_CREATE;
                }
                break;
            case ON_START:
                if(this.mLifeStatus.value < LifeStatus.ON_RESUME.value)
                {
                    if (!this.isCreate)
                    {
                        this.isCalled = false;
                        onCreate(null);
                        if (!this.isCalled)
                            throw new SuperNotCalledException("BasicPresenter " + this + " did not call through to super.onCreate()");
                    }
                    getBasicModel().onStart();
                    this.mLifeStatus = ILifeRecycle.LifeStatus.ON_START;
                }
                break;
            case ON_RESUME:
                getBasicModel().onStart();
                this.mLifeStatus = ILifeRecycle.LifeStatus.ON_RESUME;
                break;
            case ON_PAUSE:
                this.mLifeStatus = ILifeRecycle.LifeStatus.ON_PAUSE;
                getBasicModel().onPause();

                break;
            case ON_STOP:
                if((this.isDestroyed) || (this.mLifeStatus.value >= ILifeRecycle.LifeStatus.ON_DESTROY.value))
                {
                    this.isCalled = false;
                    if (!this.isCalled)
                        throw new SuperNotCalledException("BasicPresenter " + this + " did not call through to super.onStop()");
                }else{
                    getBasicModel().onStop();
                    this.mLifeStatus = ILifeRecycle.LifeStatus.ON_STOP;
                }
                break;
            case ON_DESTROY:
                    this.isDestroyed = true;
                    this.mBasicModel.onDestroy();
                    if (this.mHostActivity != null)
                    {
                        Application localApplication = this.mHostActivity.getApplication();
                        if (localApplication != null)
                            localApplication.unregisterActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
                    }
                    this.mBasicView = null;
                    this.mBasicModel = null;
                    this.mHostActivity = null;
                    this.mUIHandler = null;
                    this.mLifeStatus = ON_DESTROY;

                break;

        }

    }

    @Override
    public void onCreate(Bundle paramBundle) {

    }

    @Override
    public void onDestroy() {
        moveLife(ON_DESTROY, null);
        if (DEBUG_LIFECYCLE)
            Log.d("BasicPresenter", "BasicPresenter: onDestroy " + this);
        this.isCalled = true;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        moveLife(ILifeRecycle.LifeStatus.ON_STOP, null);
        if (DEBUG_LIFECYCLE)
            Log.d("BasicPresenter", "BasicPresenter: onStop " + this);
        this.isCalled = true;
    }

    protected K getBasicModel()
    {
        return this.mBasicModel;
    }

    protected T getBasicView()
    {
        return this.mBasicView;
    }
    @Override
    public Object getData(int paramInt, Bundle paramBundle) {
        return null;
    }

    @Override
    public void handleTask(Callback paramCallback, int paramInt, Bundle paramBundle) {

    }

    @Override
    public void finish() {
        onDestroy();
    }

    protected Handler getHandler()
    {
        return this.mUIHandler;
    }
    protected boolean isDestroyed()
    {
        return this.isDestroyed;
    }
    protected void setDebugLifecycle(boolean paramBoolean)
    {
        DEBUG_LIFECYCLE = paramBoolean;
    }
}
