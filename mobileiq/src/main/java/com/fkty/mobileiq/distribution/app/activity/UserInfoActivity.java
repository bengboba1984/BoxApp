package com.fkty.mobileiq.distribution.app.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.manager.AppManager;
import com.fkty.mobileiq.distribution.manager.DataManager;

public class UserInfoActivity extends BaseActivity
{
    private ImageView backImg;
    private TextView company;
    private TextView jobId;
    private Button logout;
    private TextView title;
    private TextView userName;

    public int bindLayout()
    {
        return R.layout.activity_user_info;
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
    }

    public void initData()
    {
        this.title.setText(getString(R.string.userInfo));
        this.userName.setText(DataManager.getInstance().getLoginInfo().getUserName());
        this.jobId.setText(DataManager.getInstance().getLoginInfo().getJobnumber());
        this.company.setText(DataManager.getInstance().getLoginInfo().getUnit());
    }

    public void initParms(Bundle paramBundle)
    {
    }

    public void initView(View paramView)
    {
        this.backImg = paramView.findViewById(R.id.vixtel_btn_back);
        this.logout = paramView.findViewById(R.id.logout);
        this.title = paramView.findViewById(R.id.vixtel_tv_title);
        this.userName = paramView.findViewById(R.id.user_userName);
        this.jobId = paramView.findViewById(R.id.user_jobId);
        this.company = paramView.findViewById(R.id.user_unit);
    }

    public void onDataUpdate(Bundle paramBundle)
    {
    }

    public void setListener()
    {
        this.logout.setOnClickListener(this);
        this.backImg.setOnClickListener(this);
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
            case R.id.logout:
                startActivity(LoginActivity.class);
                AppManager.getAppManager().finishActivity(MainActivity.class);
                finish();
                break;
        }

    }

    @Override
    public void onFailedNetClient(int paramInt, String paramString) {

    }
}