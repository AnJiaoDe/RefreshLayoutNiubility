package com.cy.refreshlayoutniubility;

import android.view.View;

import com.cy.refresh.R;

public abstract class OnRefreshListener<T> {
    public abstract void onRefreshStart();

    public void onRefreshFinish() {
    }

    /**
     * 正在刷新时向上滑动屏幕，刷新被取消
     */
    public void onRefreshCancel() {
    }

    public void bindDataToRefreshFinishedLayout(View view, T msg) {
    }

    public int getRefreshFinishedLayoutID() {
        return R.layout.cy_refresh_finished_default;
    }
}
