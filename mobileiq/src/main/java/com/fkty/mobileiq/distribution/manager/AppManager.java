package com.fkty.mobileiq.distribution.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by frank_tracy on 2017/12/5.
 */

public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager()
    {
        if (activityStack == null)
            activityStack = new Stack();
    }

    public static AppManager getAppManager()
    {
        if (instance == null)
            instance = new AppManager();
        return instance;
    }

    public void AppExit(Context paramContext)
    {
        try
        {
            finishAllActivity();
            ((ActivityManager)paramContext.getSystemService(Context.ACTIVITY_SERVICE)).killBackgroundProcesses(paramContext.getPackageName());
            System.exit(0);
            return;
        }
        catch (Exception localException)
        {
        }
    }

    public void addActivity(Activity paramActivity)
    {
        activityStack.add(paramActivity);
    }

    public Activity currentActivity()
    {
        return (Activity)activityStack.lastElement();
    }

    public void finishActivity()
    {
        finishActivity((Activity)activityStack.lastElement());
    }

    public void finishActivity(Activity paramActivity)
    {
        if (paramActivity != null)
        {
            activityStack.remove(paramActivity);
            paramActivity.finish();
        }
    }

    public void finishActivity(Class<?> paramClass)
    {
        Iterator localIterator = activityStack.iterator();
        while (localIterator.hasNext())
        {
            Activity localActivity = (Activity)localIterator.next();
            if (!localActivity.getClass().equals(paramClass))
                continue;
            finishActivity(localActivity);
        }
    }

    public void finishAllActivity()
    {
        for (int i = 0; i < activityStack.size(); i++)
        {
            if (activityStack.get(i) == null)
                continue;
            ((Activity)activityStack.get(i)).finish();
        }
        activityStack.clear();
    }

    public void removeActivity(Activity paramActivity)
    {
        activityStack.remove(paramActivity);
    }
}
