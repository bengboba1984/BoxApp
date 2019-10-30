package com.fkty.mobileiq.distribution.manager;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.net.ConnectivityManager;
import android.net.Network;
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
import com.fkty.mobileiq.distribution.constant.CommonField;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private static final String WIFI_NAME = CommonField.SSID_PRE;
    private static final String WIFI_PWD = "1234567890";
    private static MWifiManager connect2Wifi = new MWifiManager();
    private WifiManager mWifiManager = SystemManager.getInstance().getWifiManager();

    public static MWifiManager getIntance() {

        return connect2Wifi;
    }

    public boolean isNetworkConnected() {
//        Log.d(TAG,"testing~~~~~~~~~~~~~~~~~~~`");
//
//        int ret=-999;
//        Runtime runtime = Runtime.getRuntime();
//        Process p=null;
//        try {
//            p = runtime.exec("ping -c 1 -w 1 -W 1 www.baidu.com");
//            ret = p.waitFor();
//            Log.i("Avalible", "Process:"+ret);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }finally {
//            if(p!=null)
//                p.destroy();
//        }
//        if(ret==0){
//            return true;
//        }else{
//            return false;
//        }

        if (CommonField.BRIDGE.equals(DataManager.getInstance().getOotConnectType())) {
            //桥接模式无法联网
            return false;
        }
        Context context = DistributedMobileIQApplication.getInstance();
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
                Log.d(TAG, "nc=" + nc.toString());
                return nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) && nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            } else {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null && (info.getState() == NetworkInfo.State.CONNECTING || info.getState() == NetworkInfo.State.CONNECTED)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isBoxConnectNetwork() {
        if (this.mWifiManager.getConnectionInfo().getSSID().contains(WIFI_NAME)) {
            return isNetworkConnected();
        } else {
            return false;
        }
    }

    public boolean isWifiConnect() {
        Context context = DistributedMobileIQApplication.getInstance();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Log.d(MWifiManager.class.getName(), "cm.getActiveNetworkInfo().getExtraInfo()=" + getSSID() + "/Build.VERSION.SDK_INT=" + Build.VERSION.SDK_INT);
        return this.mWifiManager.getConnectionInfo().getSSID().contains(WIFI_NAME);
    }

    public void startScanWifi() {

        SystemManager.getInstance().startScanWifi();
    }

    public boolean ConnectWifi() {

        if (this.mWifiManager.getConnectionInfo().getSSID().contains(WIFI_NAME))
            return true;

        List localList = SystemManager.getInstance().getWifiList();
        Log.e("Connect2Wifi", "==================" + localList.size());
        ScanResult localScanResult = null;
        for (int i = 0; i < localList.size(); i++) {
            Log.e("Connect2Wifi", "WIFI name : " + ((ScanResult) localList.get(i)).SSID);
            if (!((ScanResult) localList.get(i)).SSID.contains(WIFI_NAME)) {
                continue;
            } else {
                localScanResult = (ScanResult) localList.get(i);
                break;
            }
        }
        boolean bool = false;
        if (localScanResult != null) {
//            int j = this.mWifiManager.addNetwork(createWifiConfig(localScanResult.SSID, WIFI_PWD, WIFICIPHER_WPA2));
            int j = this.mWifiManager.addNetwork(createWifiConfig(localScanResult.SSID, WIFI_PWD, WIFICIPHER_NOPASS));
            Log.e("Connect2Wifi", "-------------------netId : " + j);
            bool = this.mWifiManager.enableNetwork(j, true);
        }
        Log.e("Connect2Wifi", "-------------isSuccess : " + bool);
        return bool;
    }

    private WifiConfiguration createWifiConfig(String paramString1, String paramString2, int paramInt) {
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

    private WifiConfiguration isExist(String paramString) {
        List localList = this.mWifiManager.getConfiguredNetworks();
        if ((localList != null) && (localList.size() > 0)) {
            Iterator localIterator = localList.iterator();
            while (localIterator.hasNext()) {
                WifiConfiguration localWifiConfiguration = (WifiConfiguration) localIterator.next();
                if (localWifiConfiguration.SSID.equals("\"" + paramString + "\""))
                    return localWifiConfiguration;
            }
        }
        return null;
    }

//    public String getSSID() {
//        return this.mWifiManager.getConnectionInfo().getSSID();
//    }


    public String getSSID() {
        String ssid = "unknown id";
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT ==  Build.VERSION_CODES.P) {
            WifiManager mWifiManager = (WifiManager) DistributedMobileIQApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();
            Log.d("@@@=====","info.toString()="+info.toString());
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return info.getSSID();
            } else {
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
            ConnectivityManager connManager = (ConnectivityManager) DistributedMobileIQApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connManager != null;
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            Log.d("@@@=====","networkInfo.toString()="+networkInfo.toString());
            if (networkInfo.isConnected()) {
                Log.d("@@@=====","networkInfo.getExtraInfo()="+networkInfo.getExtraInfo());
                if (networkInfo.getExtraInfo() != null) {
                    return networkInfo.getExtraInfo().replace("\"", "");
                }
            }
        }
        return ssid;
    }
}
