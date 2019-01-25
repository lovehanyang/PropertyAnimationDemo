package com.chuchujie.propertyanimationdemo;

import android.Manifest;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import com.chuchujie.propertyanimationdemo.change_shape.ShapeChangeButton;
import com.chuchujie.propertyanimationdemo.progressbar.KeyframeView;
import com.chuchujie.propertyanimationdemo.wechat_take_video.CirclePercentView;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    private CirclePercentView circlePercentView;
    private TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ShapeChangeButton shapeChangeButton = findViewById(R.id.change_shape_and_color_btn);
        KeyframeView keyframeView = (KeyframeView) findViewById(R.id.key_frame_view);
        circlePercentView = findViewById(R.id.circle_percent_view);
        textureView = findViewById(R.id.textureview);

        //改变形状button
        shapeChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shapeChangeButton.setStatus(count++ % 2);
            }
        });

        //keyframe进度条测试
        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100);
        Keyframe keyframe3 = Keyframe.ofFloat(1, 80);

        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(keyframeView, holder);
        animator.setDuration(2000);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.start();

        //微信拍摄进度条
        circlePercentView.setCountdownTime(5000);
        circlePercentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1000);
                } else {
                    takePhoto();
                }
            }
        });

    }

    private void takePhoto() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                takePhoto();
            } else {
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
                Toast.makeText(this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_SHORT);
            }
        }
    }

}
