package com.fkty.mobileiq.distribution.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.OttSettingInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank_tracy on 2017/12/11.
 */

public class OTTSettingAdapter extends BaseAdapter{
    private Context context;
    private List<OttSettingInfoBean> data;

    public OTTSettingAdapter(Context paramContext, List<OttSettingInfoBean> paramList)
    {
        if (paramList != null)
            this.data = paramList;
        else
            this.data = new ArrayList();

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
        if (paramView == null)
        {
            paramView = View.inflate(this.context, R.layout.ott_item_list, null);
            localViewHolder = new ViewHolder();
            localViewHolder.title= paramView.findViewById(R.id.ott_item_list_title);
            localViewHolder.content=paramView.findViewById(R.id.ott_item_list_content);
            paramView.setTag(localViewHolder);
        }
        else
        {
            localViewHolder = (ViewHolder)paramView.getTag();
        }
        localViewHolder.title.setText(((OttSettingInfoBean)this.data.get(paramInt)).getName());
        localViewHolder.content.setText(((OttSettingInfoBean)this.data.get(paramInt)).getContent());

        return paramView;
    }

    public void setData(List<OttSettingInfoBean> paramList)
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
