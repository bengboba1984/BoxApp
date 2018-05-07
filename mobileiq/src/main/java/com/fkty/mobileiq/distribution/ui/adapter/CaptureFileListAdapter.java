package com.fkty.mobileiq.distribution.ui.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.CaptureFileBean;
import com.fkty.mobileiq.distribution.constant.CommonField;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by frank_tracy on 2018/4/3.
 */

public class CaptureFileListAdapter extends BaseAdapter
{
    private Context context;
    private List<CaptureFileBean> data;
    private LayoutInflater inflater;

    public CaptureFileListAdapter(List<CaptureFileBean> paramList, Context paramContext)
    {
        if (paramList != null){
            this.data = paramList;
        }else{
            this.data = new ArrayList();
        }
        this.context = paramContext;
        this.inflater = LayoutInflater.from(paramContext);
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

    public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        final ViewHolder localViewHolder;
        if (paramView == null)
        {
            paramView = View.inflate(this.context, R.layout.capture_file_list_item, null);
            localViewHolder = new ViewHolder();
            localViewHolder.checkBox = paramView.findViewById(R.id.capture_file_list_item_checkbox);
            localViewHolder.fileName = paramView.findViewById(R.id.capture_file_list_item_filename);
            localViewHolder.fileSize = paramView.findViewById(R.id.capture_file_list_item_filesize);
            localViewHolder.fileTime = paramView.findViewById(R.id.capture_file_list_item_filetime);
            localViewHolder.fileStatus = paramView.findViewById(R.id.capture_file_list_item_status);
            paramView.setTag(localViewHolder);
        }else{
            localViewHolder = (ViewHolder)paramView.getTag();
        }

        CaptureFileBean localCaptureFileBean = this.data.get(paramInt);
        localViewHolder.fileTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date(localCaptureFileBean.getFileTime())));
        localViewHolder.fileSize.setText(localCaptureFileBean.getFileSize());
        localViewHolder.fileName.setText(localCaptureFileBean.getFileName());
        localViewHolder.checkBox.setChecked(localCaptureFileBean.isSelector());

        localViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
                localViewHolder.checkBox.setChecked(paramBoolean);
                data.get(paramInt).setSelector(paramBoolean);
            }
        });
        switch (localCaptureFileBean.getFileStatus()) {
            default:
                break;
            case CommonField.UPLOAD_NOT:
                localViewHolder.fileStatus.setImageResource(R.mipmap.upload_not);
                break;
            case CommonField.UPLOAD_YES:
                localViewHolder.fileStatus.setImageResource(R.mipmap.upload_yes);
                break;
        }
        return paramView;
    }

    class ViewHolder
    {
        CheckBox checkBox;
        TextView fileName;
        TextView fileSize;
        ImageView fileStatus;
        TextView fileTime;
    }
}