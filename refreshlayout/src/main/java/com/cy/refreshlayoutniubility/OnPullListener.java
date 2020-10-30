package com.cy.refreshlayoutniubility;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cy.refresh.R;

public abstract class OnPullListener<T> {
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

    public  void bindDataToRefreshFinishedLayout(View view,T msg) {
    }
    public int getRefreshFinishedLayoutID() {
        return R.layout.cy_refresh_finished_default;
    }
//
////    public TextView setTextViewToast() {
////        return null;
////    }
//
//    public void setRefreshFinishedText(String text) {
//
//    }
//
//    /**
//     * 必须手动调用closeLoadMore()结束loadMore
//     */
//    public void closeLoadMore(OnCloseLoadMoreCallback onCloseLoadMoreCallback) {
//        this.onCloseLoadMoreCallback = onCloseLoadMoreCallback;
//        if (getLoadMoreAdapter().getItemCount() != 0) getLoadMoreAdapter().set(0, CLEAR);
//    }
//
//    /**
//     *
//     */
//
//    public void closeLoadMoreNoData() {
//        String toast = getLoadMoreNoDataToast();
//        toast = (toast == null || TextUtils.isEmpty(toast)) ? "没有更多了哦~" : toast;
//        if (getLoadMoreAdapter().getItemCount() != 0) getLoadMoreAdapter().set(0, toast);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                closeLoadMore(null);
//            }
//        }, 1000);
//    }
//
//
//
//    public String getLoadMoreNoDataToast() {
//        return "";
//    }
}
