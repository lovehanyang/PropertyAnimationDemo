package com.chuchujie.propertyanimationdemo.circle_image_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.chuchujie.propertyanimationdemo.R;

public class ClipPathRoundView extends View {

    public ClipPathRoundView(Context context) {
        super(context);
    }

    public ClipPathRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipPathRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = createCircleBitmapForClip();
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
        }
    }

    private Bitmap createCircleBitmapForClip() {

        Bitmap mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);

        if (mSrcBitmap != null) {
            int width = mSrcBitmap.getWidth();
            int height = mSrcBitmap.getHeight();
            if (width > 0 && height > 0) {
                int size = Math.min(width, height);
                Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                int center = size / 2;
                Path path = new Path();
                path.addCircle(center, center, center, Path.Direction.CW);
                canvas.clipPath(path);
                Paint paint = new Paint();
                canvas.drawBitmap(mSrcBitmap, 0, 0, paint);
                return bitmap;
            }
        }
        return null;
    }

}


