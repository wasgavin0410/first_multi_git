package com.example.demonstration;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;


/* Flash the screen between red and green each second */
public class MainActivity extends Activity {
    final static int INTERVAL = 500; // 1000=1sec
    private View myView;
    public SoundPool sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);

    //設置顏色字串  供TransitionDrawable取得可以變更的顏色列表
    final ColorDrawable[] colors = {new ColorDrawable(Color.GRAY), new ColorDrawable(Color.YELLOW), new ColorDrawable(Color.RED), new ColorDrawable(Color.BLUE)};
    final TransitionDrawable changingColor = new TransitionDrawable(colors);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = findViewById(R.id.view);
        myView.setBackgroundColor(Color.GRAY);// set initial colour


        final int bass = sp.load(MainActivity.this, R.raw.bass_drum, 1);
        final int bubble = sp.load(MainActivity.this, R.raw.bubble, 1);

        //thread.sleep()沒有放在Thread(new runnable())裡面跑會錯誤
        new Thread(new Runnable() {
            @Override
            public void run() {
                int counting=0;
                while (true){
                    try{
                        Thread.sleep(INTERVAL);
                        if (counting==3){

                            sp.play(bubble,1,1,0,0,1);
                            updateColor();
                            counting = 0;

                        }
                        else{

                            sp.play(bass,1,1,0,0,1);
                            counting++;

                        }

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void updateColor() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    myView.setBackground(changingColor);
                    changingColor.startTransition(500);


            }

        });
    }

}
