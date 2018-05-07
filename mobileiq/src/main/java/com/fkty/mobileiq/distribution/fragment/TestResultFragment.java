package com.fkty.mobileiq.distribution.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.TestResultBean;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.json.TestFieldJson;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.ui.adapter.TestResultAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestResultFragment extends Fragment
{
    private TestResultAdapter adapter;
    private TestTypeBean bean;
    private List<TestResultBean> data;
    private ListView listView;
    private String result;
    private int testType;
    private TextView textView;
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

    }

    private void initView(View paramView)
    {
        this.listView = paramView.findViewById(R.id.test_result_listview);
        this.textView = paramView.findViewById(R.id.test_result_conclusion);
    }

    public void onCreate( Bundle paramBundle)
    {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        Log.d("@@@@@@@","@@@@@@@@@@@@@");
        Bundle localBundle = getArguments();
        if (localBundle != null)
            this.bean = localBundle.getParcelable("data");
        this.view = paramLayoutInflater.inflate(R.layout.fragment_test_result, null);
        initView(this.view);
        initData();
        return this.view;
    }
}