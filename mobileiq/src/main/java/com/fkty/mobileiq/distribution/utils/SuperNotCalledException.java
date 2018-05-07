package com.fkty.mobileiq.distribution.utils;

import android.util.AndroidRuntimeException;

/**
 * Created by frank_tracy on 2017/12/22.
 */

public class SuperNotCalledException extends AndroidRuntimeException
{
    public SuperNotCalledException(String paramString)
    {
        super(paramString);
    }
}