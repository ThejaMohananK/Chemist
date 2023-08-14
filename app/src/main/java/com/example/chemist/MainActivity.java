package com.example.chemist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView main_text2;
    ImageView main_img;

    private static int Splash_timeout=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide the actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        main_img = findViewById(R.id.main_img);
        main_text2 = findViewById(R.id.main_text2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashintent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(splashintent);
                finish();
            }
        },Splash_timeout);
        Animation myanimation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation2);
        main_img.startAnimation(myanimation);

        Animation myanimation2= AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation1);
        main_text2.startAnimation(myanimation2);
    }
}