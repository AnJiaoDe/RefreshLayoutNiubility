package com.cy.refreshlayoutniubility;

import android.content.Context;
import android.util.AttributeSet;
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
    private ILoadingView loadingView;
    private View contentView;
    private OnLoadingCallback onLoadingCallback;
    public LoadingLayout(@NonNull Context context) {
        this(context,null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadingView=new RotateLineCircleView(context);
        addView(loadingView.getView(),new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
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

    public void setOnLoadingCallback(OnLoadingCallback onLoadingCallback) {
        this.onLoadingCallback = onLoadingCallback;
    }
    public void startLoad(){
        loadingView.startLoadAnimation();
    }
    public void stopLoad(){
        loadingView.stopLoadAnimation();
    }
}
