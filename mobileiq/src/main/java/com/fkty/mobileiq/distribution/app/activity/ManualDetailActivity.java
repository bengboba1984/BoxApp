package com.fkty.mobileiq.distribution.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.fragment.ManualTestResultFragment;

/**
 * Created by frank_tracy on 2018/3/30.
 */

public class ManualDetailActivity extends BaseActivity
{
    private ImageView backImg;
   // private TestTypeBean bean;
    private Bundle bundle;
    private int modeType = CommonField.MODLE_TYPE_MANUAL_TEST;
    private TextView titel;

    public int bindLayout()
    {
        return R.layout.activity_question_detail;
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
        this.titel.setText("测试结果详情");
        ManualTestResultFragment localManualTestResultFragment = new ManualTestResultFragment();
        this.bundle = new Bundle();
        this.bundle.putInt("modeType", this.modeType);
        Log.d("MDA","data length="+getIntent().getParcelableArrayListExtra("data").size());
        this.bundle.putParcelableArrayList("data", getIntent().getParcelableArrayListExtra("data"));
        localManualTestResultFragment.setArguments(this.bundle);
        getFragmentManager().beginTransaction().add(R.id.question_detail_framelayout, localManualTestResultFragment).commit();
    }

    public void initData()
    {

    }

    public void initParms(Bundle paramBundle)
    {

    }

    public void initView(View paramView)
    {
        this.titel = paramView.findViewById(R.id.vixtel_tv_title);
        this.backImg = paramView.findViewById(R.id.vixtel_btn_back);
    }

    public void onDataUpdate(Bundle paramBundle)
    {
    }

    public void setListener()
    {
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
        }

    }
}