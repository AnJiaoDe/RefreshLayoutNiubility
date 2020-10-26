package com.cy.refreshlayoutniubility;

/**
 * Created by lcodecore on 2016/10/1.
 */

interface RefreshCallback {

    /**
     * 开始刷新
     */
    void onRefreshStart();


    void onRefreshFinish();

    /**
     * 正在刷新时向上滑动屏幕，刷新被取消
     */
    void onRefreshCancel();

//    void onStateChanged(int state);
    /**
     * 下拉中
     */
    public void onRefreshDragingDown(int distance);

    /**
     * 上拉中
     *
     * @param distance
     */
    public void onRefreshDragingUp(int distance);

    /**
     * 拖动松开
     */
    public void onRefreshDragRelease(int velocity_y);

}
