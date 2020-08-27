package com.cy.refreshlayoutniubility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable2;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
    private RotateLineCircleView loadingView;
    private View contentView;
    private OnLoadingCallback onLoadingCallback;
    public LoadingLayout(@NonNull Context context) {
        this(context,null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadingView=new RotateLineCircleView(context);
        setBackgroundColor(Color.WHITE);
        LayoutParams layoutParams_child = new LayoutParams(ScreenUtils.dpAdapt(context, 30), ScreenUtils.dpAdapt(context, 30));
        layoutParams_child.gravity = Gravity.CENTER;
        addView(loadingView.getView(),layoutParams_child);
        loadingView.getView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoad();
            }
        });
    }

    @Override
    protected final void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 2)
            throw new RuntimeException("Exception:You can add only one contentView in " + getClass().getName());
        View view = getChildAt(1);
        if (view != null)
            contentView = view;
    }

    public LoadingLayout setContentView(View view) {
        removeView(contentView);
        this.contentView = view;
        addView(contentView, 1, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        return  this;
    }

    public LoadingLayout setOnLoadingCallback(OnLoadingCallback onLoadingCallback) {
        this.onLoadingCallback = onLoadingCallback;
        return this;
    }
    public LoadingLayout startLoad(){
        loadingView.startLoadAnimation();
        onLoadingCallback.onLoadStart();
        return this;
    }
    public LoadingLayout stopLoad(){
        loadingView.closeLoadAnimation(new AnimationViewCallback() {
            @Override
            public void onLoadOpened() {

            }

            @Override
            public void onLoadClosed() {
               onLoadingCallback.onLoadFinish();
            }
        });
        return this;
    }

    public RotateLineCircleView getLoadingView() {
        return loadingView;
    }
}
