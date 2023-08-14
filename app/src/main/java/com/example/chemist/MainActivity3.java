package com.example.chemist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        BottomNavigationView bottom_NavigationView = findViewById(R.id.bottom_NavigationView);
        bottom_NavigationView.setSelectedItemId(R.id.c_table);

        bottom_NavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.c_reaction) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
                finish();
                return true;
            } else if (itemId == R.id.c_table) {
                return true;
            } else if (itemId == R.id.c_about) {
                startActivity(new Intent(getApplicationContext(), MainActivity4.class));
                overridePendingTransition(R.anim.slidein_right, R.anim.slideout_left);
                finish();
                return true;
            }
            return false;
        });

        //hide the actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}