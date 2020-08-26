package com.cy.refreshlayoutniubility;

import android.view.View;
import android.widget.FrameLayout;

/**
 * @Description:
 * @Author: cy
 * @CreateDate: 2020/8/26 18:51
 * @UpdateUser:
 * @UpdateDate: 2020/8/26 18:51
 * @UpdateRemark:
 * @Version:
 */
public interface ILoadingView {
    public <T extends View> T getView();
    /**
     * 开始load动画
     */
    public void startLoadAnimation();

    /**
     * 停止load动画
     */
    public void stopLoadAnimation();
}
