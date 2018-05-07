package com.fkty.mobileiq.distribution.ui.adapter;

import com.fkty.mobileiq.distribution.R;
import com.fkty.mobileiq.distribution.bean.TestParamsBean;
import com.fkty.mobileiq.distribution.constant.CommonField;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank_tracy on 2018/3/22.
 */

public class MunualRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private List<TestParamsBean> data;
    public OnRecyclerViewListener onRecyclerViewListener;

    public MunualRecyclerAdapter(List<TestParamsBean> paramList, Context paramContext)
    {
        if (paramList != null){
            this.data = paramList;
        }else{
            this.data = new ArrayList();
        }
        this.context = paramContext;
    }

    public int getItemCount()
    {
        return this.data.size();
    }

    public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
    {
        ViewHolder localViewHolder = (ViewHolder)paramViewHolder;
        localViewHolder.position = paramInt;
        TestParamsBean localTestParamsBean = this.data.get(paramInt);
        localViewHolder.nameTv.setText(localTestParamsBean.getTestName());
        localViewHolder.layout.setVisibility(View.GONE);
        switch (localTestParamsBean.getTestType())
        {
            default:
                localViewHolder.layout.setVisibility(View.GONE);
                break;
            case CommonField.TEST_TYPE_PING:
                localViewHolder.editView.setText(localTestParamsBean.getDestNodeIp());
                localViewHolder.layout.setVisibility(View.VISIBLE);
                break;
            case CommonField.TEST_TYPE_HTTP:
                localViewHolder.editView.setText(localTestParamsBean.getDestNodeIp());
                localViewHolder.layout.setVisibility(View.VISIBLE);
                break;
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
        return new ViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.item_manual_recyclerview, paramViewGroup, false));
    }

    public void setListener(OnRecyclerViewListener paramOnRecyclerViewListener)
    {
        this.onRecyclerViewListener = paramOnRecyclerViewListener;
    }

    public interface OnRecyclerViewListener
    {
        void onEditChange(int paramInt, String paramString);

        void onItemClick(int paramInt, boolean paramBoolean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, TextWatcher
    {
        public EditText editView;
        public ImageView imageView;
        public LinearLayout layout;
        public TextView nameTv;
        public int position;

        public ViewHolder(View localView)
        {
            super(localView);
            this.nameTv = localView.findViewById(R.id.item_manual_test_name);
            this.imageView = localView.findViewById(R.id.item_manual_test_status);
            this.editView = localView.findViewById(R.id.item_manual_test_edit);
            this.layout = localView.findViewById(R.id.item_manual_test_edit_layout);
            localView.setOnClickListener(this);
            this.editView.addTextChangedListener(this);
        }

        public void afterTextChanged(Editable paramEditable)
        {
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
        {
        }

        public void onClick(View paramView)
        {
            if (MunualRecyclerAdapter.this.onRecyclerViewListener != null)
            {
                if (this.imageView.isSelected())
                {
                    this.editView.setEnabled(false);
                    this.imageView.setSelected(false);
                    MunualRecyclerAdapter.this.onRecyclerViewListener.onItemClick(this.position, false);
                }else{
                    this.editView.setEnabled(true);
                    this.imageView.setSelected(true);
                    MunualRecyclerAdapter.this.onRecyclerViewListener.onItemClick(this.position, true);
                }
            }
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
        {
            MunualRecyclerAdapter.this.onRecyclerViewListener.onEditChange(this.position, paramCharSequence.toString());
        }
    }
}