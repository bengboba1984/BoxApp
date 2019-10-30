package com.fkty.mobileiq.distribution.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frank_tracy on 2017/12/7.
 */

public class SystemManager {
    private static SystemManager systemManager;
    private WifiManager wifiManager;
    private List<WifiConfiguration> wifiConfigurations;
    private WifiInfo wifiInfo;
    private List<ScanResult> wifiList;
//    private TelephonyManager telephonyManager;
//    private PhoneStateListener phoneStateListener;
    WifiManager.WifiLock wifiLock;
//    private Map<Integer, String> networkType = new HashMap();
//    private Map<String, String> apn2Gateway = new HashMap();
//    private Map<Integer, String> phoneType = new HashMap();
//    private Map<Integer, String> simState = new HashMap();
    private SystemManager()
    {
        prepareDate();

    }
    public static SystemManager getInstance()
    {
        if (systemManager == null)
            systemManager = new SystemManager();
        return systemManager;
    }
    private void prepareDate()
    {
//        this.apn2Gateway.put("cmwap", "10.0.0.172");
//        this.apn2Gateway.put("uniwap", "10.0.0.172");
//        this.apn2Gateway.put("3gwap", "10.0.0.172");
//        this.networkType.put(Integer.valueOf(0), "WiFi");
//        this.networkType.put(Integer.valueOf(0), "Unknown");
//        this.networkType.put(Integer.valueOf(1), "GPRS");
//        this.networkType.put(Integer.valueOf(2), "EDGE");
//        this.networkType.put(Integer.valueOf(3), "UMTS");
//        this.networkType.put(Integer.valueOf(8), "HSDPA");
//        this.networkType.put(Integer.valueOf(9), "HSUPA");
//        this.networkType.put(Integer.valueOf(10), "HSPA");
//        this.networkType.put(Integer.valueOf(4), "CDMA");
//        this.networkType.put(Integer.valueOf(5), "EVDO_0");
//        this.networkType.put(Integer.valueOf(6), "EVDO_A");
//        this.networkType.put(Integer.valueOf(7), "1xRTT");
//        this.networkType.put(Integer.valueOf(11), "LTE");
//        this.phoneType.put(Integer.valueOf(0), "Unknown");
//        this.phoneType.put(Integer.valueOf(1), "GSM");
//        this.phoneType.put(Integer.valueOf(2), "CDMA");
//        this.phoneType.put(Integer.valueOf(3), "SIP");
//        this.simState.put(Integer.valueOf(0), "Unknown");
//        this.simState.put(Integer.valueOf(1), "ABSENT");
//        this.simState.put(Integer.valueOf(4), "NETWORK_LOCKED");
//        this.simState.put(Integer.valueOf(2), "PIN_REQUIRED");
//        this.simState.put(Integer.valueOf(3), "PUK_REQUIRED");
//        this.simState.put(Integer.valueOf(5), "READY");
    }
    public WifiManager getWifiManager()
    {
        return this.wifiManager;
    }
    public void initialize(Context paramContext)
    {
//        this.context = paramContext;
//        Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
//        this.mScreenWidth = localDisplay.getWidth();
//        this.mScreenHeight = localDisplay.getHeight();
//        Log.d(TAG, "width:" + this.mScreenWidth + "height:" + this.mScreenHeight);
//        this.powerManager = ((PowerManager)paramContext.getSystemService("power"));
        this.wifiManager = ((WifiManager)paramContext.getSystemService(Context.WIFI_SERVICE));
//        this.locationManager = ((LocationManager)paramContext.getSystemService("location"));
//        this.telephonyManager = ((TelephonyManager)paramContext.getSystemService(Context.TELEPHONY_SERVICE));
//        this.connectivity = ((ConnectivityManager)paramContext.getSystemService("connectivity"));
//        this.notificationManager = ((NotificationManager)paramContext.getSystemService("notification"));
//        this.activityManager = ((ActivityManager)paramContext.getSystemService("activity"));
//        if (this.phoneStateListener == null)
//        {
//            this.phoneStateListener = new MyPhoneStateListener(null);
//            this.telephonyManager.listen(this.phoneStateListener, 321);
//        }
//        this.agentLocationManager = AgentLocationManager.getInstance();
//        this.agentLocationManager.initialize(paramContext);
//        getOperatorType();
//        upgradeApnInfo();
//        this.getLocationTimer = null;
//        this.cpuUsageThread = new CpuUsageThread();
//        this.cpuUsageThread.start();
    }

    public void openWifi()
    {
        if (!this.wifiManager.isWifiEnabled())
            this.wifiManager.setWifiEnabled(true);
    }

    public void startScanWifi()
    {
        Log.d("SystemManager", String.valueOf(this.wifiManager.isWifiEnabled())+"/"+this.wifiManager.getWifiState());
        this.wifiManager.startScan();
//        this.wifiList = this.wifiManager.getScanResults();
//        this.wifiConfigurations = this.wifiManager.getConfiguredNetworks();
    }

    public List<ScanResult> getWifiList()
    {
        return this.wifiList;
    }

    public void setScanResults(){
        this.wifiList = this.wifiManager.getScanResults();
        this.wifiConfigurations = this.wifiManager.getConfiguredNetworks();
    }

}
