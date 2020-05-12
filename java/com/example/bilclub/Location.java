package com.example.bilclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class Location extends AppCompatActivity {
    DatabaseReference myRef;
    StorageReference mStorageRef;
    double x, y;
    public Location()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        myRef = FirebaseDatabase.getInstance().getReference("events");

    }
}
