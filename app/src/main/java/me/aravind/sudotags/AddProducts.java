package me.aravind.sudotags;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class AddProducts extends AppCompatActivity {

    private Button button;
    ImageView ivOutput;
    Button addProductButton;
    EditText productName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

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



    public void handleText(View view){
        addProductButton = findViewById(R.id.addproduct);
        productName = findViewById(R.id.productname);
        ivOutput = findViewById(R.id.producttagimage);
        TextView productTextName= findViewById(R.id.productname);
        TextView productPinValue = findViewById(R.id.productpin);
        String uniqueID = ("sudo!@"  + productTextName.getText().toString() + "sudo!@"  +  productPinValue.getText().toString() + "sudo!@");
        ((TextView)findViewById(R.id.uniqueid)).setText(uniqueID);

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
            manager.hideSoftInputFromWindow(addProductButton.getWindowToken(),0);
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

}