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
    public LoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadingView = new RotateLineCircleView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ScreenUtils.dpAdapt(context, 30), ScreenUtils.dpAdapt(context, 30));
        layoutParams.gravity=Gravity.CENTER;
        loadingView.getView().setLayoutParams(layoutParams);
    }

    @Override
    protected final void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1)
            throw new RuntimeException("Exception:You can add only one contentView in " + getClass().getName());
        View view = getChildAt(0);
        if (view != null) {
            contentView = view;
        }
    }

    public LoadingLayout setLoadingView(IAnimationView animationView, RelativeLayout.LayoutParams layoutParams) {
        this.loadingView = animationView;
        loadingView.getView().setLayoutParams(layoutParams);
        return this;
    }

    public LoadingLayout setContentView(View view) {
        this.contentView = view;
        return this;
    }

    public LoadingLayout startLoadAnimation() {
        if(isRunning())return this;
        removeView(contentView);
        addView_(loadingView.getView());
        loadingView.startLoadAnimation();
        return this;
    }

    public LoadingLayout stopLoadAnimation() {
        loadingView.stopLoadAnimation();
        removeView(loadingView.getView());
        addView_(contentView);
        return this;
    }

    private LoadingLayout addView_(View view) {
        ViewGroup viewParent = (ViewGroup) view.getParent();
        if (viewParent != null) viewParent.removeView(view);
        addView(view);
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
