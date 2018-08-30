package com.orionst.mymaterialdesignapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class FabHideOnScroll extends FloatingActionButton.Behavior {

    public FabHideOnScroll() {
    }

    public FabHideOnScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child,
                               @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, @ViewCompat.NestedScrollType int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
//        child -> Floating Action Button
//        if (child.getVisibility() == View.VISIBLE && dyConsumed > 0) {
//            child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
//                @Override
//                public void onHidden(FloatingActionButton fab) {
//                    super.onHidden(fab);
//                    //
//                    fab.setVisibility(View.INVISIBLE);
//                }
//            });
//        } else if (child.getVisibility() == View.INVISIBLE && dyConsumed < 0) {
//            child.show();
//        }

        if (dyConsumed > 0) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            child.animate()
                    .translationX(child.getWidth() + layoutParams.rightMargin)
                    .translationY(child.getHeight() + layoutParams.bottomMargin)
                    .scaleX(0)
                    .scaleY(0)
                    .setInterpolator(new LinearInterpolator()).start();
        } else if (dyConsumed < 0) {
            child.animate()
                    .translationX(0)
                    .translationY(0)
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(new LinearInterpolator()).start();
        }

    }

    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target,
                                       @ViewCompat.ScrollAxis int axes, @ViewCompat.NestedScrollType int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

}
