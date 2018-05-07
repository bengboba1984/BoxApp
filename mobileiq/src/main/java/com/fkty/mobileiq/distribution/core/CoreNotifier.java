package com.fkty.mobileiq.distribution.core;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by frank_tracy on 2018/3/8.
 */

public class CoreNotifier {
    private static CoreNotifier notifier = new CoreNotifier();
    private ConcurrentMap<Integer, ICoreListener> listenerMap = new ConcurrentHashMap();

    private void broadcast(String paramString)
    {
        Iterator localIterator = this.listenerMap.keySet().iterator();
        while (localIterator.hasNext())
        {
            Integer localInteger = (Integer)localIterator.next();
            dispatch((ICoreListener)this.listenerMap.get(localInteger), paramString);
        }
    }

    private void dispatch(ICoreListener paramICoreListener, String paramString)
    {
        if (paramICoreListener != null){
            paramICoreListener.onCoreMessage(paramString);
        }
    }

    public static CoreNotifier getInstance()
    {
        return notifier;
    }

    public void notify(String paramString)
    {
        broadcast(paramString);
    }

    public void registerListener(ICoreListener paramICoreListener)
    {
        if (!this.listenerMap.containsKey(Integer.valueOf(paramICoreListener.hashCode())))
            this.listenerMap.put(Integer.valueOf(paramICoreListener.hashCode()), paramICoreListener);
    }

    public void removeListener(ICoreListener paramICoreListener)
    {
        this.listenerMap.remove(Integer.valueOf(paramICoreListener.hashCode()));
    }
}
