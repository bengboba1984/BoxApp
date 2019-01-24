package com.fkty.mobileiq.distribution.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends BaseActivity
        implements AdapterView.OnItemClickListener, QuestionConstant, IQuestionView
{
    int BTN_STATUS = OpenTestConstant.BTN_START;
    private int TEST_STATUS = OpenTestConstant.TEST_START;
    private QuestionListAdapter adapter;
    private ImageView backImg;
    private List<TestTypeBean> data;
    private String deviceSeq;
    private TextView deviceStatus;
    private ModelTypeBean lastTestResult = null;
    private int lastTestStatus = MODLE_STATUS_READY;
    private ListView listView;
    private TextView listviewName;
    private ImageView mProgressIV;
    private RotateAnimation mRotateAnimation = null;
    private QuestionPresenter presenter;
    private TextView testBtn;
    private TextView testContent;
    private List<TestParamsBean> testData;
    private TextView title;

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
        Log.d(TAG,"call:paramInt="+paramInt);
        switch (paramInt)
        {
            default:
                break;
            case CommonField.MODLE_STATUS_READY:
                this.deviceStatus.setVisibility(View.GONE);
                if ((this.data != null) && (this.data.size() > 0))
                    for (int i = 0; i < this.data.size(); i++)
                    {
                        if (this.data.get(i).getStatus() == CommonField.MODLE_STATUS_TESTING)
                            this.data.get(i).setStatus(CommonField.MODLE_STATUS_READY);
                    }
                this.adapter.setData(this.data);
                this.adapter.notifyDataSetChanged();
                this.BTN_STATUS = BTN_START;
                this.TEST_STATUS = TEST_COMPLETE;
                this.listviewName.setText(getString(R.string.test_result));
                break;
            case ServerErrorCode.ERROR_CODE_FAILED:

                if (this.mProgressIV.getAnimation() != null)
                    this.mProgressIV.clearAnimation();
                this.testContent.setText(getString(R.string.test_failed));
                this.testBtn.setText(getString(R.string.begin_test));
                showToast("获取测试状态失败，请检查wifi连接和盒子状态");
                break;
            case CommonField.MODLE_STATUS_TESTING:
                this.BTN_STATUS = BTN_STOP;
                this.TEST_STATUS = TEST_DOING;
                if (this.mProgressIV.getAnimation() == null)
                    this.mProgressIV.startAnimation(this.mRotateAnimation);
                this.testBtn.setText(getString(R.string.stop_test));
                String msg = paramBundle.getString("message");
                try{
                    JSONObject msgJson = new JSONObject(msg);
                    JSONArray parametersJson = msgJson.optJSONArray("parameters");
                    this.deviceStatus.setVisibility(View.VISIBLE);
                    String ds = msgJson.optString("deviceSeq");
                    if (this.deviceSeq.equals(ds))
                    {
                        this.deviceStatus.setText(getString(R.string.device_this_testing));
                        if ((parametersJson != null) && (parametersJson.length() > 0)){
                            this.data.clear();
                            for (int n = 0;n < parametersJson.length() ; n++)
                            {
                                JSONObject testTypeBeanJson = parametersJson.optJSONObject(n);
                                TestTypeBean ttb = new TestTypeBean();
                                ttb.setTestName(testTypeBeanJson.optString("testName"));
                                ttb.setStatus(testTypeBeanJson.optInt("testState"));
                                ttb.setTestType(testTypeBeanJson.optInt("testType"));

                                JSONObject testResultJson = testTypeBeanJson.optJSONObject("testResult");
                                if ((testResultJson != null) && (testResultJson.length() > 0)){
                                    ttb.setResult(testResultJson.toString());
                                }
                                this.data.add(ttb);
                                if(ttb!=null && ttb.getStatus()==CommonField.MODLE_STATUS_TESTING){
                                    this.testContent.setText(ttb.getTestName() + "...");
                                }
                            }
                        }
                    }else{
                        this.deviceStatus.setText(getString(R.string.device_other_testing));
                    }
                    this.adapter.setData(this.data);
                    this.adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                break;
            case CommonField.MODLE_STATUS_FINISH:
                String str4 = paramBundle.getString("message");
                try
                {
                    JSONObject localJSONObject5 = new JSONObject(str4);
                    JSONArray localJSONArray2 = localJSONObject5.optJSONArray("parameters");
                    this.deviceStatus.setVisibility(View.VISIBLE);
                    String str5 = localJSONObject5.optString("deviceSeq");
                    if (this.deviceSeq.equals(str5))
                    {
                        this.deviceStatus.setText(getString(R.string.device_this_test_complete));
                        if ((localJSONArray2 != null) && (localJSONArray2.length() > 0)){
                            this.data.clear();
                            for (int k = 0;k < localJSONArray2.length() ; k++)
                            {
                                JSONObject localJSONObject6 = localJSONArray2.optJSONObject(k);
                                TestTypeBean localTestTypeBean2 = new TestTypeBean();
                                localTestTypeBean2.setTestType(localJSONObject6.optInt("testType"));
                                localTestTypeBean2.setStatus(localJSONObject6.optInt("testState"));
                                if (localJSONObject6.optJSONObject("testResult") != null){
                                    localTestTypeBean2.setResult(localJSONObject6.optJSONObject("testResult").toString());
                                }
                                localTestTypeBean2.setTestName(localJSONObject6.optString("testName"));
                                this.data.add(localTestTypeBean2);
                                if ((localTestTypeBean2 != null) && (localTestTypeBean2.getStatus() == CommonField.MODLE_STATUS_TESTING)){
                                    this.testContent.setText(localTestTypeBean2.getTestName() + "...");
                                }
                            }
                        }
                    }else{
                        this.deviceStatus.setText(getString(R.string.device_other_test_complete));
                    }
                    this.adapter.setData(this.data);
                    this.adapter.notifyDataSetChanged();
                    this.BTN_STATUS = BTN_START;
                    this.TEST_STATUS = TEST_COMPLETE;
                    this.listviewName.setText(getString(R.string.test_result));
                    if (this.mProgressIV.getAnimation() != null)
                        this.mProgressIV.clearAnimation();
                    this.testContent.setText(getString(R.string.test_complete));
                    this.testBtn.setText(getString(R.string.begin_test));
                }catch (JSONException e){
                    e.printStackTrace();
                }
                break;
            case CommonField.MODLE_STATUS_STOP:
                String str1 = paramBundle.getString("message");
                try
                {
                    JSONObject localJSONObject2 = new JSONObject(str1);
                    JSONArray localJSONArray1 = localJSONObject2.optJSONArray("parameters");
                    this.deviceStatus.setVisibility(View.VISIBLE);
                    String str2 = localJSONObject2.optString("deviceSeq");
                    if (this.deviceSeq.equals(str2))
                    {
                        this.deviceStatus.setText(getString(R.string.device_this_test_stop));
                        if ((localJSONArray1 != null) && (localJSONArray1.length() > 0)){
                            this.data.clear();
                            for (int i = 0;i<localJSONArray1.length() ; i++)
                            {
                                JSONObject localJSONObject3 = localJSONArray1.optJSONObject(i);
                                TestTypeBean localTestTypeBean1 = new TestTypeBean();
                                localTestTypeBean1.setTestType(localJSONObject3.optInt("testType"));
                                localTestTypeBean1.setStatus(localJSONObject3.optInt("testState"));
                                localTestTypeBean1.setTestName(localJSONObject3.optString("testName"));
                                if (localJSONObject3.optJSONObject("testResult") != null){
                                    localTestTypeBean1.setResult(localJSONObject3.optJSONObject("testResult").toString());
                                }
                                this.data.add(localTestTypeBean1);
                                if ((localTestTypeBean1 != null) && (localTestTypeBean1.getStatus() == CommonField.MODLE_STATUS_TESTING)){
                                    this.testContent.setText(localTestTypeBean1.getTestName() + "...");
                                }
                            }
                        }
                    }else{
                        this.deviceStatus.setText(getString(R.string.device_other_test_stop));
                    }
                    this.adapter.setData(this.data);
                    this.adapter.notifyDataSetChanged();
                    this.BTN_STATUS = OpenTestConstant.BTN_START;
                    this.TEST_STATUS = OpenTestConstant.TEST_COMPLETE;
                    this.listviewName.setText(getString(R.string.test_result));
                    if (this.mProgressIV.getAnimation() != null)
                        this.mProgressIV.clearAnimation();
                    this.testContent.setText(getString(R.string.test_stoped));
                    this.testBtn.setText(getString(R.string.begin_test));
                }catch (JSONException e){
                    e.printStackTrace();
                }
                break;
        }

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
        Log.d(TAG,"initData:this.lastTestStatus="+this.lastTestStatus);
        switch (this.lastTestStatus)
        {
            default:
                break;
            case OpenTestConstant.MODLE_STATUS_READY:
                this.data = new ArrayList();

                this.mRotateAnimation = AnimationUtils.generateRotateAnimation();
                this.presenter = QuestionPresenter.getPresenter(this);
                this.presenter.setmBasicView(this);
                this.deviceSeq = DataManager.getInstance().getDeviceSeq();
                this.TEST_STATUS = OpenTestConstant.TEST_START;
                this.testData = DataManager.getInstance().getTroubleData();
                if ((this.testData != null) && (this.testData.size() > 0)){
                    for (int i = 0; i < this.testData.size(); i++)
                    {
                        TestTypeBean localTestTypeBean = new TestTypeBean();
                        localTestTypeBean.setTestType(this.testData.get(i).getTestType());
                        localTestTypeBean.setTestName(this.testData.get(i).getTestName());
                        this.data.add(localTestTypeBean);
                    }
                }
                this.adapter = new QuestionListAdapter(this, this.data);
                break;
            case OpenTestConstant.MODLE_STATUS_TESTING:
                this.data = this.lastTestResult.getSubTests();
                this.TEST_STATUS = OpenTestConstant.TEST_DOING;
                break;
            case OpenTestConstant.MODLE_STATUS_FINISH:
                this.TEST_STATUS = OpenTestConstant.TEST_COMPLETE;
                this.data = this.lastTestResult.getSubTests();
                break;
        }
    }

    public void initParms(Bundle paramBundle)
    {
        if (TestResult.getInstance().isNeedSave())
            this.lastTestResult = TestResult.getInstance().getResult();
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
        switch (this.TEST_STATUS)
        {
            default:
                break;
            case OpenTestConstant.TEST_STOP:
                this.presenter.setmBasicView(null);
                break;
            case OpenTestConstant.TEST_COMPLETE:
                TestResult.getInstance().setNeedSave(false);
                TestResult.getInstance().clear();
                break;
            case OpenTestConstant.TEST_DOING:
                TestResult.getInstance().setNeedSave(true);
                break;
            case OpenTestConstant.TEST_START:
                TestResult.getInstance().setNeedSave(false);
                TestResult.getInstance().clear();
                break;
        }
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
        this.BTN_STATUS = OpenTestConstant.BTN_STOP;
        String str = paramBundle.getString("msg");
        try
        {
            JSONObject localJSONObject = new JSONObject(str);
            switch (localJSONObject.optInt("errorCode"))
            {
                default:
                    Toast.makeText(this, "开始测试失败", Toast.LENGTH_SHORT).show();
                    return;
                case ServerErrorCode.ERROR_CODE_TEST_ALREADY_START:
                    Toast.makeText(this, "正在测试中", Toast.LENGTH_SHORT).show();
                    this.BTN_STATUS =OpenTestConstant.BTN_STOP ;
                    this.TEST_STATUS = OpenTestConstant.TEST_DOING;
                    if (this.mProgressIV.getAnimation() == null)
                        this.mProgressIV.startAnimation(this.mRotateAnimation);
                    this.testBtn.setText(getString(R.string.stop_test));
                    break;
                case ServerErrorCode.ERRIR_CODE_ONE_MODLE_ONLY:
                    this.BTN_STATUS = OpenTestConstant.BTN_START;
                    this.TEST_STATUS = OpenTestConstant.TEST_START;
                    Toast.makeText(this, "开启测试失败，只允许同时一种测试进行", Toast.LENGTH_SHORT).show();
                case ServerErrorCode.ERROR_CODE_REQUEST_NULL:
                    this.BTN_STATUS = OpenTestConstant.BTN_START;
                    this.TEST_STATUS = OpenTestConstant.TEST_START;
                    Toast.makeText(this, "没有测试模板", Toast.LENGTH_SHORT).show();
                    break;
            }
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }


    }

    public void onStartSuccess(int paramInt, Bundle paramBundle)
    {
        this.BTN_STATUS = OpenTestConstant.BTN_STOP;
        this.TEST_STATUS = OpenTestConstant.TEST_DOING;
        if (this.mProgressIV.getAnimation() == null)
            this.mProgressIV.startAnimation(this.mRotateAnimation);
        this.testBtn.setText(getString(R.string.stop_test));
    }

    public void onStopFailed(int paramInt, Bundle paramBundle)
    {
        Toast.makeText(this, "停止测试失败", Toast.LENGTH_SHORT).show();
        this.BTN_STATUS = OpenTestConstant.BTN_START;
        this.listviewName.setText(getString(R.string.test_result));
        if (this.mProgressIV.getAnimation() != null)
            this.mProgressIV.clearAnimation();
        this.testContent.setText(getString(R.string.test_stoped));
        this.testBtn.setText(getString(R.string.begin_test));
    }

    public void onStopSuccess(int paramInt, Bundle paramBundle)
    {
        Toast.makeText(this, "停止测试成功", Toast.LENGTH_SHORT).show();
        this.BTN_STATUS = OpenTestConstant.BTN_START;
        this.listviewName.setText(getString(R.string.test_result));
        if (this.mProgressIV.getAnimation() != null)
            this.mProgressIV.clearAnimation();
        this.testContent.setText(getString(R.string.test_stoped));
        this.testBtn.setText(getString(R.string.begin_test));
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
                // startActivity(MainActivity.class);
                break;
            case R.id.question_test_btn:
                Log.d(TAG,"this.BTN_STATUS="+this.BTN_STATUS);
                Log.d(TAG,"this.TEST_STATUS="+this.TEST_STATUS);
                switch (this.BTN_STATUS)
                {
                    default:
                        break;
                    case OpenTestConstant.BTN_START:
                        this.BTN_STATUS = OpenTestConstant.BTN_MIDDLE;
                        this.presenter.startTest(DistributedMobileIQApplication.getInstance(), this.data, this.testData);
                        break;
                    case OpenTestConstant.BTN_MIDDLE:
                        break;
                    case OpenTestConstant.BTN_STOP:
                        this.BTN_STATUS = OpenTestConstant.BTN_MIDDLE;
                        this.TEST_STATUS = OpenTestConstant.TEST_STOP;
                        this.presenter.stopTest();
                        break;
                }
                break;
        }


    }
}
