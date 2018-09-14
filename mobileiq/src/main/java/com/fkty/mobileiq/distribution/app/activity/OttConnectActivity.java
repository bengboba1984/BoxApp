package com.fkty.mobileiq.distribution.app.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.view.EditView;
import com.fkty.mobileiq.distribution.view.LinearView;

import org.json.JSONException;
import org.json.JSONObject;

public class OttConnectActivity extends BaseActivity
        implements INetNotify
{
    private final long CONNECT_TIMEOUT = 30000L;
    private final int GET_SET_BRIDGE = 4;
    private final int GET_SET_BRIDGE_OFF = 5;
    private final int GET_SET_DHCP = 3;
    private final int GET_SET_PPPOE = 1;
    private final int GET_SET_STATIC = 2;
    private final int BRIDGE_ON = 1;
    private final int BRIDGE_OFF = 0;
    private ImageView backImg;
    private Button commit;
    private String connectType;
    private LinearView dhcpView;
    private LinearView bridgeView;
    private String ppoePwdString = "";
    private String ppoeUserString = "";
    private EditView pppoePwdView;
    private EditView pppoeUserView;
    private LinearView pppoeView;
    private ProgressDialog progressBar;
    private String staticDnsString = "";
    private EditView staticDnsView;
    private String staticGateString = "";
    private EditView staticGateView;
    private EditView staticIPView;
    private String staticIpString = "";
    private String staticSubnetString = "";
    private EditView staticSubnetView;
    private LinearView staticView;
    private TextView title;

    public int bindLayout()
    {
        return R.layout.activity_ott_connect;
    }

    public View bindView()
    {
        return null;
    }

    public void controlDialog(int paramInt, String paramString)
    {
    }

    public void doBusiness(Context paramContext)
    {
        this.title.setText(getString(R.string.ott_connect_info));
    }

    public void initData()
    {
        this.pppoeView.setTitleText("PPPoe");
        this.pppoeUserView.setTitleText("用户名");
        this.ppoeUserString = DataManager.getInstance().getPppoeUser();
        this.pppoePwdView.setTitleText("密码");
        this.ppoePwdString = DataManager.getInstance().getPppoePwd();

        this.staticView.setTitleText("设置静态IP");
        this.staticIPView.setTitleText("IP地址");
        this.staticIpString = DataManager.getInstance().getStaticIP();
        this.staticSubnetView.setTitleText("子网掩码");
        this.staticSubnetString = DataManager.getInstance().getStaticSubNet();
        this.staticGateView.setTitleText("网关");
        this.staticGateString = DataManager.getInstance().getStaticGate();
        this.staticDnsView.setTitleText("DNS");
        this.staticDnsString = DataManager.getInstance().getStaticDNS();

        this.dhcpView.setTitleText("DHCP");
        this.bridgeView.setTitleText("桥接");

        this.connectType = DataManager.getInstance().getOotConnectType();

        resetFalse();

        if (CommonField.PPPOE.equals(this.connectType)){
            this.pppoeView.setSelected(true);
            this.pppoeUserView.setEditEnable(true);
            this.pppoePwdView.setEditEnable(true);
            if ((DataManager.getInstance().getPppoeUser() != null) && (DataManager.getInstance().getPppoeUser().length() > 0)){
                this.pppoeUserView.setEditText(this.ppoeUserString);
            }
            if ((DataManager.getInstance().getPppoePwd() != null) && (DataManager.getInstance().getPppoePwd().length() > 0)){
                this.pppoePwdView.setEditText(this.ppoePwdString);
            }
        }else if (CommonField.STATICIP.equals(this.connectType)){
            this.staticView.setSelected(true);
            this.staticDnsView.setEditEnable(true);
            this.staticSubnetView.setEditEnable(true);
            this.staticGateView.setEditEnable(true);
            this.staticIPView.setEditEnable(true);
            if(DataManager.getInstance().getStaticDNS()!=null && DataManager.getInstance().getStaticDNS().length()>0){
                this.staticDnsView.setEditText(this.staticDnsString);
            }
            if(DataManager.getInstance().getStaticGate()!=null && DataManager.getInstance().getStaticGate().length()>0){
                this.staticGateView.setEditText(this.staticGateString);
            }
            if(DataManager.getInstance().getStaticIP()!=null && DataManager.getInstance().getStaticIP().length()>0){
                this.staticIPView.setEditText(this.staticIpString);
            }
            if(DataManager.getInstance().getStaticSubNet()!=null && DataManager.getInstance().getStaticSubNet().length()>0){
                this.staticSubnetView.setEditText(this.staticSubnetString);
            }
        }else if (CommonField.DHCP.equals(this.connectType)){
            this.dhcpView.setSelected(true);
        }else if (CommonField.BRIDGE.equals(this.connectType)){
            this.bridgeView.setSelected(true);
        }

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

    public void initParms(Bundle paramBundle)
    {
    }

    public void initView(View paramView)
    {
        this.title = paramView.findViewById(R.id.vixtel_tv_title);
        this.backImg = paramView.findViewById(R.id.vixtel_btn_back);
        this.pppoeView = paramView.findViewById(R.id.ott_connect_pppoe);
        this.staticView = paramView.findViewById(R.id.ott_connect_static);
        this.dhcpView = paramView.findViewById(R.id.ott_connect_dhcp);
        this.bridgeView = paramView.findViewById(R.id.ott_connect_bridge);
        this.pppoeUserView = paramView.findViewById(R.id.ott_connect_pppoe_user);
        this.pppoePwdView = paramView.findViewById(R.id.ott_connect_pppoe_pwd);
        this.staticSubnetView = paramView.findViewById(R.id.ott_connect_static_subnet);
        this.staticIPView = paramView.findViewById(R.id.ott_connect_static_ip);
        this.staticDnsView = paramView.findViewById(R.id.ott_connect_static_dns);
        this.staticGateView = paramView.findViewById(R.id.ott_connect_static_gate);
        this.commit = paramView.findViewById(R.id.ott_connect_btn);
    }

    public void onDataUpdate(Bundle paramBundle)
    {
    }

    public void onErrorNetClient(int paramInt, String paramString)
    {
        if (this.progressBar.isShowing()){
            this.progressBar.dismiss();
        }
        switch (paramInt){
            default:
                break;
            case GET_SET_PPPOE:
                showToast("设置PPPOE拨号错误,错误信息：" + paramString);
                break;
            case GET_SET_STATIC:
                showToast("设置静态IP错误,错误信息：" + paramString);
                break;
            case GET_SET_DHCP:
                showToast("设置DHCP错误,错误信息：" + paramString);
                break;
            case GET_SET_BRIDGE:
                showToast("设置桥接错误,错误信息：" + paramString);
                break;
            case GET_SET_BRIDGE_OFF:
                showToast("关闭桥接错误,错误信息：" + paramString);
                break;
        }
    }

    public void onFailedNetClient(int paramInt, String paramString)
    {
    }

    public void onSuccessNetClient(int paramInt, String paramString)
    {
        if (this.progressBar.isShowing()){
            this.progressBar.dismiss();
        }
        if(paramString!=null && paramString.length()>0){
            try {
                Log.d(TAG,"paramString="+paramString.substring(paramString.indexOf("{",0),paramString.lastIndexOf("}")+1));
                paramString=paramString.substring(paramString.indexOf("{",0),paramString.lastIndexOf("}")+1);
                JSONObject localJSONObject1 = new JSONObject(paramString);
                String str = localJSONObject1.optString("state");
                int i=-1;
                i = localJSONObject1.optInt(ServerErrorCode.ERROR_CODE);
                switch (i){
                    default:
                        showToast("未知错误！");
                        break;
                    case ServerErrorCode.ERROR_CODE_SUCCESS:
                        switch (paramInt){
                            default:
                                break;
                            case GET_SET_PPPOE:
                                DataManager.getInstance().setPppoeUser(this.ppoeUserString);
                                DataManager.getInstance().setPppoePwd(this.ppoePwdString);
                                showToast("设置成功");
                                break;
                            case GET_SET_STATIC:
                                DataManager.getInstance().setStaticIP(this.staticIpString);
                                DataManager.getInstance().setStaticGate(this.staticGateString);
                                DataManager.getInstance().setStaticDNS(this.staticDnsString);
                                DataManager.getInstance().setStaticSubNet(this.staticSubnetString);
                                showToast("设置成功");
                                break;
                            case GET_SET_DHCP:
                                showToast("设置成功");
                                break;
                            case GET_SET_BRIDGE:
                                showToast("设置成功");
                                break;
                            case GET_SET_BRIDGE_OFF:
                                DataManager.getInstance().setOotConnectType(this.connectType);
                                if((CommonField.PPPOE).equals(this.connectType)){
                                    this.progressBar.setMessage("设置PPPOE");
                                    this.ppoePwdString = this.pppoePwdView.getEditText();
                                    this.ppoeUserString = this.pppoeUserView.getEditText();
                                    if ((this.ppoePwdString != null) && (this.ppoePwdString.length() > 0) && (this.ppoeUserString != null) && (this.ppoeUserString.length() > 0)){
                                        if (!this.progressBar.isShowing()){
                                            this.progressBar.show();
                                        }
                                        WebHttpUtils.getInstance().setPPPoe(this.ppoeUserString, this.ppoePwdString, this, GET_SET_PPPOE);
                                    }else{
                                        showToast("用户名或密码不能为空");
                                    }
                                }else if((CommonField.STATICIP).equals(this.connectType)){
                                    this.staticIpString = this.staticIPView.getEditText();
                                    this.staticSubnetString = this.staticSubnetView.getEditText();
                                    this.staticGateString = this.staticGateView.getEditText();
                                    this.staticDnsString = this.staticDnsView.getEditText();
                                    if ((this.staticIpString != null) && (this.staticIpString.length() > 0)
                                            && (this.staticSubnetString != null) && (this.staticSubnetString.length() > 0)
                                            && (this.staticGateString != null) && (this.staticGateString.length() > 0)
                                            && (this.staticDnsString != null) && (this.staticDnsString.length() > 0)){
                                        this.progressBar.setMessage("设置静态IP");
                                        if (!this.progressBar.isShowing()){
                                            this.progressBar.show();
                                        }
                                        WebHttpUtils.getInstance().setStaticIP(this.staticIpString, this.staticGateString, this.staticDnsString, this.staticSubnetString, this, GET_SET_STATIC);
                                    }else{
                                        showToast("静态Ip设置4项均不能为空");
                                    }
                                }else if((CommonField.DHCP).equals(this.connectType)){
                                    this.progressBar.setMessage("设置DHCP");
                                    WebHttpUtils.getInstance().setDHCP(this, GET_SET_DHCP);
                                }
                                if (!this.progressBar.isShowing())
                                    this.progressBar.show();
                                break;
                        }
                        break;
                    case ServerErrorCode.ERROR_CODE_EMPTY:
                        showToast(str);
                        break;
                    case ServerErrorCode.ERROR_CODE_SETTING:
                        showToast(str);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetFalse()
    {
        this.pppoeView.setSelected(false);
        this.dhcpView.setSelected(false);
        this.bridgeView.setSelected(false);
        this.staticView.setSelected(false);
        this.pppoeUserView.setEditEnable(false);
        this.pppoePwdView.setEditEnable(false);
        this.staticDnsView.setEditEnable(false);
        this.staticGateView.setEditEnable(false);
        this.staticIPView.setEditEnable(false);
        this.staticSubnetView.setEditEnable(false);
    }

    public void setListener()
    {
        this.backImg.setOnClickListener(this);
        this.pppoeView.setOnClickListener(this);
        this.staticView.setOnClickListener(this);
        this.dhcpView.setOnClickListener(this);
        this.bridgeView.setOnClickListener(this);
        this.commit.setOnClickListener(this);
    }

    public void toastMsg(String paramString)
    {
    }

    public void widgetClick(View paramView)
    {
        switch (paramView.getId())
        {
            default:
                break;
            case R.id.vixtel_btn_back:
                finish();
                break;
            case R.id.ott_connect_dhcp:
                resetFalse();
                this.connectType = CommonField.DHCP;
                this.dhcpView.setSelected(true);
                break;
            case R.id.ott_connect_bridge:
                resetFalse();
                this.connectType = CommonField.BRIDGE;
                this.bridgeView.setSelected(true);
                break;
            case R.id.ott_connect_static:
                resetFalse();
                this.connectType = CommonField.STATICIP;
                this.staticView.setSelected(true);
                this.staticDnsView.setEditEnable(true);
                this.staticSubnetView.setEditEnable(true);
                this.staticIPView.setEditEnable(true);
                this.staticGateView.setEditEnable(true);
                break;
            case R.id.ott_connect_pppoe:
                resetFalse();
                this.connectType = CommonField.PPPOE;
                this.pppoeView.setSelected(true);
                this.pppoePwdView.setEditEnable(true);
                this.pppoeUserView.setEditEnable(true);
                break;
            case R.id.ott_connect_btn:
                if(DataManager.getInstance().getOotConnectType()!=null
                        && CommonField.BRIDGE.equals(DataManager.getInstance().getOotConnectType())
                        && !CommonField.BRIDGE.equals(this.connectType)){
                    //原本盒子为桥接模式，切换到其他连接模式
                    WebHttpUtils.getInstance().setBridge(this, GET_SET_BRIDGE_OFF,BRIDGE_OFF);
                    this.progressBar.setMessage("关闭桥接");
                    if (!this.progressBar.isShowing())
                        this.progressBar.show();
                    break;
                }else{
                    DataManager.getInstance().setOotConnectType(this.connectType);
                }

                if((CommonField.PPPOE).equals(this.connectType)){
                    this.progressBar.setMessage("设置PPPOE");
                    this.ppoePwdString = this.pppoePwdView.getEditText();
                    this.ppoeUserString = this.pppoeUserView.getEditText();
                    if ((this.ppoePwdString != null) && (this.ppoePwdString.length() > 0) && (this.ppoeUserString != null) && (this.ppoeUserString.length() > 0)){
                        if (!this.progressBar.isShowing()){
                            this.progressBar.show();
                        }
                        WebHttpUtils.getInstance().setPPPoe(this.ppoeUserString, this.ppoePwdString, this, GET_SET_PPPOE);
                    }else{
                        showToast("用户名或密码不能为空");
                    }
                }else if((CommonField.STATICIP).equals(this.connectType)){
                    this.staticIpString = this.staticIPView.getEditText();
                    this.staticSubnetString = this.staticSubnetView.getEditText();
                    this.staticGateString = this.staticGateView.getEditText();
                    this.staticDnsString = this.staticDnsView.getEditText();
                    if ((this.staticIpString != null) && (this.staticIpString.length() > 0)
                            && (this.staticSubnetString != null) && (this.staticSubnetString.length() > 0)
                            && (this.staticGateString != null) && (this.staticGateString.length() > 0)
                            && (this.staticDnsString != null) && (this.staticDnsString.length() > 0)){
                        this.progressBar.setMessage("设置静态IP");
                        if (!this.progressBar.isShowing()){
                            this.progressBar.show();
                        }
                        WebHttpUtils.getInstance().setStaticIP(this.staticIpString, this.staticGateString, this.staticDnsString, this.staticSubnetString, this, GET_SET_STATIC);
                    }else{
                        showToast("静态Ip设置4项均不能为空");
                    }
                }else if((CommonField.DHCP).equals(this.connectType)){
                    this.progressBar.setMessage("设置DHCP");
                    WebHttpUtils.getInstance().setDHCP(this, GET_SET_DHCP);
                }else if((CommonField.BRIDGE).equals(this.connectType)){
                    this.progressBar.setMessage("设置桥接");
                    WebHttpUtils.getInstance().setBridge(this, GET_SET_BRIDGE,BRIDGE_ON);
                }
                if (!this.progressBar.isShowing())
                    this.progressBar.show();
                break;

        }
    }

}