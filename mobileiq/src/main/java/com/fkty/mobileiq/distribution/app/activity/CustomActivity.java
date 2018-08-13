package com.fkty.mobileiq.distribution.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.fkty.mobileiq.distribution.manager.DataManager;

public class CustomActivity extends BaseActivity implements INetNotify {
    private final int SET_ACCOUNT_ID = 1;
    private EditText account;
    private EditText stbID;
    private ImageView backImg;
    private Button btn;
    private ProgressDialog progressBar;
    private TextView title;

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
        this.backImg = paramView.findViewById(R.id.vixtel_btn_back);
        this.title = paramView.findViewById(R.id.vixtel_tv_title);
        this.btn = paramView.findViewById(R.id.custom_sure);
        this.account = paramView.findViewById(R.id.custom_account);
        this.account.setOnClickListener(this);
        this.stbID = paramView.findViewById(R.id.stb_id);
        this.stbID.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if (DataManager.getInstance().getOotConnectType().equals(CommonField.PPPOE)){
            this.account.setEnabled(false);
            this.account.setText(DataManager.getInstance().getPppoeUser());
        }else{
            this.account.setText(DataManager.getInstance().getAccount());
            this.account.setEnabled(true);
        }
        if (this.progressBar == null){
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
        return R.layout.activity_custom;
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
        this.btn.setOnClickListener(this);
        this.backImg.setOnClickListener(this);
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
            case R.id.custom_sure:
                String strAccount = this.account.getText().toString().trim();
                String strSTB = this.stbID.getText().toString().trim();

                if (((strAccount == null) || (strAccount.length() < 1)) && ((strSTB == null) || (strSTB.length() < 1)))
                {
                    showToast("宽带账号/STB ID 不能都设置为空");
                    break;
                }
                if ((strSTB != null) && (strSTB.length() >= 1))
                {
                    DataManager.getInstance().setStbID(strSTB);
                }
                this.progressBar.setMessage("设置宽带账号/STB");
                if (this.progressBar.isShowing())
                    this.progressBar.show();
                WebHttpUtils.getInstance().setAccount(this, SET_ACCOUNT_ID, strAccount);
                break;
            case R.id.custom_account:
                if (CommonField.PPPOE.equals(DataManager.getInstance().getOotConnectType())){
                    showToast("当前盒子连接方式是PPPOE方式，请去盒子配置页面进行PPPOE账号密码配置");
                }
                break;
        }
    }

    @Override
    public void onErrorNetClient(int paramInt, String paramString) {
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        showToast("设置失败");
    }

    @Override
    public void onFailedNetClient(int paramInt, String paramString) {
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        showToast("设置失败");
    }

    @Override
    public void onSuccessNetClient(int paramInt, String paramString) {
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        Log.d("CustomActivity","account="+DataManager.getInstance().getAccount());
        showToast("设置成功");
        finish();
    }
}
