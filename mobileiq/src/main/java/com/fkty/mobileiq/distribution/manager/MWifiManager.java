package com.fkty.mobileiq.distribution.manager;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import com.fkty.mobileiq.distribution.DistributedMobileIQApplication;
import com.fkty.mobileiq.distribution.common.SystemManager;

import java.util.Iterator;
import java.util.List;

/**
 * Created by frank_tracy on 2017/12/7.
 */

public class MWifiManager {
    private static final String TAG = "Connect2Wifi";
    private static final int WIFICIPHER_NOPASS = 0;
    private static final int WIFICIPHER_WEP = 1;
    private static final int WIFICIPHER_WPA = 2;
    private static final int WIFICIPHER_WPA2 = 3;
    private static final String WIFI_NAME = "OpenWrt";
    private static final String WIFI_PWD = "1234567890";
    private static MWifiManager connect2Wifi = new MWifiManager();
    private WifiManager mWifiManager = SystemManager.getInstance().getWifiManager();

    public static MWifiManager getIntance()
    {

        return connect2Wifi;
    }

    private boolean isNetworkConnected(){
        Context context=DistributedMobileIQApplication.getInstance();
        if(context!=null){
            ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            System.out.println("step1:Build.VERSION.SDK_INT ="+Build.VERSION.SDK_INT );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                System.out.println("step2");
                NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
                return nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            }else{
                NetworkInfo info= cm.getActiveNetworkInfo();
                System.out.println("step3：info.getType()="+info.getType());
                if(info!=null && info.getType()==ConnectivityManager.TYPE_WIFI){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isConnect()
    {
        if(this.mWifiManager.getConnectionInfo().getSSID().contains(WIFI_NAME)){
            return isNetworkConnected();
        }else{
            return false;
        }
    }

    public boolean isWifiConnect()
    {
        return this.mWifiManager.getConnectionInfo().getSSID().contains(WIFI_NAME);
    }

    public void startScanWifi(){

        SystemManager.getInstance().startScanWifi();
    }

    public boolean ConnectWifi()
    {

        if (this.mWifiManager.getConnectionInfo().getSSID().contains(WIFI_NAME))
            return true;

        List localList = SystemManager.getInstance().getWifiList();
        Log.e("Connect2Wifi", "==================" + localList.size());
        ScanResult localScanResult = null;
        for (int i = 0; i < localList.size(); i++)
        {
            Log.e("Connect2Wifi", "WIFI name : " + ((ScanResult)localList.get(i)).SSID);
            if (!((ScanResult)localList.get(i)).SSID.contains(WIFI_NAME)){
                continue;
            }else{
                localScanResult = (ScanResult)localList.get(i);
                break;
            }
        }
        boolean bool = false;
        if (localScanResult != null)
        {
//            int j = this.mWifiManager.addNetwork(createWifiConfig(localScanResult.SSID, WIFI_PWD, WIFICIPHER_WPA2));
            int j = this.mWifiManager.addNetwork(createWifiConfig(localScanResult.SSID, WIFI_PWD, WIFICIPHER_NOPASS));
            Log.e("Connect2Wifi", "-------------------netId : " + j);
            bool = this.mWifiManager.enableNetwork(j, true);
        }
        Log.e("Connect2Wifi", "-------------isSuccess : " + bool);
        return bool;
    }

    private WifiConfiguration createWifiConfig(String paramString1, String paramString2, int paramInt)
    {
        WifiConfiguration localWifiConfiguration1 = new WifiConfiguration();
        localWifiConfiguration1.allowedAuthAlgorithms.clear();
        localWifiConfiguration1.allowedGroupCiphers.clear();
        localWifiConfiguration1.allowedKeyManagement.clear();
        localWifiConfiguration1.allowedPairwiseCiphers.clear();
        localWifiConfiguration1.allowedProtocols.clear();
        localWifiConfiguration1.SSID = ("\"" + paramString1 + "\"");
        WifiConfiguration localWifiConfiguration2 = isExist(paramString1);
        if (localWifiConfiguration2 != null)
            this.mWifiManager.removeNetwork(localWifiConfiguration2.networkId);
        switch (paramInt) {
            default:
                return localWifiConfiguration1;
            case WIFICIPHER_NOPASS:
                localWifiConfiguration1.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                return localWifiConfiguration1;
            case WIFICIPHER_WEP:
                localWifiConfiguration1.hiddenSSID = true;
                localWifiConfiguration1.wepKeys[0] = ("\"" + paramString2 + "\"");
                localWifiConfiguration1.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                localWifiConfiguration1.wepTxKeyIndex = 0;
                localWifiConfiguration1.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                localWifiConfiguration1.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                localWifiConfiguration1.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                localWifiConfiguration1.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                localWifiConfiguration1.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                return localWifiConfiguration1;
            case WIFICIPHER_WPA:
                localWifiConfiguration1.preSharedKey = ("\"" + paramString2 + "\"");
                localWifiConfiguration1.hiddenSSID = true;
                localWifiConfiguration1.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                localWifiConfiguration1.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                localWifiConfiguration1.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                localWifiConfiguration1.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                localWifiConfiguration1.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                localWifiConfiguration1.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                localWifiConfiguration1.status = WifiConfiguration.Status.ENABLED;
                return localWifiConfiguration1;
            case WIFICIPHER_WPA2:
                localWifiConfiguration1.preSharedKey = ("\"" + paramString2 + "\"");
                localWifiConfiguration1.status = WifiConfiguration.Status.ENABLED;
                localWifiConfiguration1.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                localWifiConfiguration1.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                localWifiConfiguration1.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                localWifiConfiguration1.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                localWifiConfiguration1.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                localWifiConfiguration1.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                return localWifiConfiguration1;
        }
    }
    private WifiConfiguration isExist(String paramString)
    {
        List localList = this.mWifiManager.getConfiguredNetworks();
        if ((localList != null) && (localList.size() > 0))
        {
            Iterator localIterator = localList.iterator();
            while (localIterator.hasNext())
            {
                WifiConfiguration localWifiConfiguration = (WifiConfiguration)localIterator.next();
                if (localWifiConfiguration.SSID.equals("\"" + paramString + "\""))
                    return localWifiConfiguration;
            }
        }
        return null;
    }

}
