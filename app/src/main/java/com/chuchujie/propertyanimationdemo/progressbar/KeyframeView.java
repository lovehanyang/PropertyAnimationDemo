package com.chuchujie.propertyanimationdemo.progressbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;


public class KeyframeView extends View {

    final float radius = dpToPixel(80);
    float progress = 0;
    RectF arcRectF;
    Paint paint;

    public KeyframeView(Context context) {
        super(context);
        initView();
    }

    public KeyframeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public KeyframeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }

    private void initView() {
        arcRectF = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    }

    //set，get方法非常重要！！！keyframe会调用
    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;

        paint.setColor(Color.parseColor("#E91E63"));


        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(dpToPixel(40));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(dpToPixel(15));

        arcRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(arcRectF, 135, progress * 2.7f, false, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText((int) progress + "%", centerX, centerY - (paint.ascent() + paint.descent()) / 2, paint);
    }
}
