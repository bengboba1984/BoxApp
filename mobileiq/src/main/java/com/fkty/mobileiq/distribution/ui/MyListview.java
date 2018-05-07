package com.fkty.mobileiq.distribution.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by frank_tracy on 2017/12/11.
 */

public class MyListview extends ListView
    {
  public MyListview(Context paramContext)
        {
            super(paramContext);
        }

  public MyListview(Context paramContext, AttributeSet paramAttributeSet)
        {
            super(paramContext, paramAttributeSet);
        }

  public MyListview(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
        {
            super(paramContext, paramAttributeSet, paramInt);
        }

  public MyListview(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
        {
            super(paramContext, paramAttributeSet, paramInt1);
        }

    protected void onMeasure(int paramInt1, int paramInt2)
    {
        super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
