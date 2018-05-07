package com.fkty.mobileiq.distribution.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;

/**
 * Created by frank_tracy on 2018/3/9.
 */

public class LinearView extends RelativeLayout
{
    private ImageView img;
    private TextView mTitleTv;

    public LinearView(Context paramContext)
    {
        super(paramContext);
        init(paramContext);
    }

    public LinearView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    public LinearView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext);
    }

    public LinearView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
    {
        super(paramContext, paramAttributeSet, paramInt1);
        init(paramContext);
    }

    public void init(Context paramContext)
    {
        LayoutInflater.from(paramContext).inflate(R.layout.linear_item, this);
        this.img = ((ImageView)findViewById(R.id.linear_img));
        this.mTitleTv = ((TextView)findViewById(R.id.linear_titel));
    }

    public boolean isSelected()
    {
        return this.img.isSelected();
    }

    public void setImgResource(int paramInt)
    {
        this.img.setImageResource(paramInt);
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
