package com.qf.damobobo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GushiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gushi);
    }

    public void myClick_back(View view) {
        finish();
    }

    public void myClick(View view) {
        // TODO: 2016/12/5  
    }
}
