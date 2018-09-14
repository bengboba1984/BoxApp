package com.fkty.mobileiq.distribution.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.TestResultBean;
import com.fkty.mobileiq.distribution.bean.TestShowFieldBean;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.json.TestFieldJson;
import com.fkty.mobileiq.distribution.manager.DataManager;
import com.fkty.mobileiq.distribution.ui.adapter.TestResultDetailAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TestResultDetailFragment extends Fragment {
    private TestResultDetailAdapter adapter;
    private List<List<TestResultBean>> data;
    private TestTypeBean bean;
    private View view;
    private ListView listView;
    private TextView testResultTitle;
    private String result;
    private int testType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle localBundle = getArguments();
        if (localBundle != null)
            this.bean = localBundle.getParcelable("data");
        this.view = inflater.inflate(R.layout.fragment_test_result, null);
        initView(this.view);
        initData();
        return this.view;
    }

    private void initView(View paramView)
    {
        this.listView = paramView.findViewById(R.id.test_result_listview);
        this.testResultTitle=paramView.findViewById(R.id.test_result_title);
    }
    private void initData()
    {
        this.data = new ArrayList();
        if (this.bean != null)
        {
            this.result = this.bean.getResult();
            this.testType = this.bean.getTestType();
        }
        if ((this.result != null) && (this.result.length() > 0)) try {
            JSONArray resultJSON = new JSONArray(this.result);
            switch (this.testType) {
                default:
                    break;
                case CommonField.TEST_TYPE_TRACE_HOP:
                    List<TestShowFieldBean> traceDetailFieldList = DataManager.getInstance().getTraceSubField();

                    List<TestResultBean> titleLine = new ArrayList<>();
                    for (int i = 0; i < traceDetailFieldList.size(); i++) {
                        TestShowFieldBean tsfb = traceDetailFieldList.get(i);
                        titleLine.add(new TestResultBean(tsfb.getName(), tsfb.getName()));
                    }
                    titleLine.add(new TestResultBean("序号", "序号"));
                    this.data.add(titleLine);


                    for (int i = 0; i < resultJSON.length(); i++) {
                        JSONObject dataLineObj = resultJSON.optJSONObject(i);
                        List dataLine=TestFieldJson.parseFieldColumn(traceDetailFieldList, dataLineObj);
                        dataLine.add(new TestResultBean("序号", String.valueOf(i+1)));//sequence number
                        this.data.add(dataLine);
                    }

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.adapter = new TestResultDetailAdapter(getActivity(), this.data);
        this.listView.setAdapter(this.adapter);

        this.testResultTitle.setText(this.bean.getTestName());
    }


}
