package com.example.bilclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ManagerProfileActivity extends AppCompatActivity {

    TextView text;
    DatabaseReference myRef;
    FirebaseDatabase database;
    Button button5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profile);
        button5 = findViewById(R.id.button5);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("accounts");
        Query q = myRef.orderByChild("key").equalTo(MainActivity.key);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren())
                {
                    Account a = d.getValue(Account.class);
                    text = findViewById(R.id.textView7);
                    text.setText("CLUB NAME: " + a.getClubName() + "\nMANAGER: " + a.getUserName());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
