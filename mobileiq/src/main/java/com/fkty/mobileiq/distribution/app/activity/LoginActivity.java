package com.fkty.mobileiq.distribution.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.app.view.ILoginView;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.bean.LoginInfo;
import com.fkty.mobileiq.distribution.common.SystemManager;
import com.fkty.mobileiq.distribution.constant.LoginConstant;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.manager.PermissionManager;
import com.fkty.mobileiq.distribution.ui.adapter.UnitListAdapter;
import com.fkty.mobileiq.distribution.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements ILoginView, LoginConstant, AdapterView.OnItemClickListener {
    private final int GET_FOREGIN_SERVER = 1;
    private ImageView backImg;
    private List<String> data;
    private ImageView icon;
    private EditText jobnumber;
    private Button loginBtn;
    private LoginInfo loginInfo;
    private ProgressDialog loginProgress;
    private EditText password;
//    private ILoginPresenter presenter;
    private TextView title;
    private TextView unit;
    private ImageView unitImg;
    private LinearLayout unitLayout;
    private EditText userName;
    private PopupWindow window;


    @Override
    public void initView(View paramView) {
        this.userName = paramView.findViewById(R.id.username);
        this.password = paramView.findViewById(R.id.password);
        this.jobnumber = paramView.findViewById(R.id.jobnumber);
        this.unit = paramView.findViewById(R.id.unit);
        this.unitImg = paramView.findViewById(R.id.unit_select);
        this.loginBtn = paramView.findViewById(R.id.login_btn);
        this.unitLayout = paramView.findViewById(R.id.unit_layout);
        this.backImg = paramView.findViewById(R.id.vixtel_btn_back);
        this.title = paramView.findViewById(R.id.vixtel_tv_title);
        this.icon = paramView.findViewById(R.id.vixtel_btn_icon);
        this.icon.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
//        this.presenter = new LoginPresenter(this);
        this.loginInfo = new LoginInfo();
        if (this.loginProgress == null)
        {
            this.loginProgress = new ProgressDialog(this);
            this.loginProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.loginProgress.setIndeterminate(true);
//            this.loginProgress.setOnCancelListener(new DialogInterface.OnCancelListener()
//            {
//                public void onCancel(DialogInterface paramDialogInterface)
//                {
//                    LoginActivity.this.presenter.cancelLogin();
//                }
//            });
            this.loginProgress.setCancelable(false);
        }
    }

    @Override
    public void initParms(Bundle paramBundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void doBusiness(Context paramContext) {
        this.backImg.setVisibility(View.GONE);
        this.title.setText(getString(R.string.app_name));
        if (Preferences.contains(this, LoginConstant.USERNAME))
        {
            this.userName.setText(Preferences.get(this, LoginConstant.USERNAME, "") + "");
            if (Preferences.contains(this, "password"))
                this.password.setText(Preferences.get(this, LoginConstant.PASSWORD, "") + "");
            if (Preferences.contains(this, "jobnumber"))
                this.jobnumber.setText(Preferences.get(this, LoginConstant.JOBNUMBER, "") + "");
        }
    }

    @Override
    public void setListener() {
        this.loginBtn.setOnClickListener(this);
        this.unitImg.setOnClickListener(this);
        this.unitLayout.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View paramView) {
        switch (paramView.getId())
        {
            case R.id.login_btn:
                if (!checkLoginInfo())return;
                controlDialog(DIALOG_SHOW, "开始登陆");


                //for test
                this.loginInfo=getLoginInfo();
                DataManager.getInstance().setLoginInfo(this.loginInfo);



               // this.presenter.startLogin(1, getLoginInfo());
                controlDialog(DIALOG_DISMISS,null);
                startActivity(MainActivity.class);
                return;
            case R.id.unit_layout:
                if (this.window == null)
                {
                    this.window = new PopupWindow(this);
                    this.window.setHeight(dip2px(this, 200.0F));
                    this.window.setWidth(this.unitLayout.getWidth());
                    this.window.setOutsideTouchable(true);
                    showWindow();
                    return;
                }else{
                    if (this.window.isShowing())
                    {
                        this.window.dismiss();
                        return;
                    }else{
                        showWindow();
                        return;
                    }
                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.unit.setText(this.data.get(i).trim());
        if (this.window.isShowing())
            this.window.dismiss();
    }

    @Override
    public void controlDialog(int paramInt, String paramString) {
        switch (paramInt)
        {
            case LoginConstant.DIALOG_SHOW:
                this.loginProgress.setMessage(paramString);
                this.loginProgress.show();
            case LoginConstant.DIALOG_DISMISS:
                if(this.loginProgress.isShowing())
                {
                    this.loginProgress.dismiss();
                }
        }
    }

    @Override
    public void onDataUpdate(Bundle paramBundle) {

    }

    @Override
    public void toastMsg(String paramString) {
        showToast(paramString);
    }

    private void showWindow()
    {
        this.data = new ArrayList();
        this.data.add("测试一部");
        this.data.add("测试二部");
        this.data.add("装维一部");
        this.data.add("装维二部");
        ListView localListView = new ListView(this);
        localListView.setBackgroundColor(Color.WHITE);
        localListView.setAdapter(new UnitListAdapter(this, this.data));
        this.window.setContentView(localListView);
        this.window.showAsDropDown(this.unitLayout);
        localListView.setOnItemClickListener(this);
    }

    public LoginInfo getLoginInfo()
    {
        this.loginInfo.setJobnumber(this.jobnumber.getText().toString().trim());
        this.loginInfo.setPassword(this.password.getText().toString().trim());
        this.loginInfo.setUnit(this.unit.getText().toString().trim());
        this.loginInfo.setUserName(this.userName.getText().toString().trim());
        return this.loginInfo;
    }

    @Override
    public void onGetForeginFailed(int paramInt, Bundle paramBundle) {

    }

    @Override
    public void onGetForeginSuccess(Bundle paramBundle) {

    }

    @Override
    public void onLoginFailed(int paramInt, Bundle paramBundle) {
        controlDialog(DIALOG_DISMISS, "");
        if (paramBundle != null)
            showToast("登录失败失败," + paramBundle.getString("login_failed_msg"));
    }

    @Override
    public void onLoginSuccess(Bundle paramBundle) {
        Preferences.put(this, "username", this.loginInfo.getUserName());
        Preferences.put(this, "password", this.loginInfo.getPassword());
        Preferences.put(this, "unit", this.loginInfo.getUnit());
        Preferences.put(this, "jobnumber", this.loginInfo.getJobnumber());
        DataManager.getInstance().setLoginInfo(this.loginInfo);
        if (PermissionManager.getInstance().needRequestPermission())
        {
            checkPermission();
        }

//        if ((DataManager.getInstance().getUrl() != null) && (DataManager.getInstance().getUrl().length() > 0))
//        {
            controlDialog(LoginConstant.DIALOG_DISMISS, "");
            startActivity(MainActivity.class, paramBundle);
            finish();
            return;
//        }else{
//            DataManager localDataManager = DataManager.getInstance();
//            if (SystemManager.getInstance().getTelephonyManager() == null){
//                localDataManager.setDevice(SystemManager.getInstance().getTelephonyManager().getDeviceId());
//            }
            //controlDialog(LoginConstant.DIALOG_SHOW, "重新获取系统配置信息");
            // this.presenter.getForeginServer();

//        }

    }

    private boolean checkLoginInfo()
    {
        if (this.userName.getText().toString().trim().length() < 1)
        {
            toastMsg("请输入账号");
            this.userName.setFocusable(true);
            return false;
        }
        if (this.password.getText().toString().trim().length() < 1)
        {
            toastMsg("请输入密码");
            this.password.setFocusable(true);
            return false;
        }
        if (this.jobnumber.getText().toString().trim().length() < 1)
        {
            toastMsg("请输入工号");
            this.jobnumber.setFocusable(true);
            return false;
        }
        return true;
    }

    private boolean checkPermission()
    {
        Log.d("hello", "checkPermission");
        new ArrayList();
        String[] arrayOfString1 = { "android.permission.READ_PHONE_STATE" };
        String[] arrayOfString2 = PermissionManager.getInstance().checkPermisson(this, arrayOfString1);
        if (arrayOfString2[0] != null)
        {
            Log.d("hello", "regist Permission");
            ActivityCompat.requestPermissions(this, arrayOfString2, 100);
            return false;
        }
        return true;
//        DataManager localDataManager = DataManager.getInstance();
//        if (SystemManager.getInstance().getTelephonyManager() != null);
//        for (String str = SystemManager.getInstance().getTelephonyManager().getDeviceId(); ; str = "")
//        {
//            localDataManager.setDevice(str);
//            return true;
//        }
    }
}
