package com.fkty.mobileiq.distribution.app.activity;


import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.manager.MWifiManager;

public class SSIDActivity extends BaseActivity implements INetNotify,View.OnClickListener {
    private final String TAG="SSIDActivity";
    private Button btn;
    private EditText ssidNamePre;
    private EditText ssidName;
    private ImageView mBackButton;
    private TextView mTitile;
    private final int SET_SSID = 1;
//    private ProgressDialog progressBar;
    @Override
    public void controlDialog(int paramInt, String paramString) {

    }

    @Override
    public void onDataUpdate(Bundle paramBundle) {

    }

    @Override
    public void toastMsg(String paramString) {

    }

    @Override
    public void onErrorNetClient(int paramInt, String paramString) {
        showToast("设置成功!");
        finish();
    }

    @Override
    public void onFailedNetClient(int paramInt, String paramString) {

    }

    @Override
    public void onSuccessNetClient(int paramInt, String paramString) {
        showToast("设置成功!");
        finish();
    }

    @Override
    public void initView(View paramView) {
        this.mTitile = findViewById(R.id.vixtel_tv_title);
        this.mBackButton = findViewById(R.id.vixtel_btn_back);
        this.btn = findViewById(R.id.setting_ssid_btn);
        this.ssidName = findViewById(R.id.ssid_name);
        this.ssidNamePre = findViewById(R.id.ssid_name_pre);
        this.ssidNamePre.setInputType(InputType.TYPE_NULL);

    }

    @Override
    public void initData() {

        this.ssidNamePre.setText(CommonField.SSID_PRE+"_");
        String conSSID=MWifiManager.getIntance().getSSID().replace("\"","");
        if(CommonField.SSID_PRE.equals(conSSID)){
        }else{
            Log.d(TAG,"conSSID.replace()="+conSSID.replace(CommonField.SSID_PRE+"_",""));
            ssidName.setText(conSSID.replace(CommonField.SSID_PRE+"_",""));
        }
    }

    @Override
    public void initParms(Bundle paramBundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_ssid;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void doBusiness(Context paramContext) {

    }

    @Override
    public void setListener() {
        mBackButton.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View paramView) {
        switch(paramView.getId())
        {
            default:
                break;
            case R.id.vixtel_btn_back:
                finish();
                break;
            case R.id.setting_ssid_btn:
                String ssid = this.ssidName.getText().toString().trim();
                if ((ssid == null) || (ssid.length() < 1))
                {
                    showToast("平台地址不能为空");
                    break;
                }
                ssid=this.ssidNamePre.getText()+ssid;
//                this.progressBar.setMessage("设置服务器地址");
//                if (!this.progressBar.isShowing())
//                    this.progressBar.show();
                Log.d(TAG,"ssid="+ssid);
                WebHttpUtils.getInstance().setSSID(this, SET_SSID, ssid);
                break;
        }
    }
}
