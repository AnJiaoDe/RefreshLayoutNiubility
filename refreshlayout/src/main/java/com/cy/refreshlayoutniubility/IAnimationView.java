package com.cy.refreshlayoutniubility;

import android.view.View;

/**
 * Created by lcodecore on 2016/10/1.面向对象的精髓多态，接口的强扩展性
 */

public interface IAnimationView {

    public View getView();

    public <T extends IAnimationView> T setColor(int color);

//    public void openLoadAnimation(AnimationViewCallback animationViewCallback);

    /**
     * 开始load动画
     */
    public <T extends IAnimationView> T startLoadAnimation();

    /**
     * 停止load动画
     */
    public <T extends IAnimationView> T stopLoadAnimation();


    public <T extends IAnimationView> T closeLoadAnimation(AnimationViewCallback animationViewCallback);

    /**
     * 停止所有动画
     */
    public <T extends IAnimationView> T cancelAllAnimation();

    public boolean isRunning();

    public void onDraging(int height_current, int height_load, int height_max);

    public void onDragClosed();

}
