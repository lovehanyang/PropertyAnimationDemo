package com.chuchujie.propertyanimationdemo.scroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chuchujie.propertyanimationdemo.R;

/**
 * @author hanyang
 */
public class ScrollActivity extends Activity {

    private LinearLayout layout;

    private Button scrollToBtn;

    private Button scrollByBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        layout = (LinearLayout) findViewById(R.id.layout);
        scrollToBtn = (Button) findViewById(R.id.scroll_to_btn);
        scrollByBtn = (Button) findViewById(R.id.scroll_by_btn);
        scrollToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.scrollTo(-60, -100);
            }
        });
        scrollByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.scrollBy(-60, -100);
            }
        });
    }

}
