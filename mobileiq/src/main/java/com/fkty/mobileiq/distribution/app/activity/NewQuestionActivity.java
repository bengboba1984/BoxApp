package com.fkty.mobileiq.distribution.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fkty.mobileiq.distribution.DistributedMobileIQApplication;
import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.app.presenter.impl.QuestionPresenter;
import com.fkty.mobileiq.distribution.app.view.IQuestionView;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.OpenTestConstant;
import com.fkty.mobileiq.distribution.constant.QuestionConstant;
import com.fkty.mobileiq.distribution.constant.ServerErrorCode;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.result.ModelTypeBean;
import com.fkty.mobileiq.distribution.result.TestResult;
import com.fkty.mobileiq.distribution.ui.adapter.QuestionListAdapter;
import com.fkty.mobileiq.distribution.utils.AnimationUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewQuestionActivity extends BaseActivity
        implements AdapterView.OnItemClickListener, QuestionConstant, IQuestionView
{
    int BTN_STATUS = OpenTestConstant.BTN_START;
    private QuestionListAdapter adapter;
    private ImageView backImg;
    private List<TestTypeBean> data;
    private int testingIndex=0;
//    private String deviceSeq;
    private TextView deviceStatus;
    private ListView listView;
    private TextView listviewName;
    private ImageView mProgressIV;
    private RotateAnimation mRotateAnimation = null;
    private QuestionPresenter presenter;
    private TextView testBtn;
    private TextView testContent;
    private List<TestParamsBean> testData;
    private TextView title;
    private ProgressDialog progressBar;

    public int bindLayout()
    {
        return R.layout.activity_question;
    }

    public View bindView()
    {
        return null;
    }

    public void call(int paramInt, Bundle paramBundle)
    {

    }

    public void controlDialog(int paramInt, String paramString)
    {
    }

    public void doBusiness(Context paramContext)
    {
        Log.d(TAG,"doBusiness:"+(data==null));
        this.title.setText(getString(R.string.questionTest));
        this.listView.setAdapter(this.adapter);
    }

    public void initData()
    {

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
        this.data = new ArrayList();

        this.mRotateAnimation = AnimationUtils.generateRotateAnimation();
        this.presenter = QuestionPresenter.getPresenter(this);
        this.presenter.setmBasicView(this);
        this.testData = DataManager.getInstance().getTroubleData();
        if ((this.testData != null) && (this.testData.size() > 0)) {
            for (int i = 0; i < this.testData.size(); i++) {
                TestTypeBean localTestTypeBean = new TestTypeBean();
                localTestTypeBean.setTestType(this.testData.get(i).getTestType());
                localTestTypeBean.setTestName(this.testData.get(i).getTestName());
                this.data.add(localTestTypeBean);

            }
        }
        this.adapter = new QuestionListAdapter(this, this.data);
    }

    public void initParms(Bundle paramBundle)
    {

    }

    public void initView(View paramView)
    {
        this.backImg = paramView.findViewById(R.id.vixtel_btn_back);
        this.title = paramView.findViewById(R.id.vixtel_tv_title);
        this.listView = paramView.findViewById(R.id.question_listview);
        this.listviewName = paramView.findViewById(R.id.question_listview_name);
        this.mProgressIV = paramView.findViewById(R.id.img_scroll);
        this.testBtn = paramView.findViewById(R.id.question_test_btn);
        this.testContent = paramView.findViewById(R.id.question_test_content);
        this.deviceStatus = paramView.findViewById(R.id.question_listview_device);
    }

    public void onDataUpdate(Bundle paramBundle)
    {
    }

    protected void onDestroy()
    {
        super.onDestroy();
        this.presenter.setmBasicView(null);
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
        TestTypeBean localTestTypeBean = this.data.get(paramInt);

        switch (localTestTypeBean.getStatus())
        {
            default:
                break;
            case CommonField.TEST_STATUS_READY:
                showToast("请先开启测试");
                break;
            case CommonField.TEST_STATUS_TESTING:
                showToast("测试中");
                break;
            case CommonField.TEST_STATUS_FINISH:
                Intent localIntent = new Intent();
                localIntent.setClassName(getApplicationContext(), "com.fkty.mobileiq.distribution.app.activity.QuestionDetailActivity");
                //  Bundle localBundle = new Bundle();
                //  localBundle.putParcelable("data", localTestTypeBean);
                // localIntent.putExtras(localBundle);
                localIntent.putExtra("data",localTestTypeBean);
                Log.d(TAG,"onItemClick:data.getResult"+localTestTypeBean.getResult());
                startActivity(localIntent);
                break;
        }

    }

    public void onStartFailed(int paramInt, Bundle paramBundle)
    {
        Toast.makeText(this, "测试失败", Toast.LENGTH_SHORT).show();

        this.data.get(testingIndex).setStatus(CommonField.MODLE_STATUS_FINISH);
        this.data.get(testingIndex).setResult("{'taskErrorCode':1}");
        this.adapter.setData(this.data);
        this.adapter.notifyDataSetChanged();

        testingIndex++;
        if(testingIndex!=this.testData.size()){
            this.data.get(testingIndex).setStatus(CommonField.MODLE_STATUS_TESTING);
            this.presenter.startTest(DistributedMobileIQApplication.getInstance(), this.testingIndex, this.testData);
        }else{
            this.BTN_STATUS = BTN_START;
            this.listviewName.setText(getString(R.string.test_result));
            if (this.mProgressIV.getAnimation() != null)
                this.mProgressIV.clearAnimation();
            this.testContent.setText(getString(R.string.test_complete));
            this.testBtn.setText(getString(R.string.begin_test));
            this.testBtn.setTextColor(Color.WHITE);
        }
    }

    public void onStartSuccess(int paramInt, Bundle paramBundle) {

        if(this.BTN_STATUS != OpenTestConstant.BTN_STOP){
            String msg = paramBundle.getString("msg");
            try {
                JSONObject localJSONObject1 = new JSONObject(msg);
                JSONArray localJSONArray = localJSONObject1.optJSONArray("modleResult");
                if ((localJSONArray != null) && (localJSONArray.length() > 0)) {

                    for (int j = 0; j < localJSONArray.length(); j++) {
                        JSONObject localJSONObject2 = localJSONArray.optJSONObject(j);
                        if (localJSONObject2.optInt("testTemplateType") == CommonField.MODLE_TYPE_MALFUNCTION_ELIMINATION) {

                            JSONArray localJSONArray2 = localJSONObject2.optJSONArray("parameters");
                            this.deviceStatus.setVisibility(View.VISIBLE);

                            this.deviceStatus.setText(getString(R.string.device_this_test_complete));
                            if ((localJSONArray2 != null) && (localJSONArray2.length() > 0)) {


                                for (int k = 0; k < localJSONArray2.length(); k++) {
                                    JSONObject localJSONObject6 = localJSONArray2.optJSONObject(k);
                                    TestTypeBean localTestTypeBean2 = new TestTypeBean();
                                    localTestTypeBean2.setTestName(localJSONObject6.optString("testName"));
                                    localTestTypeBean2.setTestType(localJSONObject6.optInt("testType"));
                                    localTestTypeBean2.setStatus(localJSONObject6.optInt("testState"));

                                    if (localJSONObject6.optJSONArray("testResult") != null && localJSONObject6.optJSONArray("testResult").length()>0) {
                                        Log.d(TAG,"onStartSuccess:"+localJSONObject6.optJSONArray("testResult").get(0).toString());
                                        localTestTypeBean2.setResult(localJSONObject6.optJSONArray("testResult").get(0).toString());
                                    }else{
                                        localTestTypeBean2.setResult("");
                                    }

                                    if(this.data.get(testingIndex).getTestName().equals(localTestTypeBean2.getTestName())){
                                        this.data.get(testingIndex).setResult(localTestTypeBean2.getResult());
                                        this.data.get(testingIndex).setStatus(CommonField.MODLE_STATUS_FINISH);
                                        testingIndex++;
                                        if(testingIndex!=this.testData.size()){
                                            this.data.get(testingIndex).setStatus(CommonField.MODLE_STATUS_TESTING);
                                        }
                                        break;
                                    }
                                }

                            }
                            this.adapter.setData(this.data);
                            this.adapter.notifyDataSetChanged();



                            if(testingIndex!=this.testData.size()){
                                this.presenter.startTest(DistributedMobileIQApplication.getInstance(), this.testingIndex, this.testData);
                            }else{
                                this.BTN_STATUS = BTN_START;
                                this.listviewName.setText(getString(R.string.test_result));
                                if (this.mProgressIV.getAnimation() != null)
                                    this.mProgressIV.clearAnimation();
                                this.testContent.setText(getString(R.string.test_complete));
                                this.testBtn.setText(getString(R.string.begin_test));
                                this.testBtn.setTextColor(Color.WHITE);
                                testingIndex=0;
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public void onStopFailed(int paramInt, Bundle paramBundle)
    {
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        this.data.get(testingIndex).setStatus(CommonField.MODLE_STATUS_FINISH);
        this.data.get(testingIndex).setResult("{'taskErrorCode':1}");
        this.adapter.setData(this.data);
        this.adapter.notifyDataSetChanged();

        Toast.makeText(this, "停止测试失败", Toast.LENGTH_SHORT).show();
        this.BTN_STATUS = OpenTestConstant.BTN_START;
        this.listviewName.setText(getString(R.string.test_result));
        if (this.mProgressIV.getAnimation() != null)
            this.mProgressIV.clearAnimation();
        this.testContent.setText(getString(R.string.test_stoped));
        this.testBtn.setText(getString(R.string.begin_test));
        this.testBtn.setTextColor(Color.WHITE);

        testingIndex=0;
    }

    public void onStopSuccess(int paramInt, Bundle paramBundle)
    {
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        Toast.makeText(this, "停止测试成功", Toast.LENGTH_SHORT).show();
        String msg = paramBundle.getString("msg");
        try {
            JSONObject localJSONObject1 = new JSONObject(msg);
            JSONArray localJSONArray = localJSONObject1.optJSONArray("modleResult");
            if ((localJSONArray != null) && (localJSONArray.length() > 0)) {

                for (int j = 0; j < localJSONArray.length(); j++) {
                    JSONObject localJSONObject2 = localJSONArray.optJSONObject(j);
                    if (localJSONObject2.optInt("testTemplateType") == CommonField.MODLE_TYPE_MALFUNCTION_ELIMINATION) {

                        JSONArray localJSONArray2 = localJSONObject2.optJSONArray("parameters");
                        this.deviceStatus.setVisibility(View.VISIBLE);

                        this.deviceStatus.setText(getString(R.string.device_this_test_complete));
                        if ((localJSONArray2 != null) && (localJSONArray2.length() > 0)) {

                            for (int k = 0; k < localJSONArray2.length(); k++) {
                                JSONObject localJSONObject6 = localJSONArray2.optJSONObject(k);
                                TestTypeBean localTestTypeBean2 = new TestTypeBean();
                                localTestTypeBean2.setTestName(localJSONObject6.optString("testName"));
                                localTestTypeBean2.setTestType(localJSONObject6.optInt("testType"));
                                localTestTypeBean2.setStatus(localJSONObject6.optInt("testState"));
                                if (localJSONObject6.optJSONArray("testResult") != null && localJSONObject6.optJSONArray("testResult").length()>0) {
                                    Log.d(TAG,"onStartSuccess:"+localJSONObject6.optJSONArray("testResult").get(0).toString());
                                    localTestTypeBean2.setResult(localJSONObject6.optJSONArray("testResult").get(0).toString());
                                }else{
                                    localTestTypeBean2.setResult("");
                                }

                                if(this.data.get(testingIndex).getTestName().equals(localTestTypeBean2.getTestName())){
                                    this.data.get(testingIndex).setResult(localTestTypeBean2.getResult());
                                    this.data.get(testingIndex).setStatus(CommonField.MODLE_STATUS_FINISH);
                                    break;
                                }
                            }
                        }
                        this.adapter.setData(this.data);
                        this.adapter.notifyDataSetChanged();

                        testingIndex=0;
                        this.BTN_STATUS = BTN_START;
                        this.listviewName.setText(getString(R.string.test_result));
                        if (this.mProgressIV.getAnimation() != null)
                            this.mProgressIV.clearAnimation();
                        this.testContent.setText(getString(R.string.test_complete));
                        this.testBtn.setText(getString(R.string.begin_test));
                        this.testBtn.setTextColor(Color.WHITE);


                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setListener()
    {
        this.backImg.setOnClickListener(this);
        this.listView.setOnItemClickListener(this);
        this.testBtn.setOnClickListener(this);
    }

    public void stopTest()
    {
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
            case R.id.question_test_btn:
//                Log.d(TAG,"this.BTN_STATUS="+this.BTN_STATUS);
//                Log.d(TAG,"this.TEST_STATUS="+this.TEST_STATUS);
                switch (this.BTN_STATUS)
                {
                    default:
                        break;
                    case OpenTestConstant.BTN_START:
                    case OpenTestConstant.BTN_STOP:
                        initialDataStatus();
                        if (this.mProgressIV.getAnimation() == null)
                            this.mProgressIV.startAnimation(this.mRotateAnimation);
                        Toast.makeText(this, "开始测试，点击结束查看结果", Toast.LENGTH_SHORT).show();
                        this.testBtn.setText(getString(R.string.stop_test));
                        this.testBtn.setTextColor(Color.RED);
                        this.BTN_STATUS = OpenTestConstant.BTN_MIDDLE;
                        this.presenter.startTest(DistributedMobileIQApplication.getInstance(), this.testingIndex, this.testData);
                        break;
                    case OpenTestConstant.BTN_MIDDLE:
                        this.BTN_STATUS = OpenTestConstant.BTN_STOP;
                        this.progressBar.setMessage("处理数据中...");
                        if (!this.progressBar.isShowing())
                            this.progressBar.show();
                        this.presenter.stopTest();
                        break;
                }
                break;
        }
    }

    private void initialDataStatus(){
        for(int i=0;i<this.data.size();i++){
            if(i==0){
                this.data.get(i).setStatus(CommonField.MODLE_STATUS_TESTING);
            }else{
                this.data.get(i).setStatus(CommonField.MODLE_STATUS_READY);
            }
        }
        //this.adapter.setData(this.data);
        this.adapter.notifyDataSetChanged();
    }
}
