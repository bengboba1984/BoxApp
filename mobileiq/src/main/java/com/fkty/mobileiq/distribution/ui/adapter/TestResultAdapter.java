package com.fkty.mobileiq.distribution.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.TestResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank_tracy on 2018/3/20.
 */

public class TestResultAdapter extends BaseAdapter
{
    private Context context;
    private List<TestResultBean> data;

    public TestResultAdapter(Context paramContext, List<TestResultBean> paramList)
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
            paramView = View.inflate(this.context, R.layout.item_test_result, null);
            localViewHolder = new ViewHolder();
            localViewHolder.title= paramView.findViewById(R.id.test_result_list_title);
            localViewHolder.content= paramView.findViewById(R.id.test_result_list_content);
            paramView.setTag(localViewHolder);
        }else{
            localViewHolder = (ViewHolder)paramView.getTag();
        }
        localViewHolder.title.setText(this.data.get(paramInt).getTestName());
        localViewHolder.content.setText(this.data.get(paramInt).getContent());
        return paramView;
    }

    public void setData(List<TestResultBean> paramList)
    {
        if (paramList != null)
        {
            this.data = paramList;
            return;
        }
        this.data = new ArrayList();
    }

    class ViewHolder
    {
        private TextView content;
        private TextView title;

        ViewHolder()
        {
        }
    }
}