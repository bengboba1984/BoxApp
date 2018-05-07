package com.fkty.mobileiq.distribution.utils;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by frank_tracy on 2018/3/12.
 */

public class AnimationUtils {
    public static RotateAnimation generateRotateAnimation()
    {
        RotateAnimation localRotateAnimation = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
        localRotateAnimation.setDuration(1000L);
        localRotateAnimation.setRepeatCount(2147483647);
        localRotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        localRotateAnimation.setFillAfter(true);
        return localRotateAnimation;
    }

//    public static ValueAnimator generateSlideDownAnimator(View paramView, int paramInt)
//    {
//        ValueAnimator localValueAnimator = ValueAnimator.ofInt(new int[] { 0, paramInt });
//        localValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(paramView)
//        {
//            public void onAnimationUpdate(ValueAnimator paramValueAnimator)
//            {
//                this.paramView.getLayoutParams().height = ((Integer)paramValueAnimator.getAnimatedValue()).intValue();
//                this.paramView.requestLayout();
//            }
//        });
//        return localValueAnimator;
//    }
}
