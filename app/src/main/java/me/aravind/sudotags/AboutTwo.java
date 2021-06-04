package me.aravind.sudotags;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AboutTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_two);

        //force turn off night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        //navigate to next page
        Button buttonToThird = (Button) findViewById(R.id.toThird);
        buttonToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutTwo.this, AboutThree.class);
                startActivity(intent);
                finish();
            }
        });

        //navigate to previous page
        Button buttonToFirst = (Button) findViewById(R.id.toFirst);
        buttonToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutTwo.this, AboutOne.class);
                startActivity(intent);
                finish();
            }
        });

        //navigate to home page
        Button buttonTwoHome = (Button) findViewById(R.id.toFirst);
        buttonTwoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutTwo.this, HomePage.class);
                startActivity(intent);
                finish();
                finish();
            }
        });



    }
}