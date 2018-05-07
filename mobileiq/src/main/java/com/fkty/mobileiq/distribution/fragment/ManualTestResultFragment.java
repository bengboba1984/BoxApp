package com.fkty.mobileiq.distribution.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.TestChildBean;
import com.fkty.mobileiq.distribution.bean.TestResultFatherBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.json.TestFieldJson;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.ui.adapter.ManualTestResultAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank_tracy on 2018/3/30.
 */

public class ManualTestResultFragment extends Fragment
{
    private ManualTestResultAdapter adapter;
    //private TestRecordBean bean;
    private List<TestChildBean> beans;
    private List<TestResultFatherBean> data;
    private ListView listView;
    private int modeType;
    private String result;
    private int testType;
    private View view;

    private void initData()
    {
        this.data = new ArrayList();
        TestResultFatherBean localTestResultFatherBean1;
        if ((this.beans != null) && (this.beans.size() > 0)){
            Log.d("tttt","beans's length="+beans.size());
            for(int i=0;i<beans.size();i++){
                TestChildBean tcb = this.beans.get(i);
                TestResultFatherBean trfb = new TestResultFatherBean();
                trfb.setTestName(tcb.getTestName());
                trfb.setTestType(tcb.getTestType());
                trfb.setName(tcb.getTemplateName());
                ArrayList resultBeanList=new ArrayList();
                try {
                    switch (trfb.getTestType()){
                        default:
                            break;
                        case CommonField.TEST_TYPE_PING:
                            List pingFieldList = DataManager.getInstance().getPingField();
                            resultBeanList.addAll(TestFieldJson.parseFieldColumn(pingFieldList, new JSONObject(tcb.getResult())));
                            break;
                        case CommonField.TEST_TYPE_DNS:
                            List dnsFieldList = DataManager.getInstance().getDnsField();
                            resultBeanList.addAll(TestFieldJson.parseFieldColumn(dnsFieldList, new JSONObject(tcb.getResult())));
                            break;
                        case CommonField.TEST_TYPE_HTTP:
                            List httpFieldList = DataManager.getInstance().getHttpField();
                            resultBeanList.addAll(TestFieldJson.parseFieldColumn(httpFieldList, new JSONObject(tcb.getResult())));
                            break;
                        case CommonField.TEST_TYPE_TRACE:
                            List traceFieldList = DataManager.getInstance().getTraceField();
                            resultBeanList.addAll(TestFieldJson.parseFieldColumn(traceFieldList, new JSONObject(tcb.getResult())));
                            break;
                        case CommonField.TEST_TYPE_SPEED:
                            List speedFieldList = DataManager.getInstance().getSpeedField();
                            resultBeanList.addAll(TestFieldJson.parseFieldColumn(speedFieldList, new JSONObject(tcb.getResult())));
                            break;
                    }
                    trfb.setData(resultBeanList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.data.add(trfb);
            }
        }

        this.adapter = new ManualTestResultAdapter(getActivity(), this.data);
        this.listView.setAdapter(this.adapter);
    }

    private void initView(View paramView)
    {
        this.listView = paramView.findViewById(R.id.manual_test_result_listview);
    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        Bundle localBundle = getArguments();
        if (localBundle != null)
        {
            this.modeType = localBundle.getInt("modeType");
            Log.d("MFragment","modeType="+this.modeType);
            switch (this.modeType)
            {
                default:
                    break;
                case CommonField.MODLE_TYPE_MANUAL_TEST:
                    this.beans = localBundle.getParcelableArrayList("data");
                    break;
                case CommonField.MODLE_TYPE_TEST_RECORE:
//                    this.bean = ((TestRecordBean)localBundle.getParcelable("data"));
//                    this.beans = this.bean.getList();
                    break;
            }
        }
            this.view = paramLayoutInflater.inflate(R.layout.fragment_manual_test_result, null);
            initView(this.view);
            initData();
            return this.view;
    }
}