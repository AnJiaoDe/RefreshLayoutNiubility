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
public class LoadingLayout extends RelativeLayout {
    private IAnimationView loadingView;
    private View contentView;
    private View showingView;
    public LoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadingView = new RotateLineCircleView(context);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ScreenUtils.dpAdapt(context, 30), ScreenUtils.dpAdapt(context, 30));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        loadingView.getView().setLayoutParams(layoutParams);
    }

    @Override
    protected final void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1)
            throw new RuntimeException("Exception:You can add only one contentView in " + getClass().getName());
        View view = getChildAt(0);
        if (view != null){
            contentView = view;
            showingView=contentView;
        }
    }

    public LoadingLayout setLoadingView(IAnimationView animationView,ViewGroup.LayoutParams layoutParams) {
        this.loadingView = animationView;
        loadingView.getView().setLayoutParams(layoutParams);
        return this;
    }

    public LoadingLayout setContentView(View view) {
        this.contentView = view;
        return this;
    }

    public LoadingLayout setShowingView(View view, ViewGroup.LayoutParams layoutParams) {
        if (view == null) return this;
        ViewGroup viewParent= (ViewGroup) view.getParent();
        if(viewParent!=null)viewParent.removeView(view);
        removeView(showingView);
        this.showingView = view;
        addView(showingView, getChildCount(), layoutParams);
        return this;
    }

    public LoadingLayout setShowingView(View view) {
        setShowingView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return this;
    }

    public LoadingLayout startLoadAnimation() {
        setShowingView(loadingView.getView(), loadingView.getView().getLayoutParams());
        loadingView.startLoadAnimation();
        return this;
    }

    public LoadingLayout stopLoadAnimation() {
        loadingView.stopLoadAnimation();
        setShowingView(contentView);
        return this;
    }

    public IAnimationView getLoadingView() {
        return loadingView;
    }

    public View getContentView() {
        return contentView;
    }

    public View getShowingView() {
        return showingView;
    }
}
