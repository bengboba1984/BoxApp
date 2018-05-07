package com.fkty.mobileiq.distribution.ui.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.TestTypeBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.QuestionConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank_tracy on 2018/3/12.
 */

public class QuestionListAdapter extends BaseAdapter
        implements QuestionConstant
{
    private Context context;
    private List<TestTypeBean> data;

    public QuestionListAdapter(Context paramContext, List<TestTypeBean> paramList)
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
        if (paramView == null)
        {
            paramView = View.inflate(this.context, R.layout.item_list, null);
            localViewHolder = new ViewHolder();
            localViewHolder.title= paramView.findViewById(R.id.item_list_title);
            localViewHolder.rightImg= paramView.findViewById(R.id.item_list_right);
            localViewHolder.statusImg= paramView.findViewById(R.id.item_list_status);
            localViewHolder.progressBar= paramView.findViewById(R.id.item_list_progress);
            localViewHolder.linearLayout= paramView.findViewById(R.id.item_list_layout);
            paramView.setTag(localViewHolder);
        }else{
            localViewHolder = (ViewHolder)paramView.getTag();

        }
        localViewHolder.title.setText(((TestTypeBean)this.data.get(paramInt)).getTestName());
        Log.d("@@@@@@@","((TestTypeBean)this.data.get("+paramInt+")).getTestName()==="+((TestTypeBean)this.data.get(paramInt)).getTestName()+"/status="+((TestTypeBean)this.data.get(paramInt)).getStatus());


        switch (((TestTypeBean)this.data.get(paramInt)).getStatus()){
                default:
                    localViewHolder.progressBar.setVisibility(View.GONE);
                    localViewHolder.linearLayout.setVisibility(View.GONE);
                    break;
                case CommonField.MODLE_STATUS_READY:
                    localViewHolder.progressBar.setVisibility(View.GONE);
                    localViewHolder.linearLayout.setVisibility(View.GONE);
                    break;
                case CommonField.MODLE_STATUS_STOP:
                    localViewHolder.progressBar.setVisibility(View.GONE);
                    localViewHolder.linearLayout.setVisibility(View.GONE);
                    break;
                case CommonField.MODLE_STATUS_TESTING:
                    localViewHolder.progressBar.setVisibility(View.VISIBLE);
                    localViewHolder.linearLayout.setVisibility(View.GONE);
                    break;
                case CommonField.MODLE_STATUS_FINISH:
                    String str = ((TestTypeBean)this.data.get(paramInt)).getResult();
                    try
                    {
                        if(str!=null && !"".equals(str)){
                            JSONObject localJSONObject2 = new JSONObject(str);
                            switch (localJSONObject2.optInt("taskErrorCode")){
                                default:
                                    localViewHolder.statusImg.setImageResource(R.mipmap.err);
                                    break;
                                case 0:
                                    localViewHolder.statusImg.setImageResource(R.mipmap.success);
                                    break;
                            }
                        }

                        localViewHolder.progressBar.setVisibility(View.GONE);
                        localViewHolder.linearLayout.setVisibility(View.VISIBLE);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    break;
        }
        return paramView;
    }

    public void setData(List<TestTypeBean> paramList)
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
        private LinearLayout linearLayout;
        private ProgressBar progressBar;
        private ImageView rightImg;
        private ImageView statusImg;
        private TextView title;

        ViewHolder()
        {
        }
    }
}