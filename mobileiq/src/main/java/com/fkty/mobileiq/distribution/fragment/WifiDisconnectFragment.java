package com.fkty.mobileiq.distribution.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;

/**
 * Created by frank_tracy on 2017/12/11.
 */

public class WifiDisconnectFragment extends Fragment implements View.OnClickListener {
    private TextView setting;
    private View view;

    private void initData()
    {
    }

    private void initView()
    {
        this.setting = this.view.findViewById(R.id.wifi_disconnect_setting);
        this.setting.setOnClickListener(this);
    }

    public void onClick(View paramView)
    {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.wifi_disconnect_setting:

                Intent localIntent = new Intent();
                localIntent.setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
                startActivity(localIntent);
                return;
        }
    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        this.view = paramLayoutInflater.inflate(R.layout.wifi_disconnect, null);
        initView();
        initData();
        return this.view;
    }
}
