package com.example.jimison.demothread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2;
    Switch switch01, switch02, switch03;
    SeekBar bar01, bar02, bar03;
    Handler handler01, handler02, handler03;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        createHandler();
        controller();
    }

    private void findViews() {
        btn1 = findViewById(R.id.btnThread1);
        btn2 = findViewById(R.id.btnThread2);
        switch01 = findViewById(R.id.switch01);
        switch02 = findViewById(R.id.switch02);
        switch03 = findViewById(R.id.switch03);
        bar01 = findViewById(R.id.bar01);
        bar02 = findViewById(R.id.bar02);
        bar03 = findViewById(R.id.bar03);
    }

    private void createHandler() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case R.id.switch01:
                        bar01.setProgress(getNextProgress(bar01));
                        handler.sendEmptyMessageDelayed(msg.what, 100);
                        break;
                    case R.id.switch02:
                        bar02.setProgress(getNextProgress(bar02));
                        handler.sendEmptyMessageDelayed(msg.what, 200);
                        break;
                    case R.id.switch03:
                        bar03.setProgress(getNextProgress(bar03));
                        handler.sendEmptyMessageDelayed(msg.what, 300);
                        break;
                }
            }
        };

        //分散式的handler

        handler01 = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                bar01.setProgress(getNextProgress(bar01));
                handler01.sendEmptyMessageDelayed(0,100);
            }
        };

        handler02 = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                bar02.setProgress(getNextProgress(bar02));
                handler02.sendEmptyMessageDelayed(0,200);
            }
        };

        handler03 = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                bar03.setProgress(getNextProgress(bar03));
                handler03.sendEmptyMessageDelayed(0,300);
            }
        };

    }

    private int getNextProgress(SeekBar seekBar) {
        int now = seekBar.getProgress();
        int max = seekBar.getMax();
        if (++now >= max) {
            now = 0;
        }
        return now;
    }

    private void controller() {
        //switch的父類別為CompoundButton，這邊以CompoundButton的監聽器偵測點到畫面上哪一個switch
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                if(isChecked){
                    handler.sendEmptyMessage(id);
                }else {
                    handler.removeMessages(id);
                }

                //分散式

                Handler target = null;
                if(id==R.id.switch01){
                    target = handler01;
                }else if(id==R.id.switch02){
                    target = handler02;
                }else if(id==R.id.switch03){
                    target = handler03;
                }

                if(isChecked){
                    target.sendEmptyMessage(0);
                }else {
                    target.removeMessages(0);
                }

            }
        };

        switch01.setOnCheckedChangeListener(listener);
        switch02.setOnCheckedChangeListener(listener);
        switch03.setOnCheckedChangeListener(listener);
    }

    public void btnClick1(View view) {
        int id = view.getId();
        if (id == R.id.btnThread1) {
            new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception e) {
                    }
                }
            }.start();
            btn1.setText("Done");
        }
        //在舊的版本這邊會報錯，因為背景執行緒無法控制畫面的顯示，需用runOnUiThread，但新版本是乎把這個限制拿掉了
        if (id == R.id.btnThread2) {
            new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception e) {
                    }
                    btn2.setText("Done1");
                }
            }.start();
        }
    }
}
