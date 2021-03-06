package me.aravind.sudotags;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AboutThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_three);

        //force turn off night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //navigate to next page
        Button buttonFinish = (Button) findViewById(R.id.finishButton);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutThree.this, SigninPage.class);
                startActivity(intent);
                finish();
            }
        });

        //navigate to previous -secon-  page
        Button buttonToSecond = (Button) findViewById(R.id.toSecond);
        buttonToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutThree.this, AboutTwo.class);
                startActivity(intent);
                finish();
            }
        });

        //navigate to home page
        Button buttonThreeHome = (Button) findViewById(R.id.homebutton);
        buttonThreeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutThree.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}