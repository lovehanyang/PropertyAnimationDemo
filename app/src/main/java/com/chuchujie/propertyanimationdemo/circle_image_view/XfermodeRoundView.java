package com.chuchujie.propertyanimationdemo.circle_image_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.chuchujie.propertyanimationdemo.R;

public class XfermodeRoundView extends View {

    private Paint mPaint;
    private Bitmap mSrcBitmap;
    private Bitmap mDefaultBitmap;

    public XfermodeRoundView(Context context) {
        super(context);
        initView();
    }

    public XfermodeRoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public XfermodeRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.XfermodeRoundView, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int resId;
            int index = a.getIndex(i);
            switch (index) {
                case R.styleable.XfermodeRoundView_defaultImage:
                    resId = a.getResourceId(index, 0);
                    mDefaultBitmap = BitmapFactory.decodeResource(getResources(), resId);
                    break;
                case R.styleable.XfermodeRoundView_image:
                    resId = a.getResourceId(index, 0);
                    mSrcBitmap = BitmapFactory.decodeResource(getResources(), resId);
                    break;
                default:
                    break;
            }
        }
        a.recycle();
        if (null == mSrcBitmap) {
            mSrcBitmap = mDefaultBitmap;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //取宽、高中的较小值
        int min = width > height ? height : width;
        if (null != mSrcBitmap) {
            canvas.drawBitmap(createCircleBitmap(mSrcBitmap, min), 0, 0, mPaint);
        }

    }

    private Bitmap createCircleBitmap(Bitmap srcBitmap, int min) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        final int tWidth = getMeasuredWidth();
        final int tHeight = getMeasuredHeight();
        //创建一个新的bitmap，大小跟imageview一样
        Bitmap target = Bitmap.createBitmap(tWidth, tHeight, Bitmap.Config.ARGB_8888);
        //下面所有的绘图操作均在这个target上面完成
        Canvas canvas = new Canvas(target);

        //1.画圆形
        //计算圆的圆心在imageview中的坐标
        int halfWidth = tWidth >> 1;
        int halfHeight = tHeight >> 1;
        //画圆
        canvas.drawCircle(halfWidth, halfHeight, min >> 1, paint);
        //设置Xformode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //画图片
        Rect srcRect = new Rect(0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight());
        int offsetX = (tWidth - min) >> 1;
        int offsetY = (tHeight - min) >> 1;
        Rect dstRect = new Rect(offsetX, offsetY, offsetX + min, offsetY + min);
        canvas.drawBitmap(srcBitmap, srcRect, dstRect, paint);

        return target;


    }

    public void setImage(int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        setImage(bitmap);
    }

    public void setImage(Bitmap bitmap) {
        mSrcBitmap = bitmap;
        invalidate();
    }

}
