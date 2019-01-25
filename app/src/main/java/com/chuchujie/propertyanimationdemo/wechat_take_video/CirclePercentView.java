package com.chuchujie.propertyanimationdemo.wechat_take_video;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chuchujie.propertyanimationdemo.R;

public class CirclePercentView extends View {
    private int countdownTime;
    private CountDownTimer countDownTimer;
    private int curPercent;
    RectF mRectF;
    private int radius;
    private Paint bgPaint, centerPaint, arcPaint;
    private int curAngle;
    private int ringColor;

    public CirclePercentView(Context context) {
        super(context);
        initView();
    }

    public CirclePercentView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CiclePercentView);
        radius = array.getInt(R.styleable.CiclePercentView_radius,85);
        ringColor = array.getColor(R.styleable.CiclePercentView_ring_color,Color.GREEN);
        array.recycle();

        initView();
    }

    public CirclePercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {



        mRectF = new RectF();

        //圆弧paint
        arcPaint = new Paint();
        arcPaint.setColor(ringColor);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeWidth(14);

        //背景paint
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.GRAY);

        //中间圆paint
        centerPaint = new Paint();
        centerPaint.setAntiAlias(true);
        centerPaint.setColor(Color.YELLOW);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                countDown(countdownTime);
                break;
            case MotionEvent.ACTION_UP:
                countDownTimer.cancel();
                curPercent = 0;
                percentToAngle(curPercent);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, dp2px(radius) / 2, bgPaint);
        canvas.drawCircle(getWidth() / 2, getMeasuredHeight() / 2, dp2px(radius / 3) / 2, centerPaint);

        //画圆弧
        mRectF.set(6, 6, dp2px(radius) - 6, dp2px(radius) - 6);
        canvas.drawArc(mRectF, -90, curAngle, false, arcPaint);

    }

    private void percentToAngle(int curPercent) {
        curAngle = (int) (curPercent / 100f * 360);
        invalidate();
    }

    private void countDown(final int countdownTime) {
        countDownTimer = new CountDownTimer(countdownTime, (long) (countdownTime / 100f)) {
            @Override
            public void onTick(long millisUntilFinished) {
                curPercent = (int) ((countdownTime - millisUntilFinished) / (float) countdownTime * 100);
                percentToAngle(curPercent);
            }

            @Override
            public void onFinish() {
                curPercent = 0;
                percentToAngle(curPercent);
            }
        }.start();
    }

    private int dp2px(int dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp + 0.5);
    }

    public void setCountdownTime(int countdownTime) {
        this.countdownTime = countdownTime;
    }
}
