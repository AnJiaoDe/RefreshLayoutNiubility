package com.cy.refreshlayoutniubility;

/**
 * Created by lcodecore on 2016/10/1.
 */

public interface LoadMoreCallback {


    /**
     * 开始loadMore
     */
    public void onLoadMoreStart();


    public void onLoadMoreFinish();


    /**
     * 正在loadMore时向下滑动屏幕，loadMore被取消
     */
    public void onLoadMoreCancel();

//    void onStateChanged(int state);
    /**
     * 下拉中
     */
    public void onLoadMoreDragingDown(int distance);

    /**
     * 上拉中
     *
     * @param distance
     */
    public void onLoadMoreDragingUp(int distance);

    /**
     * 拖动松开
     */
    public void onLoadMoreDragRelease(int velocity_y);
}
