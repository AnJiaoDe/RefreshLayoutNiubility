package com.cy.refreshlayoutniubility;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final RefreshLayoutNiubility refreshLayout = findViewById(R.id.baseRefreshLayout);

        RotateRingView rotateRingView = new RotateRingView(this);
        FrameLayout.LayoutParams layoutParams_child = new FrameLayout.LayoutParams(ScreenUtils.dpAdapt(this, 22), ScreenUtils.dpAdapt(this, 22));
        layoutParams_child.gravity = Gravity.CENTER;
        rotateRingView.setLayoutParams(layoutParams_child);
        refreshLayout.getHeadView().setAnimationView(rotateRingView);
        refreshLayout.setOnRefreshListener(new OnRefreshListener<String>() {

//            @Override
//            public void onRefreshCancel(IHeadView headView) {
//                super.onRefreshCancel(headView);
//            }

            @Override
            public void bindDataToRefreshFinishedLayout(View view, String msg) {
                TextView textView = view.findViewById(R.id.tv);
                textView.setText(msg);
            }

            @Override
            public int getRefreshFinishedLayoutID() {
//                return super.getRefreshFinishedLayoutID();
                return R.layout.refresh_finished;
            }

//            @Override
//            public void onRefreshFinish(IHeadView headView) {
//                super.onRefreshFinish(headView);
//            }

            @Override
            public void onRefreshStart(IHeadView headView) {
                LogUtils.log("onRefreshStart");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.closeRefreshDelay("有8条更新");
                    }
                },2000);
            }

//            @Override
//            public void onLoadMoreStart() {
//                LogUtils.log("onLoadMoreStart");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.finishLoadMore(new LoadMoreFinishListener() {
//                            @Override
//                            public void onLoadMoreFinish(final FrameLayout footLayout) {
//                                final TextView textView = new TextView(footLayout.getContext());
//                                textView.setGravity(Gravity.CENTER);
//                                textView.setBackgroundColor(Color.WHITE);
//                                textView.setTextColor(Color.RED);
//                                textView.setText("有8条更新");
//                                footLayout.addView(textView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        footLayout.removeView(textView);
//                                        refreshLayout.closeLoadMore();
//                                    }
//                                }, 2000);
//                            }
//
//                        });
////                        refreshLayout.finishLoadMore();
//                    }
//                }, 3000);
//            }

        });

        final LoadingLayout loadingLayout = findViewById(R.id.loadinglayout);
        loadingLayout.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.startLoadAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingLayout.stopLoadAnimation().showContentView();
                    }
                }, 3000);
            }
        });
        loadingLayout.startLoadAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingLayout.stopLoadAnimation().showContentView();
            }
        }, 3000);

        final LoadingLayout loadingLayout2 = findViewById(R.id.loadinglayout2);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        loadingLayout2.setLoadingView(new ThreeScaleCircleView(this), layoutParams);
        loadingLayout2.startLoadAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingLayout2.stopLoadAnimation().showContentView();
            }
        }, 3000);
    }
}
