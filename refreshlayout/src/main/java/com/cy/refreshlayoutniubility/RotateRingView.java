package com.cy.refreshlayoutniubility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: cy
 * @CreateDate: 2020/6/17 17:25
 * @UpdateUser:
 * @UpdateDate: 2020/6/17 17:25
 * @UpdateRemark:
 * @Version:
 */
public class RotateRingView extends View implements IAnimationView {
    private Paint paint_bg;
    private Paint paint_rotate;
    private Context context;
    private int startAngle_rotate = 270;
    private int sweepAngle_rotate = 0;
    private ValueAnimator valueAnimator_load;
    private ValueAnimator valueAnimator_close;
    private int color_bg = 0xffeeeeee;
    private int color_rotate = 0xff0081ff;

    public RotateRingView(Context context) {
        this(context, null);
    }

    public RotateRingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint_bg = new Paint();
        paint_rotate = new Paint();

        paint_bg.setColor(color_bg);
        paint_bg.setStyle(Paint.Style.STROKE);
        paint_bg.setAntiAlias(true);
        paint_bg.setStrokeWidth(dpAdapt(3));
        paint_bg.setStrokeCap(Paint.Cap.ROUND);

        paint_rotate.setColor(color_rotate);
        paint_rotate.setStyle(Paint.Style.STROKE);
        paint_rotate.setAntiAlias(true);
        paint_rotate.setStrokeWidth(dpAdapt(3));
        paint_rotate.setStrokeCap(Paint.Cap.ROUND);


        valueAnimator_load = ValueAnimator.ofInt(110, 90, 70, 50, 30, 50, 70, 110);
        valueAnimator_load.setDuration(6000);
        valueAnimator_load.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_load.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator_load.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                sweepAngle_rotate = value;
                invalidate();
                startAngle_rotate = (int) ((startAngle_rotate + 8) % 360);
            }
        });
        /**
         * -----------------------------------------------------
         */
        valueAnimator_close = ValueAnimator.ofFloat(0, 1);
        valueAnimator_close.setDuration(3000);
        valueAnimator_close.setInterpolator(new LinearInterpolator());
        valueAnimator_close.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                sweepAngle_rotate += value * 360;
                invalidate();
                if (sweepAngle_rotate >= 360) valueAnimator_close.cancel();
            }
        });
    }

    public Paint getPaint_bg() {
        return paint_bg;
    }

    public Paint getPaint_rotate() {
        return paint_rotate;
    }


    /**
     * 恢复初始状态值
     */
    private void restoreParams() {
        startAngle_rotate = 270;
        sweepAngle_rotate = 0;
    }

    @Override
    public <T extends IAnimationView> T setColor(int color) {
        this.color_rotate = color;
        paint_rotate.setColor(color);
        return (T) this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(paint_bg.getStrokeWidth(), paint_bg.getStrokeWidth(), getWidth() - paint_bg.getStrokeWidth(),
                    getHeight() - paint_bg.getStrokeWidth(), 0, 360, false, paint_bg);
            canvas.drawArc(paint_rotate.getStrokeWidth(), paint_rotate.getStrokeWidth(), getWidth() - paint_rotate.getStrokeWidth(),
                    getHeight() - paint_rotate.getStrokeWidth(), startAngle_rotate, sweepAngle_rotate, false, paint_rotate);
        }
    }

    /**
     * --------------------------------------------------------------------------
     */
    public int getStartAngle_rotate() {
        return startAngle_rotate;
    }

    public RotateRingView setStartAngle_rotate(int startAngle_rotate) {
        this.startAngle_rotate = startAngle_rotate;
        return this;
    }

    public int getSweepAngle_rotate() {
        return sweepAngle_rotate;
    }

    public RotateRingView setSweepAngle_rotate(int sweepAngle_rotate) {
        this.sweepAngle_rotate = sweepAngle_rotate;
        return this;

    }

    private void close(AnimatorListenerAdapter animatorListenerAdapter) {
        //防止多次回调
        valueAnimator_close.removeAllListeners();
        valueAnimator_close.addListener(animatorListenerAdapter);
        valueAnimator_close.start();
    }

    //    @Override
//    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
//        super.onVisibilityChanged(changedView, visibility);
//        if(visibility==GONE||visibility==INVISIBLE)cancelAllAnimation();
//    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAllAnimation();
    }

    /**
     * --------------------------------------------------------------------------
     */
    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean isRunning() {
        if (valueAnimator_load.isRunning() || valueAnimator_close.isRunning())
            return true;
        return false;
    }

    @Override
    public <T extends IAnimationView> T closeLoadAnimation(final AnimationViewCallback animationViewCallback) {
        cancelAllAnimation();
        close(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animationViewCallback.onLoadClosed();
                    }
                }, 500);
            }
        });
        return (T) this;
    }

    @Override
    public <T extends IAnimationView> T startLoadAnimation() {
        cancelAllAnimation();
        startAngle_rotate = 270;
        sweepAngle_rotate = 0;
        valueAnimator_load.start();
        return (T) this;
    }

    @Override
    public <T extends IAnimationView> T stopLoadAnimation() {
        cancelAllAnimation();
        return (T) this;
    }


    @Override
    public <T extends IAnimationView> T cancelAllAnimation() {
        valueAnimator_load.cancel();
        valueAnimator_close.cancel();
        return (T) this;
    }

    @Override
    public void onDraging(int height_current, int height_load, int height_max) {
        sweepAngle_rotate = Math.min(360, (int) (360 * height_current * 1f / height_load));
        invalidate();
    }


    @Override
    public void onDragClosed() {
        restoreParams();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startLoadAnimation();
    }

    /**
     * --------------------------------------------------------------------------------
     */
    public int dpAdapt(float dp) {
        return dpAdapt(dp, 360);
    }

    public int dpAdapt(float dp, float widthDpBase) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int heightPixels = dm.heightPixels;//高的像素
        int widthPixels = dm.widthPixels;//宽的像素
        float density = dm.density;//density=dpi/160,密度比
        float heightDP = heightPixels / density;//高度的dp
        float widthDP = widthPixels / density;//宽度的dp
        float w = widthDP > heightDP ? heightDP : widthDP;
        return (int) (dp * w / widthDpBase * density + 0.5f);
    }
}
