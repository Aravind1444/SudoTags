package me.aravind.sudotags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class WelcomeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //statubar color code
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#059669"));
        //end of statusbar color code

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_details);

        //force turn off night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);

        String FirstTime = preferences.getString("FirstTimeInstall", "");

        if(FirstTime.equals("Yes")){
            Intent intent = new Intent(WelcomeDetails.this, SigninPage.class);
            startActivity(intent);
        }
        else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }


        //navigate to Yes-AboutPage page
        Button buttonYes = (Button) findViewById(R.id.yesButton);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeDetails.this, AboutOne.class);
                startActivity(intent);
            }
        });

            //navigate to No-Sign in page
        Button buttonNo = (Button) findViewById(R.id.noButton);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeDetails.this, SigninPage.class);
                startActivity(intent);
            }
        });

    }
}