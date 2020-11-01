package com.cy.refreshlayoutniubility;

import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.cy.refresh.R;

/**
 * Created by lcodecore on 2016/10/1.
 */

public interface IHeadView {

    public <T extends FrameLayout> T getView();

    public int getHeightRefresh();

    public void setHeightRefresh(int heightRefresh);

    public int getHeightMax();

    public void setHeightMax(int heightMax);

    public void setAnimationView(IAnimationView animationView);

    public IAnimationView getAnimationView();

    public void setCallback(RefreshCallback callback);

    /**
     * 下拉中
     */
    public void onDragingDown(int distance);

    /**
     * 上拉中
     *
     * @param distance
     */
    public void onDragingUp(int distance);

    /**
     * 拖动松开
     */
    public void onDragRelease(int velocity_y);

    /**
     * 正在刷新时向上滑动屏幕，刷新被取消
     */
    public void refreshCancel();

    public boolean isRefreshing();

    public void refreshStart();

    public <T> void refreshFinish(T msg);

    public void refreshFinish();

    public void openRefresh();

    public void closeRefresh();

    public void setRefreshFinishedLayoutID(@LayoutRes int finishedLayoutId);
}
