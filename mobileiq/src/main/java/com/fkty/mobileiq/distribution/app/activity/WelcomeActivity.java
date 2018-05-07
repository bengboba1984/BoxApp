package com.fkty.mobileiq.distribution.app.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.common.SystemManager;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.constant.WebServerConstant;
import com.fkty.mobileiq.distribution.http.HttpUtil;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.json.MainJsonUtil;
import com.fkty.mobileiq.distribution.manager.MWifiManager;
import com.fkty.mobileiq.distribution.manager.PermissionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class WelcomeActivity extends BaseActivity implements INetNotify {

    private final int GET_FOREGIN_SERVER = 1;
    private final int MY_REQUEST_CODE = 99;
    private final int GET_BOX_VERSION = 2;
    private final int UPGRAD_BOX_VERSION = 3;
    private final int REBOOT = 4;
    private final int GET_BOX_VERSION_ON_SERVER = 5;
    private String boxVersion = "";
    private String lastFileName = "";

    private Bundle bundle;
    private ProgressDialog progressBar;

    private Handler handler = new Handler()
    {
        public void handleMessage(Message paramMessage)
        {
//            super.handleMessage(paramMessage);
            switch (paramMessage.what) {
                default:
                    break;
                case -1:
                   // showToast("获取foreginServer失败," + paramMessage.obj);
                    Log.d(this.getClass().getName(),"net error============");
                    break;
                case 1:
                    //showToast("获取foreginServer成功," + paramMessage.obj);
                    MainJsonUtil.parseForeginServer(paramMessage.obj + "");
                    break;
            }
//            if (PermissionManager.getInstance().needRequestPermission()) {
//                checkPermission();
//            }
//            if (!MWifiManager.getIntance().isConnect())
//            {
//                SystemManager.getInstance().openWifi();
////                WelcomeActivity.this.bundle.putBoolean("isSuccess", true);
//            }

            upgradeBoxVersion();
        }
    };
    private void upgradeBoxVersion(){
        Log.d(TAG,"@@@@@@@@@@@@@@@@@@@@@");
        if (!MWifiManager.getIntance().isConnect()){
            Toast.makeText(this, getString(R.string.wifi_disconnect_4upgrade), Toast.LENGTH_LONG).show();
            startActivity(LoginActivity.class);
            WelcomeActivity.this.finish();
        }else{
            Log.d(TAG,"@@@@@@@@@@@@@@@@@@@");
            WebHttpUtils.getInstance().getBoxVersion(GET_BOX_VERSION,this);
        }

    }

//    private void checkPermission()
//    {
//        Log.d("hello", "checkPermission");
//        String[] arrayOfString1 = { "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION" };
//        String[] arrayOfString2 = PermissionManager.getInstance().checkPermisson(this, arrayOfString1);
//        if (arrayOfString2[0]!=null || arrayOfString2[1]!=null )
//        {
//            Log.d("hello", "regist Permission");
//            ActivityCompat.requestPermissions(this, arrayOfString2, MY_REQUEST_CODE);
//          //  return false;
//        }
//      //  return true;
//
//    }
    @Override
    public void initParms(Bundle paramBundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void initData() {
        if (this.progressBar == null)
        {
            this.progressBar = new ProgressDialog(this);
            this.progressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.progressBar.setIndeterminate(true);
            this.progressBar.setOnCancelListener(new DialogInterface.OnCancelListener()
            {
                public void onCancel(DialogInterface paramDialogInterface)
                {
                }
            });
            this.progressBar.setCancelable(false);
        }
    }

    @Override
    public void doBusiness(Context paramContext) {
        this.bundle = new Bundle();
    }

    @Override
    public void initView(View paramView) {

    }


    @Override
    public void controlDialog(int paramInt, String paramString) {

    }

    @Override
    public void onDataUpdate(Bundle paramBundle) {

    }

    @Override
    public void onErrorNetClient(int paramInt, String paramString) {

    }

    @Override
    public void onFailedNetClient(int paramInt, String paramString) {
        switch (paramInt){
            case GET_FOREGIN_SERVER:
                Log.d(this.getClass().getName(),"onFailedNetClient======/"+paramString);
                Message localMessage = Message.obtain();
                localMessage.what = -1;
                localMessage.obj = paramString;
                this.handler.sendMessage(localMessage);
                break;
            case GET_BOX_VERSION:

                startActivity(LoginActivity.class);
                WelcomeActivity.this.finish();
                break;
            case UPGRAD_BOX_VERSION:
                Log.d(TAG,"UPGRAD_BOX_VERSION:onFailedNetClient");
                if (this.progressBar.isShowing())
                    this.progressBar.dismiss();
                startActivity(LoginActivity.class);
                WelcomeActivity.this.finish();
                break;
            case GET_BOX_VERSION_ON_SERVER:
                if (this.progressBar.isShowing())
                    this.progressBar.dismiss();
                startActivity(LoginActivity.class);
                WelcomeActivity.this.finish();
                break;
        }

    }

    public void onSuccessNetClient(int paramInt, String paramString)
    {
        switch (paramInt){
            case GET_FOREGIN_SERVER:
                Log.d(this.getClass().getName(),"onSuccessNetClient======");
                Message localMessage = Message.obtain();
                localMessage.what = 1;
                localMessage.obj = paramString;
                this.handler.sendMessage(localMessage);
                break;
            case GET_BOX_VERSION:
                JSONObject boxVersionJson = null;
                try {
                    boxVersionJson = new JSONObject(paramString);
                    if (boxVersionJson.optInt("errorCode") == ServerErrorCode.ERROR_CODE_SUCCESS){
                        this.boxVersion=boxVersionJson.optString("versionNum");
                        Log.d(TAG,"box version="+boxVersion);

                        WebHttpUtils.getInstance().getBoxVersionOnServer(GET_BOX_VERSION_ON_SERVER,this);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case UPGRAD_BOX_VERSION:
                Log.d(TAG,"onSuccessNetClient:"+UPGRAD_BOX_VERSION);
                if (this.progressBar.isShowing())
                    this.progressBar.dismiss();
                startActivity(LoginActivity.class);
                WelcomeActivity.this.finish();
                break;
            case GET_BOX_VERSION_ON_SERVER:
                String lastVersionNum="";
                JSONObject boxVersionOnServerJson = null;
                try {
                    boxVersionOnServerJson = new JSONObject(paramString);
                    lastVersionNum=boxVersionOnServerJson.optString("versionNum");
                    this.lastFileName=boxVersionOnServerJson.optString("fileName");
                    Log.d(TAG,"box version on server="+lastVersionNum+"/lastFileName="+this.lastFileName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if(this.boxVersion!=null && !this.boxVersion.equals(lastVersionNum)){
                    Dialog upgradeDialog=new AlertDialog.Builder(this).setTitle("固件升级")
                            .setMessage("当前版本："+this.boxVersion+",发现新版本："+lastVersionNum+",是否需要更新？")
                            .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setMessage("盒子升级中，请不要断开电源！！！");
                                    if (!progressBar.isShowing())
                                        progressBar.show();
                                    WebHttpUtils.getInstance().upgradeBoxVersion(WelcomeActivity.this,UPGRAD_BOX_VERSION, WebServerConstant.HTTP_GET_BOX_VERSION_URL+lastFileName);

//                                    startActivity(LoginActivity.class);
//                                    WelcomeActivity.this.finish();
                                }
                            }).setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    startActivity(LoginActivity.class);
                                    WelcomeActivity.this.finish();
                                }
                            }).create();
                    upgradeDialog.show();
                }else{
                    startActivity(LoginActivity.class);
                    WelcomeActivity.this.finish();
                }
                break;
        }

    }

    public void onRequestPermissionsResult(int paramInt,  String[] paramArrayOfString, int[] paramArrayOfInt) {
        super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfInt);
        Log.d("hello", "onRequestPermissionsResult");
        switch (paramInt) {
            default:
                return;
            case MY_REQUEST_CODE:

                if (((paramArrayOfString.length == 1) && (paramArrayOfInt[0] == PackageManager.PERMISSION_GRANTED))
                        || ((paramArrayOfString.length == 2) && (paramArrayOfInt[0] == PackageManager.PERMISSION_GRANTED)
                        && (paramArrayOfInt[1] == PackageManager.PERMISSION_GRANTED))) {
                    Toast.makeText(this, "permission allow", Toast.LENGTH_LONG).show();
                    if (MWifiManager.getIntance().ConnectWifi()) {
                        this.bundle.putBoolean("isSuccess", true);
                    } else {
                        this.bundle.putBoolean("isSuccess", false);
                    }
                    startActivity(LoginActivity.class);
                    finish();
                } else {
                    Toast.makeText(this, "permission deny", Toast.LENGTH_LONG).show();
//                    this.bundle.putBoolean("isSuccess", false);
//                    startActivity(LoginActivity.class);
                    finish();

                }
        }
    }

    protected void onResume()
    {
        super.onResume();

    }

    public void setListener()
    {
    }

    public void toastMsg(String paramString)
    {
    }

    public void widgetClick(View paramView)
    {
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        HttpUtil.getInstance().getForeginServer(GET_FOREGIN_SERVER, this);

        super.onCreate(paramBundle);
    }
}
