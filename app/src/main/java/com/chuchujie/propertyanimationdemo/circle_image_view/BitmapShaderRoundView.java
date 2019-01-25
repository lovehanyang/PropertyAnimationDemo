package com.chuchujie.propertyanimationdemo.circle_image_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.chuchujie.propertyanimationdemo.R;

public class BitmapShaderRoundView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public BitmapShaderRoundView(Context context) {
        super(context);
    }

    public BitmapShaderRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BitmapShaderRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //用 Bitmap 来着色（终于不是渐变了）。其实也就是用 Bitmap 的像素来作为图形或文字的填充
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawCircle(150, 150, 150, paint);
    }
}
