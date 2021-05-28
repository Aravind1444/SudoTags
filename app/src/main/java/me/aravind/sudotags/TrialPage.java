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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class TrialPage extends AppCompatActivity {

    private Button button;
    Button btScan;

    DatabaseReference dbOwner;

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


        //navigate to info page
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
        final TextView email = findViewById(R.id.email);

        //querying owner info and to fire mail

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

                        String dbMail = String.valueOf(dataSnapshot1.child("userEmailDB").getValue());
                        Log.i("The mail is ", dbMail);
                        email.setText(dbMail);

                        count++;

                        //mailing feature
                        String mail = dbMail;
                        String subject = "Lost Product Scanned!";
                        String message = "Hello " + dbName + ", \n\nYour product which was lost recently was found and scanned by " + personName + ". Please reach out to him using this mail address : " + personEmail;
                        sendMail(mail, subject, message);
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

    private void sendMail(String email, String subject, String message){
        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,email,subject,message);

        javaMailAPI.execute();
    }
}