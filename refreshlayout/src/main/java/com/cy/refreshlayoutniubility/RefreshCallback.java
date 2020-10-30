package com.cy.refreshlayoutniubility;

import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lcodecore on 2016/10/1.
 */

public interface RefreshCallback {

    /**
     * 开始刷新
     */
    void onRefreshStart();


    void onRefreshFinish();

    /**
     * 正在刷新时向上滑动屏幕，刷新被取消
     */
    void onRefreshCancel();

    public <T>void bindDataToRefreshFinishedLayout(View view,T msg);
}
