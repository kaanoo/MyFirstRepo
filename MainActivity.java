package com.example.bilclub;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressBar pb;
    ImageView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("accounts");

        Account a = new Account("Azer", "CS", 21555555, "");
        myRef.push().setValue(a);


        pb = (ProgressBar)findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("   Bilclub");
        toolbar.setTitleTextColor(Color.BLACK);

        toolbar.setLogo(R.drawable.ic_contact_mail_black_24dp);
        setSupportActionBar(toolbar);
        v = findViewById(R.id.imageView4);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Write a message to the database

    }
}
