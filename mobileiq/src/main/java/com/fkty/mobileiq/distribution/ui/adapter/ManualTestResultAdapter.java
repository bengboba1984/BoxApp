package com.fkty.mobileiq.distribution.ui.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.TestResultFatherBean;
import com.fkty.mobileiq.distribution.ui.MyListview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank_tracy on 2018/3/30.
 */

public class ManualTestResultAdapter extends BaseAdapter
{
    private Context context;
    private List<TestResultFatherBean> data;

    public ManualTestResultAdapter(Context paramContext, List<TestResultFatherBean> paramList)
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
        if (this.data != null)
            return this.data.size();
        return 0;
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
        TestResultFatherBean localTestResultFatherBean;
        if (paramView == null)
        {
            paramView = View.inflate(this.context, R.layout.manual_test_item, null);
            localViewHolder = new ViewHolder();
            localViewHolder.testName=paramView.findViewById(R.id.manual_item_list_name);
            localViewHolder.testStatus=paramView.findViewById(R.id.manual_item_list_code);
            localViewHolder.testRight= paramView.findViewById(R.id.manual_item_list_right);
            localViewHolder.layout= paramView.findViewById(R.id.manual_item_list_layout);
            localViewHolder.listView=paramView.findViewById(R.id.manual_item_list_listview);
            paramView.setTag(localViewHolder);
        }else{
            localViewHolder = (ViewHolder)paramView.getTag();
        }
        Log.d("MTRA","getView:"+localViewHolder.testName);
        localTestResultFatherBean = (TestResultFatherBean)this.data.get(paramInt);
        localViewHolder.testName.setText(localTestResultFatherBean.getTestName());
        localViewHolder.testStatus.setImageResource(R.mipmap.success);
        TestResultAdapter localTestResultAdapter = new TestResultAdapter(this.context, localTestResultFatherBean.getData());
        localViewHolder.listView.setAdapter(localTestResultAdapter);
        localViewHolder.layout.setTag(localViewHolder);
        localViewHolder.layout.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                if (((ViewHolder)paramView.getTag()).listView.getVisibility() == View.VISIBLE)
                {
                    ((ViewHolder)paramView.getTag()).testRight.setImageResource(R.mipmap.item_right);
                    ((ViewHolder)paramView.getTag()).listView.setVisibility(View.GONE);
                }else{
                    ((ViewHolder)paramView.getTag()).testRight.setImageResource(R.mipmap.logout_bg);
                    ((ViewHolder)paramView.getTag()).listView.setVisibility(View.VISIBLE);
                }
            }
        });

        return paramView;
    }

    public void setData(List<TestResultFatherBean> paramList)
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
        private LinearLayout layout;
        private MyListview listView;
        private TextView testName;
        private ImageView testRight;
        private ImageView testStatus;

        ViewHolder()
        {
        }
    }
}