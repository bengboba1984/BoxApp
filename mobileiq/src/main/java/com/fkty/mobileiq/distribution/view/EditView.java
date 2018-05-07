package com.fkty.mobileiq.distribution.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;

/**
 * Created by frank_tracy on 2018/3/9.
 */
public class EditView extends RelativeLayout
{
    private EditText img;
    private TextView mTitleTv;

    public EditView(Context paramContext)
    {
        super(paramContext);
        init(paramContext);
    }

    public EditView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    public EditView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext);
    }

    public EditView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
    {
        super(paramContext, paramAttributeSet, paramInt1);
        init(paramContext);
    }

    public String getEditText()
    {
        if (this.img.getText() != null)
            return this.img.getText().toString().trim();
        return "";
    }

    public String getTitleText()
    {
        if (this.mTitleTv.getText() != null)
            return this.mTitleTv.getText().toString().trim();
        return "";
    }

    public void init(Context paramContext)
    {
        LayoutInflater.from(paramContext).inflate(R.layout.item_manual_gridview, this);
        this.img = ((EditText)findViewById(R.id.item_gridview_content));
        this.mTitleTv = ((TextView)findViewById(R.id.item_gridview_title));
    }

    public boolean isEditEnableD()
    {
        return this.img.isEnabled();
    }

    public boolean isSelected()
    {
        return this.img.isSelected();
    }

    public void setEditEnable(boolean paramBoolean)
    {
        this.img.setEnabled(paramBoolean);
    }

    public void setEditText(String paramString)
    {
        this.img.setText(paramString);
    }

    public void setOnClickListener(View.OnClickListener paramOnClickListener)
    {
        setOnClickListener(paramOnClickListener);
    }

    public void setSelected(boolean paramBoolean)
    {
        this.img.setSelected(paramBoolean);
    }

    public void setTitleText(String paramString)
    {
        this.mTitleTv.setText(paramString);
    }
}