package com.chuchujie.propertyanimationdemo.change_shape;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class ShapeChangeButton extends AppCompatButton {

    public static final int ROUND = 1;
    public static final int RECTANGLE = 0;

    /**
     * 动画时长
     */
    private int mDuration = 500;

    /**
     * 当前的Padding
     */
    private int mCurrentPadding;

    /**
     * 圆角的大小
     */
    private float mRadius;

    /**
     * 是否正在动画中
     */
    private boolean isAnim;

    /**
     * 普通状态的背景色
     */
    private int mNormalBgColor = Color.parseColor("#0000ff");

    /**
     * 要绘制的颜色
     */
    private int mPaintColor = mNormalBgColor;

    /**
     * 成功状态的颜色
     */
    private int mSuccessBgColor = Color.parseColor("#00ffff");

    /**
     * 设置最大距离
     */
    private int maxPadding;

    /**
     * 设置矩形参数
     */
    private RectF mRectF;

    /**
     * 最小大小
     */
    private int minSize;

    /**
     * 画笔
     */
    private Paint mPaint;


    public ShapeChangeButton(Context context) {
        super(context);
        initView();
    }

    public ShapeChangeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ShapeChangeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 把默认背景去掉
        setBackgroundResource(0);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        // 计算最大间距，除以2是因为要均分间距，才能居中绘制
        maxPadding = Math.abs(width - height) / 2;
        // 最小是宽或高
        minSize = width > height ? height : width;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        int width = getWidth();
        int height = getHeight();

        int paddingLeftRight = width - mCurrentPadding * 2 < minSize ? (width - minSize) / 2 : mCurrentPadding;
        int paddingTopBottom = height - mCurrentPadding * 2 < minSize ? (height - minSize) / 2 : mCurrentPadding;

        if (mRectF == null){
            mRectF = new RectF();
        }

        mRectF.set(paddingLeftRight, paddingTopBottom, getWidth() - paddingLeftRight, getHeight() - paddingTopBottom);
        //绘制背景
        mPaint.setColor(mPaintColor);
//        canvas.clipRect(mRectF);
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);

        //绘制文字
        super.onDraw(canvas);

    }

    /**
     * 形状变换动画
     */
    private ValueAnimator getShapeAnim(int fromPadding, int toPadding) {
        ValueAnimator shapeAnim = ValueAnimator.ofInt(fromPadding, toPadding);
        shapeAnim.setDuration(mDuration);
        shapeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPadding = (int) animation.getAnimatedValue();
            }
        });
        return shapeAnim;
    }

    /**
     * 圆角变换动画
     */
    private ValueAnimator getRadiusAnim(int fromRadius, int toRadius) {
        ValueAnimator radiusAnim = ValueAnimator.ofFloat(fromRadius, toRadius);
        radiusAnim.setDuration(mDuration);
        radiusAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRadius = (float) animation.getAnimatedValue();
            }
        });
        return radiusAnim;
    }

    /**
     * 颜色变换动画
     */
    private ValueAnimator getColorAnim(int fromColor, int toColor) {
        final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPaintColor = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });


        return valueAnimator;

    }

    public void setStatus(int status) {
        if (!isAnim) {
            switch (status) {
                case RECTANGLE:
                    startAnimSet(mNormalBgColor, mSuccessBgColor, 0, getHeight() / 2, 0, maxPadding);
                    break;
                case ROUND:
                    startAnimSet(mSuccessBgColor, mNormalBgColor, getHeight() / 2, 0, maxPadding, 0);
                    break;
                default:
                    break;
            }
        }

    }

    private void startAnimSet(int fromColor, int toColor, int fromRadius, int toRadius, int fromPadding, int toPadding) {
        isAnim = true;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(getColorAnim(fromColor, toColor), getRadiusAnim(fromRadius, toRadius), getShapeAnim(fromPadding, toPadding));
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnim = false;
            }
        });
        animatorSet.start();

    }
}
