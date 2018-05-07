package com.fkty.mobileiq.distribution.basic;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.fkty.mobileiq.distribution.inter.IBasicHandler;
import com.fkty.mobileiq.distribution.inter.IBasicView;
import com.fkty.mobileiq.distribution.manager.AppManager;

/**
 * Created by frank_tracy on 2017/12/5.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, IBasicView {
    private final int ACTIVITY_CREATE = 0;
    private final int ACTIVITY_DESTROY = 1;
    private final int ACTIVITY_PAUSE = 2;
    private final int ACTIVITY_RESTART = 3;
    private final int ACTIVITY_RESUME = 4;
    private final int ACTIVITY_START = 5;
    private final int ACTIVITY_STOP = 6;
    protected final String TAG = getClass().getSimpleName();
    private int activityState;
    private boolean isAllowScreenRoate = false;
    private boolean mAllowFullScreen = true;
    private View mContextView = null;

    public abstract void initView(View paramView);
    public abstract void initData();
    public abstract void initParms(Bundle paramBundle);
    public abstract int bindLayout();
    public abstract View bindView();
    public abstract void doBusiness(Context paramContext);
    public abstract void setListener();
    public abstract void widgetClick(View paramView);

    public Object getData(int paramInt, Bundle paramBundle)
    {
        return null;
    }
    public void handleTask(IBasicHandler.Callback paramCallback, int paramInt, Bundle paramBundle)
    {
    }

    protected <T extends View> T $(int paramInt)
    {
        return super.findViewById(paramInt);
    }
    protected void onCreate(Bundle paramBundle)
    {Log.d(this.TAG, "start BaseActivity-->onCreate()");
        super.onCreate(paramBundle);
        this.activityState = ACTIVITY_CREATE;
        AppManager.getAppManager().addActivity(this);
        Log.d(this.TAG, "BaseActivity-->onCreate()");
        if(paramBundle!=null){
            initParms(paramBundle);
        }else if(getIntent()!=null && getIntent().getExtras()!=null){
            initParms(getIntent().getExtras());
        }

        View localView = bindView();
        if (localView == null){
            this.mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        }else{
            this.mContextView = localView;
        }


        if (this.mAllowFullScreen)
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!this.isAllowScreenRoate)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(this.mContextView);
        initView(this.mContextView);
        initData();
        setListener();
        doBusiness(this);
        return;
    }

    protected void onDestroy()
    {
        super.onDestroy();
        this.activityState = ACTIVITY_DESTROY;
        Log.d(this.TAG, "onDestroy()");
        AppManager.getAppManager().removeActivity(this);
    }

    protected void onPause()
    {
        super.onPause();
        this.activityState = ACTIVITY_PAUSE;
        Log.d(this.TAG, "onPause()");
    }

    protected void onRestart()
    {
        super.onRestart();
        this.activityState = ACTIVITY_RESTART;
        Log.d(this.TAG, "onRestart()");
    }

    protected void onResume()
    {
        super.onResume();
        this.activityState = ACTIVITY_RESUME;
        Log.d(this.TAG, "onResume()");
    }

    protected void onStart()
    {
        super.onStart();
        this.activityState = ACTIVITY_START;
        Log.d(this.TAG, "onStart()");
    }

    protected void onStop()
    {
        super.onStop();
        this.activityState = ACTIVITY_STOP;
        Log.d(this.TAG, "onStop()");
    }

    public void setAllowFullScreen(boolean paramBoolean)
    {
        this.mAllowFullScreen = paramBoolean;
    }

    public void setScreenRoate(boolean paramBoolean)
    {
        this.isAllowScreenRoate = paramBoolean;
    }

    protected void showToast(String paramString)
    {
        Toast.makeText(this, paramString, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class<?> paramClass)

    {
        startActivity(new Intent(this, paramClass));
    }

    public void startActivity(Class<?> paramClass, Bundle paramBundle)
    {
        Intent localIntent = new Intent();
        localIntent.setClass(this, paramClass);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }

    public void startActivityForResult(Class<?> paramClass, Bundle paramBundle, int paramInt)
    {
        Intent localIntent = new Intent();
        localIntent.setClass(this, paramClass);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivityForResult(localIntent, paramInt);
    }

    public void onClick(View paramView)
    {
        widgetClick(paramView);
    }

    public int dip2px(Context paramContext, float paramFloat)
    {
        return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
    }
}
