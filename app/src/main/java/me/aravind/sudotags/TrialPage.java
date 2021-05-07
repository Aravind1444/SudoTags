package me.aravind.sudotags;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class TrialPage extends AppCompatActivity {

    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_page);

        btScan = findViewById(R.id.scanbuttonone);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        TrialPage.this
                );

                //SET PROMPT TEXT
                intentIntegrator.setPrompt("Use Volume Up key for turning on the flash");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });
    }

    //code for scan results
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        String resultText = intentResult.getContents();
        TextView output = findViewById(R.id.outputTextone);
        output.setText(resultText);
        ((TextView) TrialPage.this.findViewById(R.id.outputTextone)).setText(resultText);
        ((TextView) TrialPage.this.findViewById(R.id.outputTextone)).setText(intentResult.getContents());
        Log.d("The result is", intentResult.getContents());

        if(intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(TrialPage.this);
            builder.setTitle("Tag ID");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
        else {
            //when result content is null or not properly read
            Toast.makeText(getApplicationContext(), "Please Scan Again!", Toast.LENGTH_SHORT).show();
        }
    }

}