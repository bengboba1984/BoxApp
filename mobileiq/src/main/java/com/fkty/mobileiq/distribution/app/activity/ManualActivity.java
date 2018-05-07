package com.fkty.mobileiq.distribution.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.app.presenter.impl.ManualPresenter;
import com.fkty.mobileiq.distribution.app.view.IManualView;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.bean.TestChildBean;
import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.OpenTestConstant;
import com.fkty.mobileiq.distribution.constant.QuestionConstant;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.ui.adapter.MunualRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ManualActivity extends BaseActivity
        implements MunualRecyclerAdapter.OnRecyclerViewListener, IManualView
{
    int BTN_STATUS = OpenTestConstant.BTN_START;
    private MunualRecyclerAdapter adapter;
    private ImageView backImg;
    private Button button;
    private List<TestParamsBean> data;
    private Map<Integer, TestParamsBean> dataMap;
    private RecyclerView.LayoutManager mLayoutManager;
    private ManualPresenter presenter;
    private ProgressDialog progressBar;
    private RecyclerView recyclerView;
    private List<TestParamsBean> selectedData;
    private TextView title;

    public int bindLayout()
    {
        return R.layout.activity_manual;
    }

    public View bindView()
    {
        return null;
    }

    public void call(int paramInt, Bundle paramBundle)
    {
//        switch (paramInt)
//        {
//            default:
//            case 2:
//            case -100:
//            case 3:
//        }
//        do
//        {
//            do
//            {
//                return;
//                this.progressBar.setMessage("测试中");
//            }
//            while (this.progressBar.isShowing());
//            this.progressBar.setMessage("测试中");
//            this.progressBar.show();
//            return;
//            this.button.setText(getString(2131230759));
//            if (this.progressBar.isShowing())
//                this.progressBar.dismiss();
//            showToast("获取测试状态失败，请检查wifi连接和盒子状态");
//            return;
//            this.button.setText(getString(2131230759));
//            if (!this.progressBar.isShowing())
//                continue;
//            this.progressBar.dismiss();
//        }
//        while (i != 0);
//        i = 1 + i;
//        String str1 = paramBundle.getString("message");
//        try
//        {
//            JSONObject localJSONObject1 = new JSONObject(str1);
//            localJSONObject2 = localJSONObject1;
//            JSONArray localJSONArray = localJSONObject2.optJSONArray("parameters");
//            if ((localJSONArray != null) && (localJSONArray.length() > 0))
//            {
//                manualList.clear();
//                for (int j = 0; ; j++)
//                {
//                    if (j >= localJSONArray.length())
//                        break label346;
//                    JSONObject localJSONObject3 = localJSONArray.optJSONObject(j);
//                    TestChildBean localTestChildBean = new TestChildBean();
//                    localTestChildBean.setTestType(localJSONObject3.optInt("testType"));
//                    localTestChildBean.setTaskErrorCode(localJSONObject3.optInt("taskErrorCode"));
//                    if (localJSONObject3.optJSONObject("testResult") == null)
//                        break;
//                    str2 = localJSONObject3.optJSONObject("testResult").toString();
//                    localTestChildBean.setResult(str2);
//                    localTestChildBean.setTemplateName(localJSONObject3.optString("templateName"));
//                    localTestChildBean.setTestName(localJSONObject3.optString("testName"));
//                    manualList.add(localTestChildBean);
//                }
//            }
//        }
//        catch (JSONException localJSONException)
//        {
//            while (true)
//            {
//                localJSONException.printStackTrace();
//                JSONObject localJSONObject2 = null;
//                continue;
//                String str2 = "";
//            }
//            label346: startActivity(ManualDetailActivity.class, paramBundle);
//        }
    }

    public void controlDialog(int paramInt, String paramString)
    {
    }

    public void doBusiness(Context paramContext)
    {
    }

    public void initData()
    {
        this.presenter = new ManualPresenter(this);
        this.dataMap = new HashMap();
        this.data = DataManager.getInstance().getManualData();
        this.selectedData = new ArrayList();
        this.mLayoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.adapter = new MunualRecyclerAdapter(this.data, this);
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setListener(this);
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
        this.recyclerView = paramView.findViewById(R.id.manual_recyclerview);
        this.button = paramView.findViewById(R.id.manual_btn);
        this.title = paramView.findViewById(R.id.vixtel_tv_title);
        this.backImg = paramView.findViewById(R.id.vixtel_btn_back);
    }

    public void onDataUpdate(Bundle paramBundle)
    {
    }

    public void onEditChange(int paramInt, String paramString)
    {
        if (this.dataMap.containsKey(Integer.valueOf(paramInt)))
        {
            TestParamsBean localTestParamsBean = this.dataMap.get(Integer.valueOf(paramInt));
            localTestParamsBean.setDestNodeIp(paramString);
            this.dataMap.put(Integer.valueOf(paramInt), localTestParamsBean);
        }
    }

    public void onItemClick(int paramInt, boolean paramBoolean)
    {
//        showToast("Position:" + paramInt);
        if (this.data.size() > paramInt)
        {
            TestParamsBean localTestParamsBean = this.data.get(paramInt);
            if (paramBoolean)
            {
                this.dataMap.put(Integer.valueOf(paramInt), localTestParamsBean);
                return;
            }else{
                this.dataMap.remove(Integer.valueOf(paramInt));
                return;
            }

        }
        showToast("数据错误");
    }

    public void onStartFailed(int paramInt, Bundle paramBundle)
    {
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        this.button.setText(getString(R.string.begin_test));
        this.button.setTextColor(Color.WHITE);
        Toast.makeText(this, "开始测试失败",Toast.LENGTH_SHORT).show();
    }

    public void onStartSuccess(int paramInt, Bundle paramBundle)
    {
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        this.button.setText(getString(R.string.begin_test));
        this.button.setTextColor(Color.WHITE);
        recevieMSG(paramInt,paramBundle);


    }

    public void onStopFailed(int paramInt, Bundle paramBundle)
    {
        this.BTN_STATUS=OpenTestConstant.BTN_STOP;
        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        this.button.setText(getString(R.string.begin_test));
        this.button.setTextColor(Color.WHITE);
        Toast.makeText(this, "停止测试失败", Toast.LENGTH_SHORT).show();
    }

    public void onStopSuccess(int paramInt, Bundle paramBundle)
    {

        if (this.progressBar.isShowing())
            this.progressBar.dismiss();
        this.button.setText(getString(R.string.begin_test));
        this.button.setTextColor(Color.WHITE);
        Toast.makeText(this, "停止测试成功", Toast.LENGTH_SHORT).show();
        recevieMSG(paramInt,paramBundle);

    }

    public void setListener()
    {
        this.button.setOnClickListener(this);
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
            case R.id.manual_btn:
                switch (this.BTN_STATUS){
                    default:
                        break;
                    case OpenTestConstant.BTN_STOP:
                    case OpenTestConstant.BTN_START:
                        if ((this.dataMap != null) && (this.dataMap.size() > 0))
                        {
                            this.selectedData = new ArrayList();
                            Iterator it = this.dataMap.keySet().iterator();
                            while (it.hasNext()){
                                Integer itemNo = (Integer)it.next();
                                TestParamsBean localTestParamsBean = this.dataMap.get(itemNo);
                                switch(localTestParamsBean.getTestType()){
                                    default:
                                        this.selectedData.add(localTestParamsBean);
                                        break;
                                    case CommonField.TEST_TYPE_PING:
                                        if ((localTestParamsBean.getDestNodeIp() != null) && (localTestParamsBean.getDestNodeIp().length() >= 1)){
                                            this.selectedData.add(localTestParamsBean);
                                        }else{
                                            showToast("PING测试域名不能为空");
                                        }
                                        break;
                                    case CommonField.TEST_TYPE_HTTP:
                                        if ((localTestParamsBean.getDestNodeIp() != null) && (localTestParamsBean.getDestNodeIp().length() >= 1)){
                                            this.selectedData.add(localTestParamsBean);
                                        }else{
                                            showToast("HTTP测试域名不能为空");
                                        }
                                        break;
                                }
                            }
                            if(dataMap.size()==selectedData.size()){
                                Log.d(TAG,"dataMap.size()="+dataMap.size());
                                this.button.setText(getString(R.string.stop_test));
                                this.button.setTextColor(Color.RED);
//                                this.progressBar.setMessage("测试中");
//                        if (!this.progressBar.isShowing())
//                            this.progressBar.show();
                                this.BTN_STATUS=OpenTestConstant.BTN_MIDDLE;
                                Toast.makeText(this, "开始测试，点击结束查看结果", Toast.LENGTH_SHORT).show();

                                this.presenter.startTest(this, null, this.selectedData);
                            }
                        }else{
                            showToast("请先选择测试项");
                        }
                        break;
                    case OpenTestConstant.BTN_MIDDLE:
                        this.progressBar.setMessage("处理数据中....");
                        if (!this.progressBar.isShowing())
                            this.progressBar.show();
                        this.presenter.stopTest();
                        break;
                }
                break;
        }
    }
    private boolean isChecked(TestChildBean tcb){
        boolean flag=false;

        Iterator it=this.dataMap.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry e= (Map.Entry) it.next();
            TestParamsBean tpb=(TestParamsBean)e.getValue();
            if(tcb.getTestName().equals(tpb.getTestName())){
                flag=true;
                break;
            }
        }

        return flag;
    }

    private void recevieMSG(int action,Bundle paramBundle){
        Log.d(TAG,"action="+action);
        this.button.setText(getString(R.string.begin_test));
        String msg= (String) paramBundle.get("msg");
        ArrayList<TestChildBean> manualList=new ArrayList<TestChildBean>();
        try {
            JSONObject msgJson=new JSONObject(msg);
            JSONArray modelResultList=msgJson.optJSONArray("modleResult");
            if(modelResultList!=null && modelResultList.length()>=0){

                JSONObject testModelType;
                for(int i=0;i<modelResultList.length();i++){
                    testModelType=modelResultList.optJSONObject(i);
                    JSONArray parList= testModelType.optJSONArray("parameters");
                    for(int j=0;j<parList.length();j++){
                        TestChildBean tcb=new TestChildBean();
                        JSONObject jsonTemp=parList.optJSONObject(j);
                        tcb.setTemplateName("手动测试");
                        tcb.setTestName(jsonTemp.optString("testName"));
                        Log.d(TAG,"tcb.getTestName="+tcb.getTestName());
                        tcb.setTestType(jsonTemp.optInt("testType"));
                        if(jsonTemp.optJSONArray("testResult").length()>0){
                            tcb.setResult(jsonTemp.optJSONArray("testResult").get(0).toString());
                        }
//                        if(jsonTemp.optJSONObject("testResult")!=null && jsonTemp.optJSONObject("testResult").length()>0){
//                            tcb.setResult(jsonTemp.optJSONObject("testResult").toString());
//                        }
                        if(isChecked(tcb)){
                            manualList.add(tcb);
                        }
                    }
                }
            }
            this.BTN_STATUS=OpenTestConstant.BTN_STOP;

            if(QuestionConstant.TEST_START_MSG==action){
                Intent localIntent = new Intent();
                localIntent.setClassName(getApplicationContext(), "com.fkty.mobileiq.distribution.app.activity.ManualDetailActivity");
                Bundle localBundle = new Bundle();
                localBundle.putParcelableArrayList("data", manualList);
                Log.d("MA","manualList's length="+manualList.size());
                localIntent.putExtras(localBundle);
                startActivity(localIntent);
                // startActivity(ManualDetailActivity.class, paramBundle);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}