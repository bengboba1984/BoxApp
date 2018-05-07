package com.fkty.mobileiq.distribution.utils;

/**
 * Created by frank_tracy on 2018/4/3.
 */

public class FileUtils {
    public static String formatFileSize(long paramLong)
    {
        if (paramLong < 1024L)
        {
            Object[] arrayOfObject5 = new Object[1];
            arrayOfObject5[0] = Long.valueOf(paramLong);
            return String.format("%dB", arrayOfObject5);
        }
        if (paramLong < 1048576L)
        {
            Object[] arrayOfObject4 = new Object[1];
            arrayOfObject4[0] = Double.valueOf(paramLong / 1024.0D);
            return String.format("%.1fKB", arrayOfObject4);
        }
        if (paramLong < 1073741824L)
        {
            Object[] arrayOfObject3 = new Object[1];
            arrayOfObject3[0] = Double.valueOf(paramLong / 1048576.0D);
            return String.format("%.1fMB", arrayOfObject3);
        }
        if (paramLong < 1125899906842624L)
        {
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = Double.valueOf(paramLong / 1073741824.0D);
            return String.format("%.1fGB", arrayOfObject2);
        }
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Double.valueOf(paramLong / 1125899906842624.0D);
        return String.format("%.1fTB", arrayOfObject1);
    }
}
