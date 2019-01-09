package com.fkty.mobileiq.distribution.manager;

import android.util.Log;

import com.fkty.mobileiq.distribution.core.ICoreListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by frank_tracy on 2018/3/8.
 */

public class OTTProperty implements ICoreListener {
    private static OTTProperty ottProperty;
    private boolean allowedStopCapture=true;

    public static OTTProperty getInstance()
    {
        if (ottProperty == null)
            ottProperty = new OTTProperty();
        return ottProperty;
    }

    @Override
    public boolean onCoreMessage(String paramString) {
        Log.d("OTTProperty","onCoreMessage="+paramString);
//        paramString="{'configResult': " +
//                "{'BoxDeviceInfo': " +
//                "{'userName': 1,'cpu': 2,'hardDisk': 3,'deviceSeq': 4,'memory': 5,'ipAddress': 6,'gateway': 4,'subNetMask': 5,'dns': 6}" +
//                "},'url': 3" +
//                " }";

        if((paramString != null) && (paramString.length() > 0)){
            this.setAllowedStopCapture(true);
            try {
                JSONObject ottJSON = new JSONObject(paramString);
                if(ottJSON.optString("url")!=null && ottJSON.optString("url").trim().length()>0){
                    DataManager.getInstance().setUrl(ottJSON.optString("url"));
                }
                JSONObject configResult = ottJSON.optJSONObject("configResult");
                if ((configResult != null))
                {
                    JSONObject boxDeviceInfoJSON = configResult.optJSONObject("BoxDeviceInfo");
                    if(DataManager.getInstance().getAccount()==null || DataManager.getInstance().getAccount().length()<1){
                        DataManager.getInstance().setAccount(boxDeviceInfoJSON.optString("userName"));
                    }
                    DataManager.getInstance().setCpu(boxDeviceInfoJSON.optString("load"));//cpu load for past 5 minutes
                    DataManager.getInstance().setHardDisk(boxDeviceInfoJSON.optString("hardDisk"));
                    DataManager.getInstance().setDeviceSeq(boxDeviceInfoJSON.optString("deviceSeq"));
                    DataManager.getInstance().setMemory(boxDeviceInfoJSON.optString("memory"));
                    DataManager.getInstance().setStaticIP(boxDeviceInfoJSON.optString("ipAddress"));
                    DataManager.getInstance().setStaticGate(boxDeviceInfoJSON.optString("gateway"));
                    DataManager.getInstance().setStaticSubNet(boxDeviceInfoJSON.optString("subNetMask"));
                    DataManager.getInstance().setStaticDNS(boxDeviceInfoJSON.optString("dns"));
                    DataManager.getInstance().setMschapErrcode(boxDeviceInfoJSON.optString("mschapErrcode"));
                    DataManager.getInstance().setOotConnectType(boxDeviceInfoJSON.optString("ottConnectType"));
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isAllowedStopCapture() {
        return allowedStopCapture;
    }

    public void setAllowedStopCapture(boolean allowedStopCapture) {
        this.allowedStopCapture = allowedStopCapture;
    }
}
