package me.aravind.sudotags;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class TrialPage extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    private Button button;
    Button btScan;

    DatabaseReference dbOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_page);

        //force turn off night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        //firebase analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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


        //navigate to home page
        button = (Button) findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrialPage.this, HomePage.class);
                startActivity(intent);
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

        //declaring the name and text mail variables
        final TextView name = findViewById(R.id.ownername);

        //querying owner info and to fire mail
        //.child("" + resultText)

        dbOwner = FirebaseDatabase.getInstance().getReference().child("" + resultText);
        dbOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 1;
                if(count == 1){
                    for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){

                        //google auth profile details
                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(TrialPage.this);
                        if (acct != null) {
                            String personId = acct.getId();
                            Log.d("User id is : ", personId);
                        }
                        String personName = acct.getDisplayName();
                        String personEmail = acct.getEmail();

                        //code for value pop up
                        String dbName = String.valueOf(dataSnapshot1.child("userNameDB").getValue());
                        Log.i("The name is ", dbName);
                        name.setText(dbName);

                        final String dbMail = String.valueOf(dataSnapshot1.child("userEmailDB").getValue());
                        Log.i("The mail is ", dbMail);
                        //contact the owner button

                        count++;

                        //mailing feature
                        String mail = dbMail;
                        String subject = "Lost Product Scanned!";
                        String message = "Hello " + dbName + ", \n\nYour product which was lost recently was found and scanned by " + personName + ". Please reach out to them using this mail address : " + personEmail + "\n\nRegards \nSudoTags Team";
                        int a = 0;
                        if(a == 0){
                            sendMail(mail, subject, message);
                            a++;
                        }

                        //code for contact owner button
                        Button contactButton = (Button) findViewById(R.id.contactButton);
                        contactButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = "mailto:"+dbMail;
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //alert dialog box
        if(intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(TrialPage.this);
            builder.setTitle("Hurray 🥳");
            builder.setMessage("The owner of the product has been notified about the product being found. Please feel free to reach out to the owner to return the item!");
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

    private void sendMail(String email, String subject, String message){
        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,email,subject,message);

        javaMailAPI.execute();
    }
}