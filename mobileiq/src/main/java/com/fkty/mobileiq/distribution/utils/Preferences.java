package com.fkty.mobileiq.distribution.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fkty.mobileiq.distribution.app.activity.SettingActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by frank_tracy on 2017/12/5.
 */

public class Preferences {
    public static final String FILE_NAME = "common";

    public static boolean contains(Context paramContext, String paramString)
    {
        return paramContext.getSharedPreferences(FILE_NAME, MODE_PRIVATE).contains(paramString);
    }
    public static Object get(Context paramContext, String paramString, Object paramObject)
    {
        SharedPreferences localSharedPreferences = paramContext.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        if ((paramObject instanceof String))
            return localSharedPreferences.getString(paramString, (String)paramObject);
        if ((paramObject instanceof Integer))
            return Integer.valueOf(localSharedPreferences.getInt(paramString, ((Integer)paramObject).intValue()));
        if ((paramObject instanceof Boolean))
            return Boolean.valueOf(localSharedPreferences.getBoolean(paramString, ((Boolean)paramObject).booleanValue()));
        if ((paramObject instanceof Float))
            return Float.valueOf(localSharedPreferences.getFloat(paramString, ((Float)paramObject).floatValue()));
        if ((paramObject instanceof Long))
            return Long.valueOf(localSharedPreferences.getLong(paramString, ((Long)paramObject).longValue()));
        return null;
    }

    public static float getValue(Context paramContext, String paramString1, String paramString2, float paramFloat)
    {
        return getSharedPreferences(paramContext, paramString1).getFloat(paramString2, paramFloat);
    }

    public static int getValue(Context paramContext, String paramString1, String paramString2, int paramInt)
    {
        return getSharedPreferences(paramContext, paramString1).getInt(paramString2, paramInt);
    }

    public static long getValue(Context paramContext, String paramString1, String paramString2, long paramLong)
    {
        return getSharedPreferences(paramContext, paramString1).getLong(paramString2, paramLong);
    }

    public static String getValue(Context paramContext, String paramString1, String paramString2, String paramString3)
    {
        return getSharedPreferences(paramContext, paramString1).getString(paramString2, paramString3);
    }

    public static boolean getValue(Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
    {
        return getSharedPreferences(paramContext, paramString1).getBoolean(paramString2, paramBoolean);
    }
    private static SharedPreferences getSharedPreferences(Context paramContext, String paramString)
    {
        return paramContext.getSharedPreferences(paramString, MODE_PRIVATE);
    }
    public static void putValue(Context paramContext, String paramString1, String paramString2, float paramFloat)
    {
        SharedPreferences.Editor localEditor = getEditor(paramContext, paramString1);
        localEditor.putFloat(paramString2, paramFloat);
        localEditor.commit();
    }

    public static void putValue(Context paramContext, String paramString1, String paramString2, int paramInt)
    {
        SharedPreferences.Editor localEditor = getEditor(paramContext, paramString1);
        localEditor.putInt(paramString2, paramInt);
        localEditor.commit();
    }

    public static void putValue(Context paramContext, String paramString1, String paramString2, long paramLong)
    {
        SharedPreferences.Editor localEditor = getEditor(paramContext, paramString1);
        localEditor.putLong(paramString2, paramLong);
        localEditor.commit();
    }

    public static void putValue(Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
    {
        SharedPreferences.Editor localEditor = getEditor(paramContext, paramString1);
        localEditor.putBoolean(paramString2, paramBoolean);
        localEditor.commit();
    }

    public static void putValue(SettingActivity paramSettingActivity, String paramString1, String paramString2, String paramString3)
    {
        SharedPreferences.Editor localEditor = getEditor(paramSettingActivity, paramString1);
        localEditor.putString(paramString2, paramString3);
        localEditor.commit();
    }
    private static SharedPreferences.Editor getEditor(Context paramContext, String paramString)
    {
        return getSharedPreferences(paramContext, paramString).edit();
    }
    public static void put(Context paramContext, String paramString, Object paramObject)
    {
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        if ((paramObject instanceof String)){
            localEditor.putString(paramString, (String)paramObject);
        }else if ((paramObject instanceof Integer)){
            localEditor.putInt(paramString, ((Integer)paramObject).intValue());
        }else if ((paramObject instanceof Boolean)){
            localEditor.putBoolean(paramString, ((Boolean)paramObject).booleanValue());
        }else if ((paramObject instanceof Float)){
            localEditor.putFloat(paramString, ((Float)paramObject).floatValue());
        }else if ((paramObject instanceof Long)) {
            localEditor.putLong(paramString, ((Long)paramObject).longValue());
        }else {
            localEditor.putString(paramString, paramObject.toString());
        }
        SharedPreferencesCompat.apply(localEditor);
    }
    private static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        public static void apply(SharedPreferences.Editor paramEditor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(paramEditor, new Object[0]);
                    return;
                }
            }
            catch (InvocationTargetException localInvocationTargetException)
            {
            }
            catch (IllegalAccessException localIllegalAccessException)
            {
            }
            catch (IllegalArgumentException localIllegalArgumentException)
            {
            }
            paramEditor.commit();
        }

        private static Method findApplyMethod()
        {
            try
            {
                Method localMethod = SharedPreferences.Editor.class.getMethod("apply", new Class[0]);
                return localMethod;
            }
            catch (NoSuchMethodException localNoSuchMethodException)
            {
            }
            return null;
        }
    }
}
