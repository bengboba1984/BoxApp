package com.fkty.mobileiq.distribution.ui.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.TestResultBean;

import java.util.ArrayList;
import java.util.List;
import static android.content.ContentValues.TAG;
/**
 * Created by frank_tracy on 2018/8/24.
 */

public class TestResultDetailAdapter extends BaseAdapter {
    private Context context;
    private List<List<TestResultBean>> data;

    public TestResultDetailAdapter(Context paramContext, List<List<TestResultBean>>  paramList)
    {
        if (paramList != null){
            this.data = paramList;
        }else{
            this.data = new ArrayList();
        }
        this.context = paramContext;
    }

    public int getCount()
    {
        if (this.data != null){
            return this.data.size();
        }else{
            return 0;
        }
    }

    public Object getItem(int paramInt)
    {
        return this.data.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        ViewHolder localViewHolder;
        if (paramView == null)
        {
            paramView = View.inflate(this.context, R.layout.fragment_test_result_detail, null);
            localViewHolder = new ViewHolder();
            localViewHolder.seqNum= paramView.findViewById(R.id.test_result_detail_list_no);
//            localViewHolder.spentTime= paramView.findViewById(R.id.test_result_detail_list_time);
            localViewHolder.ipAddress= paramView.findViewById(R.id.test_result_detail_list_ip);
            localViewHolder.avgDelay= paramView.findViewById(R.id.test_result_detail_list_avgDelay);
            localViewHolder.lossPercent= paramView.findViewById(R.id.test_result_detail_list_lossPercent);
            localViewHolder.avgJitter= paramView.findViewById(R.id.test_result_detail_list_avgJitter);

            paramView.setTag(localViewHolder);
        }
        localViewHolder = (ViewHolder)paramView.getTag();
        localViewHolder.seqNum.setText(this.data.get(paramInt).get(5).getContent());
//        localViewHolder.spentTime.setText(this.data.get(paramInt).get(0).getContent());
        localViewHolder.ipAddress.setText(this.data.get(paramInt).get(1).getContent());
        localViewHolder.avgDelay.setText(this.data.get(paramInt).get(2).getContent());
        localViewHolder.lossPercent.setText(this.data.get(paramInt).get(3).getContent());
        localViewHolder.avgJitter.setText(this.data.get(paramInt).get(4).getContent());

        return paramView;
    }

    public void setData(List<List<TestResultBean>> paramList)
    {
        if (paramList != null)
        {
            this.data = paramList;
        }else{
            this.data = new ArrayList();
        }
    }

    class ViewHolder
    {
        private TextView seqNum;
//        private TextView spentTime;
        private TextView ipAddress;
        private TextView avgDelay;
        private TextView lossPercent;
        private TextView avgJitter;

        ViewHolder()
        {
        }
    }
}
