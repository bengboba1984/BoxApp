package com.fkty.mobileiq.distribution.ui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.app.activity.NetworkActivity;
import com.fkty.mobileiq.distribution.basic.BaseActivity;
import com.fkty.mobileiq.distribution.bean.CaptureFileBean;
import com.fkty.mobileiq.distribution.constant.CommonField;
import com.fkty.mobileiq.distribution.constant.NetWorkConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.fkty.mobileiq.distribution.constant.NetWorkConstant.CAPTURE_HTTP_CODE;

/**
 * Created by frank_tracy on 2018/4/3.
 */

public class CaptureFileListAdapter extends BaseAdapter
{
    private BaseActivity activity;
    private List<CaptureFileBean> data;
    private LayoutInflater inflater;
//    private String activityFrom;

    public CaptureFileListAdapter(List<CaptureFileBean> paramList, BaseActivity paramContext)
    {
        if (paramList != null){
            this.data = paramList;
        }else{
            this.data = new ArrayList();
        }
        this.activity = paramContext;
        this.inflater = LayoutInflater.from(paramContext);
//        activityFrom=activityName;
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
            paramView = View.inflate(this.activity, R.layout.capture_file_list_item, null);
            localViewHolder = new ViewHolder();
            localViewHolder.checkBox = paramView.findViewById(R.id.capture_file_list_item_checkbox);
            localViewHolder.fileName = paramView.findViewById(R.id.capture_file_list_item_filename);
            localViewHolder.fileSize = paramView.findViewById(R.id.capture_file_list_item_filesize);
            localViewHolder.fileTime = paramView.findViewById(R.id.capture_file_list_item_filetime);
            localViewHolder.filedetail = paramView.findViewById(R.id.capture_file_list_item_detail);
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

        if(this.activity instanceof  NetworkActivity){
            SharedPreferences t=activity.getSharedPreferences(CAPTURE_HTTP_CODE, Context.MODE_PRIVATE);
            Log.d("CaptureFileListAdapter:","localCaptureFileBean.getFileName()="+localCaptureFileBean.getFileName()+"|t.getString(localCaptureFileBean.getFileName(),null)="+t.getString(localCaptureFileBean.getFileName().replace(".pcap",""),null));
            if(t.getString(localCaptureFileBean.getFileName().replace(".pcap",""),null)!=null){
                localViewHolder.filedetail.setVisibility(View.VISIBLE);

                localViewHolder.filedetail.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        CaptureFileBean cfb= data.get(paramInt);
                        SharedPreferences captureHttpCode=activity.getSharedPreferences(CAPTURE_HTTP_CODE, Context.MODE_PRIVATE);
                        String httpCode=captureHttpCode.getString(cfb.getFileName().replace(".pcap",""),null);
                        String httpErrorDes=getDescripByHttpCode(httpCode);
                        Log.d("CaptureFileListAdapter:","cfb.getFileName()="+cfb.getFileName()+"/httpCode="+httpCode+"/httpErrorDes="+httpErrorDes);
                        String content="Error Code:"+httpCode+"\nMessage:"+httpErrorDes;
                        Toast.makeText(activity,content,Toast.LENGTH_LONG).show();
                    }
                });

            }
        }



        return paramView;
    }

    private String getDescripByHttpCode(String httpErrorCode){

        String des="";
        for(int i=0;i<NetWorkConstant.httpCode.length;i++){
            if(NetWorkConstant.httpCode[i][0].equals(httpErrorCode)){
                des=NetWorkConstant.httpCode[i][1];
                break;
            }
        }
        return des;
    }

    class ViewHolder
    {
        CheckBox checkBox;
        TextView fileName;
        TextView fileSize;
        ImageView fileStatus;
        TextView fileTime;
        TextView filedetail;
    }
}