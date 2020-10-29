package com.cy.refreshlayoutniubility;

import com.cy.refresh.R;

/**
 * Created by lcodecore on 2016/10/1.
 */

public abstract class RefreshFinishSimpleListener implements RefreshFinishListener {
    public int getRefreshFinishLayoutID() {
        return R.layout.cy_refresh_finished_default;
    }
}
