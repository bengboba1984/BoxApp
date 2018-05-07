package com.fkty.mobileiq.distribution.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.fragment.SettingOTTFragment;
import com.fkty.mobileiq.distribution.fragment.WifiDisconnectFragment;
import com.fkty.mobileiq.distribution.manager.MWifiManager;

public class SettingOTTActivity extends BaseActivity {

    private ImageView backImg;
    private FrameLayout frameLayout;
    private SettingOTTFragment settingOTTFragment;
    private TextView title;
    private WifiDisconnectFragment wifiDisconnectFragment;

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
    public void initView(View paramView) {
        this.backImg = (paramView.findViewById(R.id.vixtel_btn_back));
        this.title = (paramView.findViewById(R.id.vixtel_tv_title));
        this.frameLayout = paramView.findViewById(R.id.ott_connect_framelayout);
    }

    @Override
    public void initData() {
        this.title.setText(getString(R.string.settingOTT));
        this.settingOTTFragment = new SettingOTTFragment();
        this.wifiDisconnectFragment = new WifiDisconnectFragment();
    }

    @Override
    public void initParms(Bundle paramBundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_setting_ott;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void doBusiness(Context paramContext) {
        if (MWifiManager.getIntance().isConnect())
        {
            getFragmentManager().beginTransaction().replace(R.id.ott_connect_framelayout, this.settingOTTFragment).commit();
        }else{
            getFragmentManager().beginTransaction().replace(R.id.ott_connect_framelayout, this.wifiDisconnectFragment).commit();
        }

    }

    @Override
    public void setListener() {
        this.backImg.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View paramView) {
        switch (paramView.getId())
        {
            default:
                return;
            case R.id.vixtel_btn_back:
                finish();
                return;
        }
    }
    protected void onRestart()
    {
        super.onRestart();
        doBusiness(this);
    }
}
