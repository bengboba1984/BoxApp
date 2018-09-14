package com.fkty.mobileiq.distribution.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.app.activity.QuestionDetailActivity;
import com.fkty.mobileiq.distribution.bean.TestResultBean;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.json.TestFieldJson;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.ui.adapter.TestResultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TestResultFragment extends Fragment implements AdapterView.OnItemClickListener
{
    private TestResultAdapter adapter;
    private TestTypeBean bean;
    private List<TestResultBean> data;
    private ListView listView;
    private String result;
    private int testType;
    private TextView testResultTitle;
    private View view;

    private void initData()
    {
        this.data = new ArrayList();
        if (this.bean != null)
        {
            this.result = this.bean.getResult();
            this.testType = this.bean.getTestType();
        }
        Log.d("TestResultFragment","initData:result="+this.result);
        Log.d("TestResultFragment","initData:testType="+this.testType);
        if ((this.result != null) && (this.result.length() > 0)){
        try
        {
            JSONObject resultJSON = new JSONObject(this.result);
            switch (this.testType)
            {
                default:
                    break;
                case CommonField.TEST_TYPE_PING:
                    List pingFieldList = DataManager.getInstance().getPingField();
                    this.data.addAll(TestFieldJson.parseFieldColumn(pingFieldList, resultJSON));
                    break;
                case CommonField.TEST_TYPE_DNS:
                    List dnsFieldList = DataManager.getInstance().getDnsField();
                    this.data.addAll(TestFieldJson.parseFieldColumn(dnsFieldList, resultJSON));
                    break;
                case CommonField.TEST_TYPE_HTTP:
                    List httpFieldList = DataManager.getInstance().getHttpField();
                    this.data.addAll(TestFieldJson.parseFieldColumn(httpFieldList, resultJSON));
                    break;
                case CommonField.TEST_TYPE_TRACE:
                    List traceFieldList = DataManager.getInstance().getTraceField();
                    this.data.addAll(TestFieldJson.parseFieldColumn(traceFieldList, resultJSON));
                    break;
//                case CommonField.TEST_TYPE_TRACE_SUB:
//                    List traceSubFieldList = DataManager.getInstance().getTraceSubField();
//                    this.data.addAll(TestFieldJson.parseFieldColumn(traceSubFieldList, resultJSON));
//                    break;
//                case CommonField.TEST_TYPE_TRACE_HOP:
//                    List traceHopFieldList = DataManager.getInstance().getTraceHopField();
//                    this.data.addAll(TestFieldJson.parseFieldColumn4SameCol(traceHopFieldList, resultJSON));
//                    break;
                case CommonField.TEST_TYPE_SPEED:
                    List speedFieldList = DataManager.getInstance().getSpeedField();
                    this.data.addAll(TestFieldJson.parseFieldColumn(speedFieldList, resultJSON));
                    break;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
        this.adapter = new TestResultAdapter(getActivity(), this.data);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(this);

        this.testResultTitle.setText(this.bean.getTestName());
    }

    private void initView(View paramView)
    {
        this.listView = paramView.findViewById(R.id.test_result_listview);
//        this.textView = paramView.findViewById(R.id.test_result_conclusion);
        this.testResultTitle=paramView.findViewById(R.id.test_result_title);
    }

    public void onCreate( Bundle paramBundle)
    {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
//        Log.d("@@@@@@@","@@@@@@@@@@@@@");
        Bundle localBundle = getArguments();
        if (localBundle != null)
            this.bean = localBundle.getParcelable("data");
        this.view = paramLayoutInflater.inflate(R.layout.fragment_test_result, null);
        initView(this.view);
        initData();
        return this.view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        TestResultBean trBean = this.data.get(position);
Log.d(TAG,"this.testType="+this.testType);
        JSONObject rsJson;
        switch (this.testType)
        {
            default:
                break;
//            case CommonField.TEST_TYPE_TRACE_HOP:
//                try {
//                    Log.d(TAG,"bean.getResult()="+bean.getResult());
//                    rsJson = new JSONObject(bean.getResult());
//                    JSONArray hopDatas=rsJson.optJSONArray("hopDatas");
//                    TestTypeBean ttb=new TestTypeBean();
//                    ttb.setTestName("路由明细"+(position+1));
//                    ttb.setTestType(CommonField.TEST_TYPE_TRACE_SUB);
//                    ttb.setResult(hopDatas.optJSONObject(position).toString());
//                    Log.d(TAG,"TEST_TYPE_TRACE_HOP：ttb.getResult()="+ttb.getResult());
//                    Intent localIntent = new Intent(getActivity(), QuestionDetailActivity.class);
//
//                    localIntent.putExtra("data",ttb);
//                    Log.d(TAG,"onItemClick:data.getResult"+ttb.getResult());
//                    startActivity(localIntent);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                break;
            case CommonField.TEST_TYPE_TRACE:
                TestResultBean trBean = this.data.get(position);
                if(CommonField.SHOW_SUB_DATA.equals(trBean.getTestName())){
                    try {
                        Log.d(TAG,"bean.getResult()="+bean.getResult());
                        rsJson=new JSONObject(bean.getResult());
                        JSONArray rsSubData=rsJson.optJSONArray("resultSubData");
//                        StringBuffer hopStr=new StringBuffer("{");
//                        StringBuffer hopDatas=new StringBuffer("\"hopDatas\":[");
//                        for(int i=0;i<rsSubData.length();i++){
//                            hopStr.append("\"hopNumber-").append(i+1).append("\":\"").append(i+1).append("\",");
//                            hopDatas.append(rsSubData.optJSONObject(i).toString()).append(",");
//                        }
//                        hopDatas.deleteCharAt(hopDatas.length() - 1).append("]");
//                        hopStr.append(hopDatas).append("}");
//                        Log.d(TAG,"hopStr="+hopStr.toString());

                        TestTypeBean ttb=new TestTypeBean();
                        ttb.setTestName("路由明细");
                        ttb.setTestType(CommonField.TEST_TYPE_TRACE_HOP);
                        ttb.setResult(rsSubData.toString());
                        Intent localIntent = new Intent(getActivity(), QuestionDetailActivity.class);

                        localIntent.putExtra("data",ttb);
                        Log.d(TAG,"onItemClick:data.getResult"+ttb.getResult());
                        startActivity(localIntent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

}