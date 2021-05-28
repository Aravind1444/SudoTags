package me.aravind.sudotags;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyProducts extends AppCompatActivity {

    private Button button;

    RecyclerView recyclerView;
    DatabaseReference database;
    MyProductAdapter myProductAdapter;
    ArrayList<MyProductData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);

        //google auth profile details for the database child below
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MyProducts.this);
        if (acct != null) {
            String personId = acct.getId();
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Log.d("User id is : ", personId);
        }
        String personId = acct.getId();


        //code for recycler view
        recyclerView = findViewById(R.id.myproducts);
        database = FirebaseDatabase.getInstance().getReference(""+personId); //or use child
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myProductAdapter = new MyProductAdapter(this, list);
        recyclerView.setAdapter(myProductAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    MyProductData myProductData = dataSnapshot.getValue(MyProductData.class);
                    list.add(myProductData);

                }
                myProductAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //navigate to info page
        button = (Button) findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProducts.this, HomePage.class);
                startActivity(intent);
            }
        });

    }
}