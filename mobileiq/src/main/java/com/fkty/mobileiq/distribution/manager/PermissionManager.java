package com.fkty.mobileiq.distribution.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by frank_tracy on 2017/12/5.
 */

public class PermissionManager {
    private static PermissionManager instance;

    public static PermissionManager getInstance()
    {
        if (instance == null)
            instance = new PermissionManager();
        return instance;
    }

    public ArrayList<String> checkPermisson(Context paramContext, String[] paramArrayOfString)
    {
        ArrayList<String> permissions = new ArrayList<>();
        if(paramArrayOfString!=null && paramArrayOfString.length>0){
            for(int i=0;i<paramArrayOfString.length;i++){
                if (ContextCompat.checkSelfPermission(paramContext, paramArrayOfString[i]) !=  PackageManager.PERMISSION_GRANTED)
                {
                    permissions.add(paramArrayOfString[i]);
                }
            }
        }
        return permissions;
    }

    public boolean needRequestPermission()
    {
        Log.d("PermissionManager",""+Build.VERSION.SDK_INT );
        return Build.VERSION.SDK_INT >= 23;
    }
}
