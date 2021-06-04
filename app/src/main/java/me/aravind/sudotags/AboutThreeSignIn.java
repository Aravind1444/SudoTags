package me.aravind.sudotags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutThreeSignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_three_sign_in);


        //force turn off night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //navigate to next page
        Button buttonFinish = (Button) findViewById(R.id.finishButtonSignIn);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutThreeSignIn.this, SigninPage.class);
                startActivity(intent);
                finish();
            }
        });

        //navigate to previous page
        Button buttonToSecond = (Button) findViewById(R.id.toSecond);
        buttonToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutThreeSignIn.this, AboutTwo.class);
                startActivity(intent);
                finish();
            }
        });


    }
}