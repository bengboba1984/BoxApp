package com.fkty.mobileiq.distribution.app.activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.http.INetNotify;
import com.fkty.mobileiq.distribution.http.WebHttpUtils;
import com.fkty.mobileiq.distribution.json.MainJsonUtil;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.utils.Preferences;

public class SettingActivity extends BaseActivity implements INetNotify,View.OnClickListener{
    private final String TAG="SettingActivity";
    private final int GET_TEMPLET = 2;
    private final int SET_URL = 1;
    private Button btn;
    private EditText editText;
    private ImageView mBackButton;
    private TextView mTitile;
    private ProgressDialog progressBar;


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
        this.mTitile = findViewById(R.id.vixtel_tv_title);
        this.mBackButton = findViewById(R.id.vixtel_btn_back);
        this.btn = findViewById(R.id.setting_btn);
        this.editText = findViewById(R.id.setting_forig_url_edit);
    }

    @Override
    public void initData() {
        this.editText.setText(DataManager.getInstance().getUrl());
        if(this.editText.getText()==null || this.editText.getText().length()<1){
            this.editText.setText("http://211.136.99.12:4100");
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

    @Override
    public void initParms(Bundle paramBundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_setting;
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
            case R.id.setting_btn:
                String str = this.editText.getText().toString().trim();
                if ((str == null) || (str.length() < 1))
                {
                    showToast("平台地址不能为空");
                    break;
                }
                this.progressBar.setMessage("设置服务器地址");
                if (!this.progressBar.isShowing())
                    this.progressBar.show();
                WebHttpUtils.getInstance().setUrl(this, SET_URL, str);
                break;
        }
    }


    @Override
    public void onErrorNetClient(int paramInt, String paramString) {
        if (this.progressBar.isShowing()){
            this.progressBar.dismiss();
        }
        Log.d(TAG,"onErrorNetClient: paramInt="+paramInt);
        switch (paramInt)
        {
            default:
            case SET_URL:
                showToast("设置服务器地址失败");
                break;
            case GET_TEMPLET:
                showToast("获取测试模板失败");
                break;
        }
    }

    @Override
    public void onFailedNetClient(int paramInt, String paramString) {
        if (this.progressBar.isShowing()){
            this.progressBar.dismiss();
        }
        Log.d(TAG,"onFailedNetClient: paramInt="+paramInt);
        switch (paramInt)
        {
            default:
            case SET_URL:
                showToast("设置服务器地址失败");
                break;
            case GET_TEMPLET:
                showToast("获取测试模板失败");
                break;
        }
    }

    @Override
    public void onSuccessNetClient(int paramInt, String paramString) {
        if (this.progressBar.isShowing()){
            this.progressBar.dismiss();
        }
        Log.d(TAG,"onSuccessNetClient: paramInt="+paramInt);
        switch (paramInt)
        {
            default:
            case SET_URL:
                showToast("设置服务器地址成功");
                DataManager.getInstance().setUrl(this.editText.getText().toString().trim());
                this.progressBar.setMessage("获取测试模板");
                WebHttpUtils.getInstance().getTemplet4Test(this, GET_TEMPLET);
//                WebHttpUtils.getInstance().getTemplet(this, GET_TEMPLET);
                break;
            case GET_TEMPLET:
                showToast("重新获取测试模板成功");
                Log.e(this.TAG, "response:" + paramString);
              //  if ((paramString != null) && (paramString.length() > 0))

                MainJsonUtil.catchModelData(paramString);
                break;
        }




    }
}
