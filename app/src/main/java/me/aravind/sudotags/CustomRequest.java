package me.aravind.sudotags;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class CustomRequest extends AppCompatActivity {

    private Button buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_request);

        //force turn off night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //navigate to info page
        buttonHome = (Button) findViewById(R.id.homebutton);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomRequest.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button placeorderButton = findViewById(R.id.placeOrder);
        placeorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

    }

    private void placeOrder() {

        //Request Details
        EditText stickerTags = findViewById(R.id.stickerTagValue);
        String stcikers = stickerTags.getText().toString();

        EditText stickerColor = findViewById(R.id.colorTagValue);
        String colors = stickerColor.getText().toString();

        EditText numberTags = findViewById(R.id.numberTagsValue);
        String numberString = numberTags.getText().toString();
        int numberofTags = Integer.parseInt(numberString);
        int price = numberofTags*25;

        EditText contactNumber = findViewById(R.id.contactTextValue);
        String contact = contactNumber.getText().toString();

        //google auth profile details
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(CustomRequest.this);
        if (acct != null) {
            String personId = acct.getId();
            Log.d("User id is : ", personId);
        }
        String personName = acct.getDisplayName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();

        //mailing feature - mail for the developer
        String mail = "thesudotags@gmail.com";
        String subject = "New Custom Order / Request";
        String message = "Hey there,\n\n" + personName + " has requested the creation of custom QR Codes with the following details. Here are the contact details, as well as the tag details: \n\nName : " + personName + "\nemail id : " + personEmail + "\nUser ID : " + personId + "\nSticker Tags : " + stcikers + "\nPreferred Tag Color : " + colors + "\nNumber of Tags : " + numberofTags + "\nContact Number : " + contact;
        sendMail(mail, subject, message);

        //mailing feature - mail for user
        String mailuser = personEmail;
        String subjectUser = "SudoTags | Order Received";
        String messageUser = "Hello " + personName + ", \n\nThe SudoTags team has received a request for a custom tag with the following details: \n\nName : " + personName + "\nemail id : " + personEmail + "\nUser ID : " + personId + "\nSticker Tags : " + stcikers + "\nPreferred Tag Color : " + colors + "\nNumber of Tags : " + numberofTags + "\nContact Number : " + contact + "\n\nIf you didn't opt for a Sticker Tag, your custom tag will be arriving to this mail box within 2 days at max. If you have opted for sticker tags, we will send you the soft copy within 2 days and the Sticker Tags will be arriving at your physical mailing address based on the delivery speed at your location. Also, please reply back to this mail with the delivery address for smooth processing.\n\nThe overall order value is ₹" + price + " for " + numberofTags + " tags. The mode of payment will be Cash on Delivery." + "\n\nIf you have any concerns or want to change any of the above details, please reply to this mail at the earliest. \n\nRegards \nSudoTags Team";
        sendUserMail(mailuser, subjectUser, messageUser);

    }


    //mail delivery for the developer
    private void sendMail(String email, String subject, String message){
        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,email,subject,message);
        javaMailAPI.execute();
    }

    //mail delivery for the user
    private void sendUserMail(String emailUser, String subjectUser, String messageUser){
        //Send Mail
        JavaMailAPIUser javaMailAPIUser = new JavaMailAPIUser(this,emailUser,subjectUser,messageUser);
        javaMailAPIUser.execute();
        Toast.makeText(CustomRequest.this, "Order successfully placed! Please check your mail for details.", Toast.LENGTH_LONG).show();
    }

}