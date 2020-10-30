package com.cy.refreshlayoutniubility;


import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.cy.refresh.R;


public abstract class OnSimplePullListener extends OnPullListener<String> {
    @Override
    public void bindDataToRefreshFinishedLayout(View view, String msg) {
        super.bindDataToRefreshFinishedLayout(view, msg);
        TextView textView = view.findViewById(R.id.tv);
        if (textView != null)
            textView.setText(msg);
    }
}
