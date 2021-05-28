package me.aravind.sudotags;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddProducts extends AppCompatActivity {

    private Button button, saveButton;
    ImageView ivOutput;
    Button addProductButton;
    EditText productName;
    TextView id;

    DatabaseReference sudotagsDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        //save the qr code to gallery
        ActivityCompat.requestPermissions(AddProducts.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ImageView imageView = (ImageView) findViewById(R.id.producttagimage);
        saveButton = (Button) findViewById(R.id.savebutton);
        ActivityCompat.requestPermissions(AddProducts.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddProducts.this, "Tag Saved Successfully! ", Toast.LENGTH_SHORT).show();
                imagesavetomyphonegallery();
            }

        });

        //google auth profile details for the database child below
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(AddProducts.this);
        if (acct != null) {
            String personId = acct.getId();
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Log.d("User id is : ", personId);
        }
        String personId = acct.getId();

        //database reference
        //trying to change child products to user id
        sudotagsDB = FirebaseDatabase.getInstance().getReference().child(""+personId);

        //navigate to info page
        button = (Button) findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProducts.this, HomePage.class);
                startActivity(intent);
            }
        });
    }


    public void handleText(View view) {

        //google auth profile details
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(AddProducts.this);
        if (acct != null) {
            String personId = acct.getId();
            Log.d("User id is : ", personId);
        }
        String personId = acct.getId();
        //TextView userid = findViewById(R.id.productpin);
        //userid.setText(personId);
        String personName = acct.getDisplayName();
        String personEmail = acct.getEmail();

        addProductButton = findViewById(R.id.addproduct);
        productName = findViewById(R.id.productname);
        ivOutput = findViewById(R.id.producttagimage);
        TextView productTextName = findViewById(R.id.productname);
        String ProductName = productTextName.getText().toString();
        //removing sudo@ and @tags and product name and making it just the user id in the tag
        //"sudo!@" + productTextName.getText().toString() + "@tags!" ---> removed
        String uniqueID = (personId);
        ((TextView) findViewById(R.id.uniqueid)).setText(uniqueID);

        //logging the uniqueID to the console

        Log.d("UniqueID is : ", uniqueID);

        //Generating the QR Code on button click

        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            //initializing the bit matrix
            BitMatrix matrix = writer.encode(uniqueID, BarcodeFormat.QR_CODE, 500, 500);
            //intializing the barcode encoder
            BarcodeEncoder encoder = new BarcodeEncoder();
            //intializing the bitmap
            Bitmap bitmap = encoder.createBitmap(matrix);
            ivOutput.setImageBitmap(bitmap);
            //Initializing input manager
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //hide soft keyboard
            manager.hideSoftInputFromWindow(addProductButton.getWindowToken(), 0);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        ProductData productdata = new ProductData(ProductName, personId, personName, personEmail);
        sudotagsDB.push().setValue(productdata);
        Toast.makeText(AddProducts.this, "Successfully Added", Toast.LENGTH_SHORT).show();
    }

    //class for saving the image to gallery
    private void imagesavetomyphonegallery() {

        ImageView img = (ImageView) findViewById(R.id.producttagimage);

        BitmapDrawable draw = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/SudoTags");
        dir.mkdirs();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        try {
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}