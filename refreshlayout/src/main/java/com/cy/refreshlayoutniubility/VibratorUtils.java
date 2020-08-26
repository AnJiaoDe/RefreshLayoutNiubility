package com.cy.refreshlayoutniubility;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * @Description:
 * @Author: cy
 * @CreateDate: 2020/8/20 16:52
 * @UpdateUser:
 * @UpdateDate: 2020/8/20 16:52
 * @UpdateRemark:
 * @Version:
 */
public class VibratorUtils {

    public static void startVibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if(vibrator!=null)vibrator.vibrate(50);
    }

    public static void stopVibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if(vibrator!=null)vibrator.cancel();
    }
}
