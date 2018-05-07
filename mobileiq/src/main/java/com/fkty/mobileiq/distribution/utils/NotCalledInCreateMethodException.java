package com.fkty.mobileiq.distribution.utils;

import android.util.AndroidRuntimeException;

/**
 * Created by frank_tracy on 2017/12/22.
 */

public class NotCalledInCreateMethodException extends AndroidRuntimeException
{
    public NotCalledInCreateMethodException(String paramString)
    {
        super(paramString);
    }
}