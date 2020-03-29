package com.fkty.mobileiq.distribution.manager;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.fkty.mobileiq.distribution.core.ICoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

import static com.fkty.mobileiq.distribution.constant.ServerErrorCode.MSCHAP_ERROR_CODE_10;
import static com.fkty.mobileiq.distribution.constant.ServerErrorCode.MSCHAP_ERROR_CODE_11;
import static com.fkty.mobileiq.distribution.constant.ServerErrorCode.MSCHAP_ERROR_CODE_12;
import static com.fkty.mobileiq.distribution.constant.ServerErrorCode.MSCHAP_ERROR_CODE_4;

/**
 * Created by frank_tracy on 2018/3/8.
 */

public class OTTProperty implements ICoreListener {
    private static OTTProperty ottProperty;
//    private boolean allowedStopCapture=true;

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
//            this.setAllowedStopCapture(true);
            try {
                Application application= (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
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

                    DataManager.getInstance().setAppVersion(application.getPackageManager().getPackageInfo(application.getPackageName(), 0).versionName);

                    DataManager.getInstance().setMemory(boxDeviceInfoJSON.optString("memory"));
                    DataManager.getInstance().setStaticIP(boxDeviceInfoJSON.optString("ipAddress"));
                    DataManager.getInstance().setStaticGate(boxDeviceInfoJSON.optString("gateway"));
                    DataManager.getInstance().setStaticSubNet(boxDeviceInfoJSON.optString("subNetMask"));
                    DataManager.getInstance().setStaticDNS(boxDeviceInfoJSON.optString("dns"));

                    if(boxDeviceInfoJSON.optString("mschapErrcode")!=null && boxDeviceInfoJSON.optString("mschapErrcode").trim().length()>0){
                        if(boxDeviceInfoJSON.optString("ottConnectReason")!=null && boxDeviceInfoJSON.optString("ottConnectReason").trim().length()>0){
                            DataManager.getInstance().setOttConnectReason(boxDeviceInfoJSON.optString("ottConnectReason"));
                        }
                        String mschapErrcode=boxDeviceInfoJSON.optString("mschapErrcode");

                        boolean needShowMessage=false;

                        if(!mschapErrcode.equals(DataManager.getInstance().getMschapErrcode())){
                            needShowMessage=true;
                            DataManager.getInstance().setShowTwice4PPPOE(true);
                        }else{
                            if(DataManager.getInstance().isShowTwice4PPPOE()){
                                DataManager.getInstance().setShowTwice4PPPOE(false);
                                needShowMessage=true;
                            }
                        }

                        DataManager.getInstance().setMschapErrcode(mschapErrcode);
                        if(needShowMessage){
                            if(!MSCHAP_ERROR_CODE_12.equals(mschapErrcode)){
                                if(MSCHAP_ERROR_CODE_10.equals(mschapErrcode) ){
                                    Toast.makeText(application,"pppoe 密码错误!" , Toast.LENGTH_LONG).show();
//                                    Thread.sleep(2000);
//                                    Toast.makeText(application,"pppoe 密码错误!" , Toast.LENGTH_LONG).show();
                                }else if(MSCHAP_ERROR_CODE_4.equals(mschapErrcode)){
                                    Toast.makeText(application,"pppoe 拨号成功，连接已经建立!" , Toast.LENGTH_LONG).show();
//                                    Thread.sleep(2000);
//                                    Toast.makeText(application,"pppoe 拨号成功，连接已经建立!" , Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(application,"Warning:"+DataManager.getInstance().getOttConnectReason() , Toast.LENGTH_LONG).show();
//                                    Thread.sleep(2000);
//                                    Toast.makeText(application,"Warning:"+DataManager.getInstance().getOttConnectReason() , Toast.LENGTH_LONG).show();
//                                    Toast tt=Toast.makeText(application,DataManager.getInstance().getOttConnectReason() , Toast.LENGTH_LONG);
//                                    tt.setGravity(Gravity.CENTER, 0, 0);
//                                    tt.show();
                                }
                            }
                        }


//                        if(DataManager.getInstance().getMschapErrcode()!=null && !"".equals(DataManager.getInstance().getMschapErrcode())){
//                            if(!DataManager.getInstance().getMschapErrcode().equals(boxDeviceInfoJSON.optString("mschapErrcode"))){
//                                DataManager.getInstance().setMschapErrcode(boxDeviceInfoJSON.optString("mschapErrcode"));
//                                if(boxDeviceInfoJSON.optString("ottConnectReason")!=null && ottJSON.optString("ottConnectReason").trim().length()>0){
//                                    DataManager.getInstance().setOttConnectReason(ottJSON.optString("ottConnectReason"));
//                                }
//                                //alert PPPOE error
//                                Toast.makeText(application, DataManager.getInstance().getPPPOEErrorMessage(DataManager.getInstance().getMschapErrcode()), Toast.LENGTH_LONG).show();
//                            }
//                        }else {
//                            DataManager.getInstance().setMschapErrcode(boxDeviceInfoJSON.optString("mschapErrcode"));
//                            if(boxDeviceInfoJSON.optString("ottConnectReason")!=null && ottJSON.optString("ottConnectReason").trim().length()>0){
//                                DataManager.getInstance().setOttConnectReason(ottJSON.optString("ottConnectReason"));
//                            }
//                        }
                    }


                    DataManager.getInstance().setOotConnectType(boxDeviceInfoJSON.optString("ottConnectType"));
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        return false;
    }

//    public boolean isAllowedStopCapture() {
//        return allowedStopCapture;
//    }
//
//    public void setAllowedStopCapture(boolean allowedStopCapture) {
//        this.allowedStopCapture = allowedStopCapture;
//    }
}
