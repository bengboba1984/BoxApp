package com.fkty.mobileiq.distribution.ui.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by frank_tracy on 2017/12/6.
 */

public class MainViewPagerAdapter extends PagerAdapter {
    private List<View> views;

    public MainViewPagerAdapter(List<View> paramList)
    {
        this.views = paramList;
    }

    public void destroyItem(View paramView, int paramInt, Object paramObject)
    {
        ((ViewPager)paramView).removeView((View)this.views.get(paramInt));
    }

    public void finishUpdate(View paramView)
    {
    }

    public int getCount()
    {
        if (this.views != null)
            return this.views.size();
        return 0;
    }

    public Object instantiateItem(View paramView, int paramInt)
    {
        ((ViewPager)paramView).addView((View)this.views.get(paramInt), 0);
        return this.views.get(paramInt);
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
        return paramView == paramObject;
    }

    public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
    {
    }

    public Parcelable saveState()
    {
        return null;
    }

    public void startUpdate(View paramView)
    {
    }
}
