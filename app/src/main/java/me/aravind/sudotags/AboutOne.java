package me.aravind.sudotags;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AboutOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_one);

        //force turn off night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //navigate to next page
        Button buttonNext = (Button) findViewById(R.id.nextPage);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutOne.this, AboutTwo.class);
                startActivity(intent);
                finish();
            }
        });

        //navigate to home page
        Button buttonHome = (Button) findViewById(R.id.homebutton);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutOne.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}