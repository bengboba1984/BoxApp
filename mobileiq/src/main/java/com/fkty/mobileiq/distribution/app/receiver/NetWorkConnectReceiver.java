package com.fkty.mobileiq.distribution.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.fkty.mobileiq.distribution.app.activity.WelcomeActivity;
import com.fkty.mobileiq.distribution.common.SystemManager;
import com.fkty.mobileiq.distribution.manager.MWifiManager;

import java.util.List;

/**
 * Created by frank_tracy on 2018/3/7.
 */

public class NetWorkConnectReceiver extends BroadcastReceiver
{
    final String TAG="NetWorkConnectReceiver";
    public void onReceive(Context paramContext, Intent paramIntent)
    {

        final String action = paramIntent.getAction();
//        Log.d("NetWorkConnectReceiver","////////////"+action+"/////////////");
        if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            SystemManager.getInstance().setScanResults();
            List localList = SystemManager.getInstance().getWifiList();
//            Log.e(TAG,"wifi list length:"+localList.size());
            if(localList.size()>0){
                if(MWifiManager.getIntance().ConnectWifi()) {
//                    Log.e(TAG,"connect wifi success!!");
                }

            }

        }else if(WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)){
            int wifiStat=paramIntent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,0);
            switch(wifiStat){
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    MWifiManager.getIntance().startScanWifi();
                    break;
            }
        }
//        Log.e("NetWorkConnectReceiver", "" + MWifiManager.getIntance().isConnect());
    }
}