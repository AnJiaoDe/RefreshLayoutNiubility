package com.cy.refreshlayoutniubility;

public abstract class OnPullListener {
    public abstract void onRefreshStart();

    public void onRefreshFinish() {
    }

    /**
     * 正在刷新时向上滑动屏幕，刷新被取消
     */
    public void onRefreshCancel() {
    }

    public abstract void onLoadMoreStart();


    public void onLoadMoreFinish() {
    }

    public void onLoadMoreCancel() {
    }

    /**
     * 下拉中
     */
    public void onRefreshDragingDown(int distance){}

    /**
     * 上拉中
     *
     * @param distance
     */
    public void onRefreshDragingUp(int distance){}

    /**
     * 拖动松开
     */
    public void onRefreshDragRelease(int velocity_y){}
    /**
     * 下拉中
     */
    public void onLoadMoreDragingDown(int distance){}

    /**
     * 上拉中
     *
     * @param distance
     */
    public void onLoadMoreDragingUp(int distance){}

    /**
     * 拖动松开
     */
    public void onLoadMoreDragRelease(int velocity_y){}
}
