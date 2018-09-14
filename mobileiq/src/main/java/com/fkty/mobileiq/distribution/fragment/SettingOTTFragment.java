package com.fkty.mobileiq.distribution.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.app.activity.OttConnectActivity;
import com.fkty.mobileiq.distribution.app.activity.SSIDActivity;
import com.fkty.mobileiq.distribution.bean.OttSettingInfoBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.ui.MyListview;
import com.fkty.mobileiq.distribution.ui.adapter.OTTSettingAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SettingOTTFragment extends Fragment implements View.OnClickListener, Runnable,INetNotify{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final int REBOOT = 2;
    private final int RESTORE = 1;
    private OTTSettingAdapter connectAdapter;
    private List<OttSettingInfoBean> connectList;
    private OTTSettingAdapter deviceAdapter;
    private List<OttSettingInfoBean> deviceList;
    private MyListview ottConnectListView;
    private TextView ottConnectType;
    private MyListview ottDeviceInfoListView;
    private TextView ottReset;
    private TextView ottRestart;
    private TextView ottSSID;
    private TextView ottReviceConnect;
    private ProgressDialog progressBar;
    private ScheduledExecutorService service = null;
    private View view;
    private Handler handler = new Handler()
    {
        public void handleMessage(Message paramMessage)
        {
            super.handleMessage(paramMessage);
            SettingOTTFragment.this.initData();
        }
    };

    private void initView()
    {
        this.ottConnectListView = this.view.findViewById(R.id.ott_connect_type_listview);
        this.ottConnectType = this.view.findViewById(R.id.ott_connect_type);
        this.ottDeviceInfoListView = this.view.findViewById(R.id.ott_device_info_listview);
        this.ottReset = this.view.findViewById(R.id.ott_reset);
        this.ottRestart = this.view.findViewById(R.id.ott_restart);
        this.ottSSID = this.view.findViewById(R.id.ott_ssid);
        this.ottReviceConnect = this.view.findViewById(R.id.ott_connect_type_revise);
    }

    public void initData()
    {
        this.ottConnectType.setText(DataManager.getInstance().getOotConnectType());
        this.connectList = new ArrayList();
        if(CommonField.DHCP.equals(this.ottConnectType.getText())){

        }else if(CommonField.PPPOE.equals(this.ottConnectType.getText())){
            OttSettingInfoBean localOttSettingInfoBean1 = new OttSettingInfoBean();
            localOttSettingInfoBean1.setName(getString(R.string.ott_setting_connect_pppoe_user));
            localOttSettingInfoBean1.setContent(DataManager.getInstance().getPppoeUser());
            this.connectList.add(localOttSettingInfoBean1);
            OttSettingInfoBean localOttSettingInfoBean2 = new OttSettingInfoBean();
            localOttSettingInfoBean2.setName(getString(R.string.ott_setting_connect_pppoe_pwd));
            localOttSettingInfoBean2.setContent(DataManager.getInstance().getPppoePwd());
            this.connectList.add(localOttSettingInfoBean2);
        }else if(CommonField.STATICIP.equals(this.ottConnectType.getText())){
            OttSettingInfoBean localOttSettingInfoBean11 = new OttSettingInfoBean();
            localOttSettingInfoBean11.setName(getString(R.string.ott_setting_connect_static_ip));
            localOttSettingInfoBean11.setContent(DataManager.getInstance().getStaticIP());
            this.connectList.add(localOttSettingInfoBean11);
            OttSettingInfoBean localOttSettingInfoBean12 = new OttSettingInfoBean();
            localOttSettingInfoBean12.setName(getString(R.string.ott_setting_connect_static_subnet));
            localOttSettingInfoBean12.setContent(DataManager.getInstance().getStaticSubNet());
            this.connectList.add(localOttSettingInfoBean12);
            OttSettingInfoBean localOttSettingInfoBean13 = new OttSettingInfoBean();
            localOttSettingInfoBean13.setName(getString(R.string.ott_setting_connect_static_gate));
            localOttSettingInfoBean13.setContent(DataManager.getInstance().getStaticGate());
            this.connectList.add(localOttSettingInfoBean13);
            OttSettingInfoBean localOttSettingInfoBean14 = new OttSettingInfoBean();
            localOttSettingInfoBean14.setName(getString(R.string.ott_setting_connect_static_dns));
            localOttSettingInfoBean14.setContent(DataManager.getInstance().getStaticDNS());
            this.connectList.add(localOttSettingInfoBean14);
        }else if(CommonField.BRIDGE.equals(this.ottConnectType.getText())){

        }
        this.connectAdapter = new OTTSettingAdapter(getActivity(), this.connectList);
        this.ottConnectListView.setAdapter(this.connectAdapter);

        this.deviceList = new ArrayList();
        OttSettingInfoBean cpuBean = new OttSettingInfoBean();
        cpuBean.setName(getString(R.string.ott_setting_device_cpu));
        cpuBean.setContent(DataManager.getInstance().getCpu());
        OttSettingInfoBean deviceSeqBean = new OttSettingInfoBean();
        deviceSeqBean.setName(getString(R.string.ott_setting_device_seq));
        deviceSeqBean.setContent(DataManager.getInstance().getDeviceSeq());
        OttSettingInfoBean memoryBean = new OttSettingInfoBean();
        memoryBean.setName(getString(R.string.ott_setting_device_memory));
        memoryBean.setContent(DataManager.getInstance().getMemory());
        OttSettingInfoBean hardDiskBean = new OttSettingInfoBean();
        hardDiskBean.setName(getString(R.string.ott_setting_device_harddisk));
        hardDiskBean.setContent(DataManager.getInstance().getHardDisk());
        OttSettingInfoBean ipAddressBean=new OttSettingInfoBean();
        ipAddressBean.setName(getString(R.string.ott_setting_device_ipAddress));
        ipAddressBean.setContent(DataManager.getInstance().getStaticIP());
        OttSettingInfoBean subNetMaskBean=new OttSettingInfoBean();
        subNetMaskBean.setName(getString(R.string.ott_setting_device_subNetMask));
        subNetMaskBean.setContent(DataManager.getInstance().getStaticSubNet());
        OttSettingInfoBean gatewayBean=new OttSettingInfoBean();
        gatewayBean.setName(getString(R.string.ott_setting_device_gateway));
        gatewayBean.setContent(DataManager.getInstance().getStaticGate());
        this.deviceList.add(cpuBean);
        this.deviceList.add(memoryBean);
        this.deviceList.add(hardDiskBean);
        this.deviceList.add(deviceSeqBean);
        this.deviceList.add(ipAddressBean);
        this.deviceList.add(subNetMaskBean);
        this.deviceList.add(gatewayBean);
        this.deviceAdapter = new OTTSettingAdapter(getActivity(), this.deviceList);
        this.ottDeviceInfoListView.setAdapter(this.deviceAdapter);
    }

    public void onClick(View paramView)
    {
        switch (paramView.getId())
        {
            default:
                break;
            case R.id.ott_connect_type_revise:
                startActivity(new Intent(getActivity(), OttConnectActivity.class));
                break;
            case R.id.ott_connect_type:
                break;
            case R.id.ott_connect_type_listview:
                break;
            case R.id.ott_reset:
                AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(getActivity());
                localBuilder2.setTitle(R.string.ott_setting_reset);
                localBuilder2.setMessage(R.string.ott_setting_reset_info);
                localBuilder2.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                        WebHttpUtils.getInstance().setRestore(SettingOTTFragment.this, RESTORE);
                    }
                });
                localBuilder2.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                    }
                });
                localBuilder2.create().show();
                break;
            case R.id.ott_restart:
                AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(getActivity());
                localBuilder1.setTitle(R.string.ott_setting_restart);
                localBuilder1.setMessage(R.string.ott_setting_restart_info);
                localBuilder1.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                        WebHttpUtils.getInstance().setReboot(SettingOTTFragment.this, REBOOT);
                    }
                });
                localBuilder1.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                    }
                });
                localBuilder1.create().show();
                break;
            case R.id.ott_ssid:
                startActivity(new Intent(getActivity(), SSIDActivity.class));
                break;
        }
    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        this.view = paramLayoutInflater.inflate(R.layout.fragment_setting_ott, null);
        initView();
        initData();
        this.ottRestart.setOnClickListener(this);
        this.ottSSID.setOnClickListener(this);
        this.ottReset.setOnClickListener(this);
        this.ottReviceConnect.setOnClickListener(this);
        this.service = Executors.newSingleThreadScheduledExecutor();
        this.service.scheduleWithFixedDelay(this, 0, 1L, TimeUnit.SECONDS);
        return this.view;
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    public void onDestroyView()
    {
        super.onDestroyView();
        this.service.shutdown();
    }

    public void onErrorNetClient(int paramInt, String paramString)
    {
        switch (paramInt)
        {
            default:
                break;
            case RESTORE:
                Toast.makeText(getActivity(), "恢复出厂设置错误，错误信息：" + paramString, Toast.LENGTH_SHORT).show();
                break;
            case REBOOT:
                Toast.makeText(getActivity(), "重启盒子错误，错误信息：" + paramString, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void onFailedNetClient(int paramInt, String paramString)
    {
        switch (paramInt)
        {
            default:
                break;
            case RESTORE:
                Toast.makeText(getActivity(), "恢复出厂设置错误，错误信息：" + paramString, Toast.LENGTH_SHORT).show();
                break;
            case REBOOT:
                Toast.makeText(getActivity(), "重启盒子错误，错误信息：" + paramString, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onStart()
    {
        super.onStart();
    }

    public void onSuccessNetClient(int paramInt, String paramString)
    {
        int i = -1;
        if ((paramString != null) && (paramString.length() > 0)){
            JSONObject msg = null;
            try {
                msg = new JSONObject(paramString);
                i = msg.optInt("errorCode");
                switch (i)
                {
                    default:
                        break;
                    case ServerErrorCode.ERROR_CODE_NO_ROOT:
                        Toast.makeText(getActivity(), "盒子没有root权限", Toast.LENGTH_SHORT).show();
                        break;
                    case ServerErrorCode.ERROR_CODE_NOT_CREATE_FILE:
                        Toast.makeText(getActivity(), "创建临时文件失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void run()
    {
        this.handler.sendEmptyMessage(1);
    }
}
