package com.fkty.mobileiq.distribution.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fkty.mobileiq.distribution.R;

/**
 * Created by frank_tracy on 2017/12/6.
 */

public class CustomView extends RelativeLayout {
    private ImageView img;
    private TextView mTitleTv;

    public CustomView(Context paramContext)
    {
        super(paramContext);
        init(paramContext);
    }

    public CustomView(Context paramContext, @Nullable AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    public CustomView(Context paramContext, @Nullable AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext);
    }

    public CustomView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
    {
        super(paramContext, paramAttributeSet, paramInt1);
        init(paramContext);
    }

    public void init(Context paramContext)
    {
        LayoutInflater.from(paramContext).inflate(R.layout.module_item, this);
        this.img = ((ImageView)findViewById(R.id.iv_module));
        this.mTitleTv = ((TextView)findViewById(R.id.tv_module));
    }

    public void setImgResource(int paramInt)
    {
        this.img.setImageResource(paramInt);
    }

    public void setTitleText(String paramString)
    {
        this.mTitleTv.setText(paramString);
    }
}
