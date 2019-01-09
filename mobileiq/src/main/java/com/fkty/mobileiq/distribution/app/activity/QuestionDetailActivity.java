package com.fkty.mobileiq.distribution.app.activity;

import android.app.Fragment;
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
import com.fkty.mobileiq.distribution.fragment.TestResultDetailFragment;
import com.fkty.mobileiq.distribution.fragment.TestResultFragment;

public class QuestionDetailActivity extends BaseActivity
{
    private ImageView backImg;
    private TestTypeBean bean;
    private Bundle bundle;
    private int testType;
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
        Log.d("QuestionDetailActivity", String.valueOf("doBusiness:"+this.bean.getResult()));
        this.titel.setText("测试结果详情");

        if(CommonField.TEST_TYPE_TRACE_HOP==this.testType){
            TestResultDetailFragment trdf = new TestResultDetailFragment();
            this.bundle = new Bundle();
            this.bundle.putParcelable("data", this.bean);
            trdf.setArguments(this.bundle);
            getFragmentManager().beginTransaction().add(R.id.question_detail_framelayout, trdf).commit();
        }else{
            TestResultFragment trf = new TestResultFragment();
            this.bundle = new Bundle();
            this.bundle.putParcelable("data", this.bean);
            trf.setArguments(this.bundle);
            getFragmentManager().beginTransaction().add(R.id.question_detail_framelayout, trf).commit();
        }

    }

    public void initData()
    {
        if (this.bean != null)
            this.testType = this.bean.getTestType();
    }

    public void initParms(Bundle paramBundle)
    {
        this.bean = getIntent().getParcelableExtra("data");
        Log.e(TAG,"######"+this.bean.getResult());
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

    @Override
    public void onFailedNetClient(int paramInt, String paramString) {

    }
}