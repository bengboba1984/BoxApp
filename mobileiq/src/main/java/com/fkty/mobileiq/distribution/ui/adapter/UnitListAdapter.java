package com.fkty.mobileiq.distribution.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;

import java.util.List;

/**
 * Created by frank_tracy on 2017/12/5.
 */

public class UnitListAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;

    public UnitListAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        if (this.data != null)
            return this.data.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return this.data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view ==null){
            view=View.inflate(context, R.layout.item_login_unit,null);
            vh=new ViewHolder();
            vh.content=view.findViewById(R.id.item_unit_text);
            view.setTag(vh);
        }else{
            vh=(ViewHolder)view.getTag();

        }
        vh.content.setText((CharSequence)this.data.get(i));
        return view;
    }
    class ViewHolder
    {
        private TextView content;

        ViewHolder()
        {
        }
    }
}
