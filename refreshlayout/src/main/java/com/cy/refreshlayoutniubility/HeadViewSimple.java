package com.cy.refreshlayoutniubility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.cy.refresh.R;

public class HeadViewSimple extends FrameLayout implements IHeadView {
    protected Context context;
    protected IAnimationView animationView;
    protected RefreshCallback callback;
    protected int heightMax;
    protected int heightRefresh;
    protected int velocity_y_limit = 1000;
    protected float y_dragUp_ratio = 1f / 2;
    protected float y_dragDown_ratio = 1f / 2;
    //    private float height_refresh_ratio ;
    protected int duration_refresh_start = 100;

    protected int duration_refresh_finish = 300;

    protected boolean isRefreshing = false;
    protected boolean vibrated = false;
    protected int finishedLayoutId = R.layout.cy_refresh_finished_default;
    protected View view_finished_layout;
    public HeadViewSimple(Context context) {
        super(context);
        this.context = context;
        LayoutParams layoutParams_child = new LayoutParams(ScreenUtils.dpAdapt(context, 24), ScreenUtils.dpAdapt(context, 24));
        layoutParams_child.gravity = Gravity.CENTER;
        animationView = new RotateLineCircleView(context);
        addView(animationView.getView(), layoutParams_child);
        heightMax = ScreenUtils.dpAdapt(context, 800);
        heightRefresh = ScreenUtils.dpAdapt(context, 80);
        velocity_y_limit = (int) (ViewConfiguration.get(context).getScaledMaximumFlingVelocity() * 0.7f);
    }


    public void setVelocity_y_limit(int velocity_y_limit) {
        this.velocity_y_limit = velocity_y_limit;
    }

    public void setY_dragUp_ratio(float y_dragUp_ratio) {
        this.y_dragUp_ratio = y_dragUp_ratio;
    }

    public void setY_dragDown_ratio(float y_dragDown_ratio) {
        this.y_dragDown_ratio = y_dragDown_ratio;
    }


    public void setDuration_refresh_start(int duration_refresh_start) {
        this.duration_refresh_start = duration_refresh_start;
    }

    public void setDuration_refresh_finish(int duration_refresh_finish) {
        this.duration_refresh_finish = duration_refresh_finish;
    }

    /**
     * ---------------------------------------------------------------
     */

    @Override
    public void setAnimationView(IAnimationView animationView) {
        removeView(this.animationView.getView());
        this.animationView = animationView;
        addView(animationView.getView());
    }


    @Override
    public IAnimationView getAnimationView() {
        return animationView;
    }

    @Override
    public void setHeightMax(int heightMax) {
        this.heightMax = heightMax;
    }

    @Override
    public int getHeightRefresh() {
        return heightRefresh;
    }

    @Override
    public int getHeightMax() {
        return heightMax;
    }

    @Override
    public void setHeightRefresh(int heightRefresh) {
        this.heightRefresh = heightRefresh;
    }

    @Override
    public boolean isRefreshing() {
        return isRefreshing;
    }

    @Override
    public void refreshCancel() {
        if (callback != null) callback.onRefreshCancel();
        closeRefresh();
    }


    @Override
    public void addCallback(RefreshCallback callback) {
        this.callback = callback;
    }

    @Override
    public HeadViewSimple getView() {
        return this;
    }

    @Override
    public void onDragingDown(int distance) {
        int value = Math.abs((int) (distance * y_dragDown_ratio));
        int height = getHeight() + value;
        height = height > heightMax ? heightMax : height;
        if (!vibrated && height >= heightRefresh) {
            VibratorUtils.startVibrate(context);
            vibrated = true;
        }
        getLayoutParams().height = height;
        requestLayout();
        animationView.onDraging(height, heightRefresh, heightMax);
    }

    @Override
    public void onDragingUp(int distance) {
        int value = Math.abs((int) (distance * y_dragUp_ratio));
        int height = getHeight() - value;
        height = height < 0 ? 0 : height;
        getLayoutParams().height = height;
        requestLayout();
        animationView.onDraging(height, heightRefresh, heightMax);
    }

    @Override
    public void onDragRelease(int velocity_y) {
        vibrated = false;
        if (velocity_y > velocity_y_limit || getHeight() >= heightRefresh) {
            if (!isRefreshing) {
                refreshStart();
            } else {
                openRefresh();
            }
            return;
        }
        if (-velocity_y > velocity_y_limit || getHeight() < heightRefresh) {
            if (isRefreshing) {
                refreshCancel();
                return;
            }
            closeRefresh();
        }
    }

    protected void open(AnimatorListenerAdapter animatorListenerAdapter) {
        final int height_head = getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, height_head - heightRefresh);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                int height = height_head - value;
                getLayoutParams().height = height;
                requestLayout();
                animationView.onDraging(height, heightRefresh, heightMax);
            }
        });
        valueAnimator.setDuration(duration_refresh_start);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setEvaluator(new IntEvaluator());
        if (animatorListenerAdapter != null) valueAnimator.addListener(animatorListenerAdapter);
        valueAnimator.start();
    }

    protected void close(AnimatorListenerAdapter animatorListenerAdapter) {
        animationView.stopLoadAnimation();
        final int height_head = getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, height_head);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                int height = height_head - value;
                getLayoutParams().height = height;
                requestLayout();
                animationView.onDraging(height, heightRefresh, heightMax);
            }
        });
        valueAnimator.setDuration(duration_refresh_finish);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setEvaluator(new IntEvaluator());
        if (animatorListenerAdapter != null) valueAnimator.addListener(animatorListenerAdapter);
        valueAnimator.start();
    }

    @Override
    public void openRefresh() {
        open(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (callback != null) callback.onRefreshStart();
                animationView.startLoadAnimation();
                isRefreshing = true;
            }
        });
    }

    @Override
    public void closeRefresh() {
        close(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRefreshing = false;
                removeView(view_finished_layout);
                animationView.onDragClosed();
            }
        });
    }


    @Override
    public void refreshStart() {
        openRefresh();
    }

//    @Override
//    public void refreshFinish() {
////        LogUtils.log("refreshFinish");
//        animationView.closeLoadAnimation(new AnimationViewCallback() {
//            @Override
//            public void onLoadOpened() {
//
//            }
//
//            @Override
//            public void onLoadClosed() {
////                LogUtils.log("onLoadClosed");
//                close(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        isRefreshing = false;
//                        animationView.onDragClosed();
//                        if (callback != null) callback.onRefreshFinish();
//                    }
//                });
//
//            }
//        });

//    }

    @Override
    public void setRefreshFinishedLayoutID(int finishedLayoutId) {
        this.finishedLayoutId = finishedLayoutId;
    }

    @Override
    public <T> void refreshFinish(final T msg) {
        animationView.closeLoadAnimation(new AnimationViewCallback() {
            @Override
            public void onLoadOpened() {

            }

            @Override
            public void onLoadClosed() {
                view_finished_layout=LayoutInflater.from(getContext()).inflate(finishedLayoutId,HeadViewSimple.this,false);
                addView(view_finished_layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                if (callback != null) callback.onRefreshFinish();
                if (callback != null) callback.bindDataToRefreshFinishedLayout(view_finished_layout,msg);
            }
        });

    }
}
