package com.cy.refreshlayoutniubility;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


public class RefreshLayoutNiubility extends LinearLayout {
    private int downX; // 按下时 X轴坐标值
    private int downY; // 按下时 Y 轴坐标值
    private Context context;
    private VelocityTracker velocityTracker;
    private int velocity_y;
    private OnRefreshListener onRefreshListener;
    private IHeadView headView;
    private View contentView;
    //    private IFootView footView;
    private boolean enableRefresh = true;
    //    private boolean enableLoadMore = true;
//    private boolean deal_head_released = true;//防止refresh 和loadMore在一次move事件中同时执行，比如手指下滑不松开，继续上滑
//    private boolean deal_foot_released = true;//防止refresh 和loadMore在一次move事件中同时执行，比如手指下滑不松开，继续上滑
    private RefreshCallback refreshCallback = new RefreshCallback() {

        @Override
        public void onRefreshStart() {
            if (onRefreshListener != null) onRefreshListener.onRefreshStart(headView);
        }

        @Override
        public void onRefreshFinish() {
            if (onRefreshListener != null) onRefreshListener.onRefreshFinish(headView);
        }


        @Override
        public void onRefreshCancel() {
            if (onRefreshListener != null) onRefreshListener.onRefreshCancel(headView);

        }

        @Override
        public <T> void bindDataToRefreshFinishedLayout(View view, T msg) {
            if (onRefreshListener != null)
                onRefreshListener.bindDataToRefreshFinishedLayout(view, msg);
        }
    };
//    private LoadMoreCallback loadMoreCallback = new LoadMoreCallback() {
//
//        @Override
//        public void onLoadMoreStart() {
//            onPullListener.onLoadMoreStart();
//        }
//
//        @Override
//        public void onLoadMoreFinish() {
//            onPullListener.onLoadMoreFinish();
//        }
//
//        @Override
//        public void onLoadMoreCancel() {
//            onPullListener.onLoadMoreCancel();
//        }
//    };

    public RefreshLayoutNiubility(Context context) {
        this(context, null);
//        LogUtils.log("RefreshLayout");
    }

    public RefreshLayoutNiubility(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        setOrientation(VERTICAL);
        headView = new HeadViewSimple(context);
        contentView = new View(context);
//        footView = new FootViewSimple(context);
//        onPullListener = new OnPullListener() {
//            @Override
//            public void onRefreshStart() {
//
//            }
//
//            @Override
//            public void onLoadMoreStart() {
//
//            }
//        };
        addHead();
        setContentView(contentView);
//        addFoot();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 3)
            throw new RuntimeException("Exception:You can add only one contentView in " + getClass().getName());
        View view = getChildAt(2);
        if (view != null) {
            contentView = view;
            setContentView(contentView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        headView.getView().measure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(headView.getView().getLayoutParams().height, MeasureSpec.EXACTLY));
        contentView.measure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
//        footView.getView().measure(widthMeasureSpec,
//                MeasureSpec.makeMeasureSpec(footView.getView().getLayoutParams().height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int width_parent = getMeasuredWidth();
        int height_parent = getMeasuredHeight();
        int height_head = headView.getView().getLayoutParams().height;
//        int height_foot = footView.getView().getLayoutParams().height;
        headView.getView().layout(0, 0, width_parent, height_head);
        if (contentView != null)
            contentView.layout(0, height_head, width_parent, height_parent + height_head);
//        footView.getView().layout(0, height_parent - height_foot, width_parent, height_parent);
    }


    private <T extends RefreshLayoutNiubility> T addHead() {
        addView(headView.getView(), 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        return (T) this;
    }


    public <T extends RefreshLayoutNiubility> T setContentView(View view) {
        removeView(contentView);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight = 1;
        this.contentView = view;
        addView(contentView, 1, layoutParams);
        return (T) this;
    }

//    private void addFoot() {
//        addView(footView.getView(), getChildCount(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
//    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        headView.setRefreshFinishedLayoutID(onRefreshListener.getRefreshFinishedLayoutID());
        headView.setCallback(refreshCallback);
//        footView.addCallback(loadMoreCallback);
    }

    public <T extends RefreshLayoutNiubility> T setHeadView(IHeadView headView) {
        removeView(this.headView.getView());
        this.headView = headView;
        addHead();
        return (T) this;

    }

//    public <T extends RefreshLayoutNiubility> T setFootView(IFootView footView) {
//        removeView(this.footView.getView());
//        this.footView = footView;
//        addFoot();
//        return (T) this;
//
//    }


    public IHeadView getHeadView() {
        return headView;
    }

//    public IFootView getFootView() {
//        return footView;
//    }


    public void startRefresh() {
        if (enableRefresh) headView.refreshStart();
    }

    public void finishRefresh() {
        headView.refreshFinish();
    }
    public <T> void finishRefresh(T msg) {
        headView.refreshFinish(msg);
    }

    public <T> void finishRefreshDelay(final T msg, int ms) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                headView.refreshFinish(msg);
            }
        }, ms);
    }

    public void closeRefresh() {
        headView.closeRefresh();
    }

    public <T> void closeRefreshDelay(T msg, int ms) {
        headView.refreshFinish(msg);
        //因为refreshFinish是耗时的，所以，时间+1S
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                headView.closeRefresh();
            }
        }, ms+1000);
    }

    public <T> void closeRefreshDelay(T msg) {
        closeRefreshDelay(msg, 1000);
    }

    public void openRefresh() {
        headView.openRefresh();
    }


    /**
     * --------------------------------------------------------------------------------------
     */
//    public void startLoadMore() {
//        if (enableLoadMore) footView.loadMoreStart();
//    }
//
//    public void finishLoadMore() {
//        footView.loadMoreFinish();
//    }

//    public void finishLoadMore(LoadMoreFinishListener loadMoreFinishListener) {
//        footView.loadMoreFinish(loadMoreFinishListener);
//    }

//    public void openLoadMore() {
//        footView.open();
//    }
//
//    public void closeLoadMore() {
//        footView.close();
//    }

    /**
     * --------------------------------------------------------------------------------------
     */
    public <T extends RefreshLayoutNiubility> T setEnableRefresh(boolean refresh) {
        this.enableRefresh = refresh;
        return (T) this;

    }

//    public <T extends RefreshLayoutNiubility> T setEnableLoadMore(boolean loadMore) {
//        this.enableLoadMore = loadMore;
//        return (T) this;
//    }

    public boolean enableRefresh() {
        return enableRefresh;
    }

//    public boolean enableLoadMore() {
//        return enableLoadMore;
//    }

    public <T extends RefreshLayoutNiubility> T setRefreshColor(int color) {
        headView.getAnimationView().setColor(color);
        return (T) this;
    }

//    public <T extends RefreshLayoutNiubility> T setLoadMoreColor(int color) {
//        footView.getAnimationView().setColor(color);
//        return (T) this;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                int distanceY = moveY - downY;
                if (Math.abs(moveX - downX) >= Math.abs(moveY - downY)) {
                    return false;
                } else {
                    requestDisallowInterceptTouchEvent();
                }
                downX = moveX;
                downY = moveY;

                if (headView.getView().getHeight() != 0) {
                    requestDisallowInterceptTouchEvent();
                    return true;
                }
                //下滑,canScrollVertically(-1)表示可以下滑，canScrollVertically(1)表示可以上滑
                if (enableRefresh && distanceY > 0 && !contentView.canScrollVertically(-1)) {
                    requestDisallowInterceptTouchEvent();
                    return true;
                }
//                //上滑
//                if (enableLoadMore && distanceY < 0 && !contentView.canScrollVertically(1)) {
//                    requestDisallowInterceptTouchEvent();
//                    return true;
//                }
                break;
        }
        return false;
    }


    /**
     *
     *
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (velocityTracker == null) velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                int distanceY = moveY - downY;
                downY = moveY;
                //下滑
                if (distanceY > 0) {
//                    //说明上次滑动的是footView
//                    if (footView.getView().getHeight() != 0) {
//                        deal_foot_released = false;
//                        footView.onDragingDown(distanceY);
//                        break;
//                    }
                    if (enableRefresh)
                        headView.onDragingDown(distanceY);
                    break;
                }
                //上滑
                if (distanceY < 0) {
                    //说明上次滑动的是headView
                    if (headView.getView().getHeight() != 0) {
//                        deal_head_released = false;
                        headView.onDragingUp(distanceY);
                        break;
                    }
//                    if (enableLoadMore && deal_head_released) {
//                        footView.onDragingUp(distanceY);
//                    }
                    break;
                }
                break;
            case MotionEvent.ACTION_UP:
//                deal_head_released = true;
//                deal_foot_released = true;
                velocityTracker.computeCurrentVelocity(1000);
                velocity_y = (int) velocityTracker.getYVelocity();
                if (headView.getView().getHeight() != 0) headView.onDragRelease(velocity_y);
//                if (footView.getView().getHeight() != 0) footView.onDragRelease(velocity_y);
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
        }
        return true;
    }

    private void requestDisallowInterceptTouchEvent() {
        final ViewParent parent = getParent();
        if (parent != null) parent.requestDisallowInterceptTouchEvent(true);
    }
}
