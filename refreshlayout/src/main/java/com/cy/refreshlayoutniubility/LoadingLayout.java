package com.cy.refreshlayoutniubility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable2;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: cy
 * @CreateDate: 2020/8/26 18:50
 * @UpdateUser:
 * @UpdateDate: 2020/8/26 18:50
 * @UpdateRemark:
 * @Version:
 */
public class LoadingLayout extends FrameLayout {
    private IAnimationView loadingView;
    private View contentView;
    private LayoutParams layoutParams_loading;

    public LoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadingView = new RotateLineCircleView(context);
        layoutParams_loading = new LayoutParams(ScreenUtils.dpAdapt(context, 30), ScreenUtils.dpAdapt(context, 30));
        layoutParams_loading.gravity = Gravity.CENTER;
        setLoadingView(loadingView,layoutParams_loading);
    }

    @Override
    protected final void onFinishInflate() {
        super.onFinishInflate();
        LogUtils.log("getChildCount",getChildCount());
        if (getChildCount() > 2)
            throw new RuntimeException("Exception:You can add only one contentView in " + getClass().getName());
        View view = getChildAt(1);
        if (view != null) {
            contentView=view;
            setContentView(contentView);
        }
    }

    public LoadingLayout setLoadingView(IAnimationView animationView, FrameLayout.LayoutParams layoutParams) {
        removeView(loadingView.getView());
        this.loadingView = animationView;
        addView(loadingView.getView(),0,layoutParams);
        return this;
    }

    public LoadingLayout setContentView(View view) {
        removeView(contentView);
        this.contentView = view;
        addView(contentView, getChildCount(), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        return this;
    }

    public LoadingLayout startLoadAnimation() {
        if(isRunning())return this;
        loadingView.startLoadAnimation();
        return this;
    }

    public LoadingLayout stopLoadAnimation() {
        loadingView.stopLoadAnimation();
        return this;
    }

    public LoadingLayout stopLoadAnimation_removeLoadingView() {
        stopLoadAnimation();
        removeLoadingView();
        return this;
    }

    public LoadingLayout removeLoadingView() {
        removeView(loadingView.getView());
        return this;
    }

    public IAnimationView getLoadingView() {
        return loadingView;
    }

    public View getContentView() {
        return contentView;
    }

    public boolean isRunning() {
        return loadingView.isRunning();
    }
}
