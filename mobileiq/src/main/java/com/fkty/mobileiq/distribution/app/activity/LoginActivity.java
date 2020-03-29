package com.fkty.mobileiq.distribution.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.LoginConstant;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.json.MainJsonUtil;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.manager.PermissionManager;
import com.fkty.mobileiq.distribution.ui.adapter.UnitListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fkty.mobileiq.distribution.constant.CommonField.UNIT_SERVER;

public class LoginActivity extends BaseActivity implements ILoginView, LoginConstant, AdapterView.OnItemClickListener, INetNotify {
    private final int DO_LOGIN = 1;
    private final int GET_TEMPLET = 2;

    private ImageView backImg;
    private List<String> data;
    private ImageView icon;
    private EditText jobnumber;
    private Button loginBtn;
    private LoginInfo loginInfo;
    private ProgressDialog loginProgress;
    private EditText password;
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
        this.loginInfo = new LoginInfo();
        if (this.loginProgress == null)
        {
            this.loginProgress = new ProgressDialog(this);
            this.loginProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.loginProgress.setIndeterminate(true);
            this.loginProgress.setCancelable(false);
        }
        DataManager.getInstance().setUrl(CommonField.UNIT_SERVER[0][5]);
        HashMap fi=new HashMap();
        fi.put("FTP_HOST_NAME",CommonField.UNIT_SERVER[0][1]);
        fi.put("FTP_PORT",CommonField.UNIT_SERVER[0][2]);
        fi.put("FTP_USER_NAME",CommonField.UNIT_SERVER[0][3]);
        fi.put("FTP_PASSWORD",CommonField.UNIT_SERVER[0][4]);
        DataManager.getInstance().setFtpInfo(fi);
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


        SharedPreferences loginInfo=getSharedPreferences(LOGIN_INFO, Context.MODE_PRIVATE);
        if(loginInfo!=null){
            if (loginInfo.contains(LoginConstant.USERNAME)) {
                this.userName.setText(loginInfo.getString(LoginConstant.USERNAME, null));
            }
            if (loginInfo.contains(LoginConstant.PASSWORD)){
                this.password.setText(loginInfo.getString(LoginConstant.PASSWORD, null));
            }
            if (loginInfo.contains(LoginConstant.JOBNUMBER)) {
                this.jobnumber.setText(loginInfo.getString(LoginConstant.JOBNUMBER, null));
            }
            if (loginInfo.contains(LoginConstant.UNIT)) {
                this.unit.setText(loginInfo.getString(LoginConstant.UNIT, null));
            }
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


                SharedPreferences loginInfo=getSharedPreferences(LOGIN_INFO, Context.MODE_PRIVATE);
                SharedPreferences.Editor loginInfoEditor=loginInfo.edit();
                loginInfoEditor.putString(LoginConstant.USERNAME,this.loginInfo.getUserName());
                loginInfoEditor.putString(LoginConstant.PASSWORD,this.loginInfo.getPassword());
                loginInfoEditor.putString(LoginConstant.JOBNUMBER,this.loginInfo.getJobnumber());
                loginInfoEditor.putString(LoginConstant.UNIT,this.loginInfo.getUnit());
                loginInfoEditor.commit();

               // this.presenter.startLogin(1, getLoginInfo());
                WebHttpUtils.getInstance().doLogin(this,this.loginInfo,DO_LOGIN);



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
        DataManager.getInstance().setUrl(CommonField.UNIT_SERVER[i][5]);
        HashMap fi=DataManager.getInstance().getFtpInfo();
        fi.clear();
        fi.put("FTP_HOST_NAME",CommonField.UNIT_SERVER[i][1]);
        fi.put("FTP_PORT",CommonField.UNIT_SERVER[i][2]);
        fi.put("FTP_USER_NAME",CommonField.UNIT_SERVER[i][3]);
        fi.put("FTP_PASSWORD",CommonField.UNIT_SERVER[i][4]);
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
//        this.data.add("测试一部");
//        this.data.add("测试二部");
//        this.data.add("装维一部");
//        this.data.add("装维二部");
        for(int i=0;i<UNIT_SERVER.length;i++){
            this.data.add(UNIT_SERVER[i][0]);
        }
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
    public void onLoginFailed(int paramInt, Bundle paramBundle) {
        controlDialog(DIALOG_DISMISS, "");
        if (paramBundle != null)
            showToast("登录失败失败," + paramBundle.getString("login_failed_msg"));
    }

    @Override
    public void onLoginSuccess(Bundle paramBundle) {
//        Preferences.put(this, "username", this.loginInfo.getUserName());
//        Preferences.put(this, "password", this.loginInfo.getPassword());
//        Preferences.put(this, "unit", this.loginInfo.getUnit());
//        Preferences.put(this, "jobnumber", this.loginInfo.getJobnumber());
//        DataManager.getInstance().setLoginInfo(this.loginInfo);
        if (PermissionManager.getInstance().needRequestPermission())
        {
//            checkPermission();
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

    @Override
    public void onErrorNetClient(int paramInt, String paramString) {
        switch (paramInt){
            default:
                break;
            case DO_LOGIN:
                Log.d(TAG,"onErrorNetClient:do login="+paramString);
                JSONObject rs= null;
                try {
                    rs = new JSONObject(paramString);
                    controlDialog(DIALOG_DISMISS,null);
                    showToast(rs.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onFailedNetClient(int paramInt, String paramString) {
        switch (paramInt){
            default:
                break;
            case DO_LOGIN:
                Log.d(TAG,"onErrorNetClient:do login="+paramString);
                JSONObject rs= null;
                try {
                    rs = new JSONObject(paramString);
                    controlDialog(DIALOG_DISMISS,null);
                    showToast(rs.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onSuccessNetClient(int paramInt, String paramString) {
        switch (paramInt){
            default:
                break;
            case DO_LOGIN:
                Log.d(TAG,"onSuccessNetClient:do login="+paramString);
                JSONObject rs= null;
                try {
                    rs = new JSONObject(paramString);
                    if(ServerErrorCode.ERROR_CODE_SUCCESS==rs.optInt("errorCode")){
                        WebHttpUtils.getInstance().getTemplet4Test(this, GET_TEMPLET);
                    }else {
                        controlDialog(DIALOG_DISMISS,null);
                        showToast(rs.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case GET_TEMPLET:
                showToast("登录成功并成功获取测试模板！");
                Log.e(this.TAG, "response:" + paramString);
                MainJsonUtil.catchModelData(paramString);
                controlDialog(DIALOG_DISMISS,null);
                startActivity(MainActivity.class);
                finish();
                break;
        }
    }

//    private boolean checkPermission()
//    {
//        Log.d("hello", "checkPermission");
//        new ArrayList();
//        String[] arrayOfString1 = { "android.permission.READ_PHONE_STATE" };
//        String[] arrayOfString2 = PermissionManager.getInstance().checkPermisson(this, arrayOfString1);
//        if (arrayOfString2[0] != null)
//        {
//            Log.d("hello", "regist Permission");
//            ActivityCompat.requestPermissions(this, arrayOfString2, 100);
//            return false;
//        }
//        return true;
////        DataManager localDataManager = DataManager.getInstance();
////        if (SystemManager.getInstance().getTelephonyManager() != null);
////        for (String str = SystemManager.getInstance().getTelephonyManager().getDeviceId(); ; str = "")
////        {
////            localDataManager.setDevice(str);
////            return true;
////        }
//    }
}
