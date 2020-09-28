package com.cy.refreshlayoutniubility;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RefreshLayoutNiubility refreshLayout = findViewById(R.id.baseRefreshLayout);

        refreshLayout.setOnPullListener(new OnPullListener() {

            @Override
            public void onRefreshCancel() {
                super.onRefreshCancel();
                LogUtils.log("onRefreshCancel");

            }

            @Override
            public void onLoadMoreCancel() {
                super.onLoadMoreCancel();
                LogUtils.log("onLoadMoreCancel");
            }

            @Override
            public void onRefreshStart() {
                LogUtils.log("onRefreshStart");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh(new RefreshFinishListener() {
                            @Override
                            public void onRefreshFinish(final FrameLayout headLayout) {
                                final TextView textView = new TextView(headLayout.getContext());
                                textView.setGravity(Gravity.CENTER);
                                textView.setBackgroundColor(Color.WHITE);
                                textView.setText("有8条更新");
                                headLayout.addView(textView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        headLayout.removeView(textView);
                                        refreshLayout.closeRefresh();
                                    }
                                }, 2000);
                            }
                        });
//                        refreshLayout.finishRefresh();

                    }
                }, 3000);

            }


            @Override
            public void onRefreshFinish() {
                super.onRefreshFinish();
                LogUtils.log("onRefreshFinish");
            }

            @Override
            public void onLoadMoreStart() {
                LogUtils.log("onLoadMoreStart");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore(new LoadMoreFinishListener() {
                            @Override
                            public void onLoadMoreFinish(final FrameLayout footLayout) {
                                final TextView textView = new TextView(footLayout.getContext());
                                textView.setGravity(Gravity.CENTER);
                                textView.setBackgroundColor(Color.WHITE);
                                textView.setTextColor(Color.RED);
                                textView.setText("有8条更新");
                                footLayout.addView(textView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        footLayout.removeView(textView);
                                        refreshLayout.closeLoadMore();
                                    }
                                }, 2000);
                            }

                        });
//                        refreshLayout.finishLoadMore();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMoreFinish() {
                super.onLoadMoreFinish();
                LogUtils.log("onLoadMoreFinish");
            }
        });

        final LoadingLayout loadingLayout = findViewById(R.id.loadinglayout);
        loadingLayout.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLayout.startLoadAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingLayout.stopLoadAnimation();
                    }
                }, 3000);
            }
        });
        loadingLayout.startLoadAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingLayout.stopLoadAnimation();
            }
        }, 3000);

        final LoadingLayout loadingLayout2 = findViewById(R.id.loadinglayout2);
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity=Gravity.CENTER;
        loadingLayout2.setLoadingView(new ThreeScaleCircleView(this),layoutParams);
        loadingLayout2.startLoadAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingLayout2.stopLoadAnimation_removeLoadingView();
            }
        }, 3000);
    }
}
