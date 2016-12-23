package com.qf.damobobo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qf.damobobo.utils.Shuju;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!MainActivity.this.isDestroyed()){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    MainActivity.this.finish();
                }
            }
        }).start();
    }
}
